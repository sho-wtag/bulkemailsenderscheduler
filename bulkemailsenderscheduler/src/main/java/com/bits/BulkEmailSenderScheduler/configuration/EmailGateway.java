/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.configuration;

import com.bits.BulkEmailSenderScheduler.model.SmtpMail;
import com.bits.BulkEmailSenderScheduler.repo.CommonRepo;
import com.bits.BulkEmailSenderScheduler.repo.MailQueueRepo;
import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sarker
 */
@Component
public class EmailGateway {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    MailQueueRepo mailQueueRepo;
    
    @Autowired
    CommonRepo commonRepo;
    
    @Autowired
    AppUtil util;

    @Async
    public void sendHtmlEmail(String[] recipients, String subject, String body, String senderEmail, String senderName) throws Exception {

        MimeMessage message = sender.createMimeMessage();

        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(recipients);
        helper.setFrom(senderEmail, senderName);
        helper.setText(body, true);
        helper.setReplyTo(senderEmail, senderName);
        helper.setSubject(subject);
        sender.send(message);
        System.out.println("Mail sent...");
    }

    @Async
    public void sendHtmlEmail(SmtpMail smtpMail, JavaMailSender cSender, int appSmtpId) throws Exception {
        try {
            MimeMessage message = cSender.createMimeMessage();

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(smtpMail.getRecipients());
            if (smtpMail.getCc().length > 0 && !smtpMail.getCc()[0].equals("") ) {
                helper.setCc(smtpMail.getCc());
            }
            if (smtpMail.getBcc().length > 0  && !smtpMail.getBcc()[0].equals("") ) {
                helper.setBcc(smtpMail.getBcc());
            }

            helper.setFrom(smtpMail.getSenderEmail(), smtpMail.getSenderName());
            helper.setText(smtpMail.getBody(), true);
            helper.setReplyTo(smtpMail.getSenderEmail(), smtpMail.getSenderName());
            helper.setSubject(smtpMail.getSubject());
            
            if( smtpMail.getAttachments() != null && !smtpMail.getAttachments().isEmpty() ){
                for(int i = 0 ; i< smtpMail.getAttachments().size() ; i++){
                    String attach = smtpMail.getAttachments().get(i).get("filename").toString();
                    FileSystemResource file = new FileSystemResource( util.getCampaignAttachPath() + File.separator + attach);
                    helper.addAttachment(file.getFilename(), file);
                }
            }
            
            if( smtpMail.getCids()!= null && !smtpMail.getCids().isEmpty() ){
                Multipart multipart = new MimeMultipart();
                
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(smtpMail.getBody(), "text/html");
                multipart.addBodyPart(messageBodyPart);
                
                if( smtpMail.getAttachments() != null && !smtpMail.getAttachments().isEmpty() ){
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    for(int i = 0 ; i< smtpMail.getAttachments().size() ; i++){
                        String attach = smtpMail.getAttachments().get(i).get("filename").toString();
                        File file = new File( util.getCampaignAttachPath() + File.separator + attach);
                        attachmentPart.attachFile(file);
                        attachmentPart.setDisposition(MimeBodyPart.ATTACHMENT);
                        multipart.addBodyPart(attachmentPart);
                    }
                }
                
                
                for(int i = 0 ; i< smtpMail.getCids().size() ; i++){
                    MimeBodyPart imagePart = new MimeBodyPart();
                    imagePart.setHeader("Content-ID", "<" + smtpMail.getCampaignId() + "_img_" + i + ">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                    
                    String imagePath = util.getCampaignCIDPath() + File.separator + smtpMail.getCids().get(i).get("filename").toString();
                    try{
                        imagePart.attachFile(imagePath);
                    } catch(Exception exe){
                        exe.printStackTrace();
                    }
                    
                    multipart.addBodyPart(imagePart);
                }
                
                
                
                message.setContent(multipart);
            }
            
            cSender.send(message);
            System.out.println("Mail sent...");
            mailQueueRepo.markAsSent(smtpMail.getMailId());
            
        } catch (Exception e) {
            // mark as failed of that smtp server connection
            if( e.getMessage().contains("server connection failed") ){
                commonRepo.setSmtpConnection(appSmtpId, 0);
            }
            
            mailQueueRepo.markAsProcessingCompleted(smtpMail.getMailId());
            System.out.println("Mail cannot be sent! " + e.getMessage());
            e.printStackTrace();
        }
    }

}
