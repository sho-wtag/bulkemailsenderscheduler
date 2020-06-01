/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.service;

import com.bits.BulkEmailSenderScheduler.configuration.EmailGateway;
import com.bits.BulkEmailSenderScheduler.model.AppSmtp;
import com.bits.BulkEmailSenderScheduler.model.SmtpMail;
import com.bits.BulkEmailSenderScheduler.repo.CommonRepo;
import com.bits.BulkEmailSenderScheduler.repo.MailQueueRepo;
import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
import com.bits.BulkEmailSenderScheduler.utils.KEY;
import com.bits.BulkEmailSenderScheduler.utils.MailUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author mdshahadat.sarker
 */

@Service
public class MailSenderService {
    Logger logger = LoggerFactory.getLogger(MailSenderService.class);
    
//    @Autowired
//    JobLauncher jobLauncher;
    
//    @Autowired
//    Job job;
    
    @Autowired
    EmailGateway emailGateway;
    
    @Autowired
    AppUtil util;
    
    @Autowired
    MailQueueRepo mailQueueRepo;
    
    @Autowired
    CommonRepo commonRepo;
    
    @Autowired
    MailUtil mailUtil;
    
    private int s=0;
    private List<AppSmtp> smtpList = new ArrayList<>();
    
//    public void sendEmail() throws Exception{
//        
//        JobParameters params = new JobParametersBuilder()
//                .addLong("time", System.currentTimeMillis() )
//                .addString("JobID", String.valueOf(System.currentTimeMillis()) ).toJobParameters();
//            jobLauncher.run(job, params);
//    }
    
    
    public void sendUnsentEmail() {
        try {
            
            List<Map<String, Object>> list = mailQueueRepo.getQueueRecipientList(1);
            if(list.isEmpty()){
                logger.info("No pending email found!!");
            } else{
                mailQueueRepo.setMailQueueRecipientAsProcessing(list);
                
                s=0;
                smtpList = commonRepo.getAllSmtp();
                
                list.forEach(email->{
                    String[] receivers = AppUtil.toString(email.get(KEY.MAIL.recipient_to)).split(",");
                    String[] cc = AppUtil.toString(email.get(KEY.MAIL.recipient_cc)).split(",");
                    String[] bcc = AppUtil.toString(email.get(KEY.MAIL.recipient_bcc)).split(",");
                    String subject = AppUtil.toString(email.get(KEY.MAIL.mail_subject));
                    String body = AppUtil.toString(email.get(KEY.MAIL.mail_body));
                    String senderEmail = AppUtil.toString(email.get(KEY.MAIL.reply_from));
                    long mailQueueRecipientId = AppUtil.toLong(email.get(KEY.MAIL.mail_queue_recipient_id));

                    long campaignId = AppUtil.toLong(email.get(KEY.MAIL.campaign_id));
                    List<Map<String, Object>> aList = mailQueueRepo.getMailAttachments(campaignId);
                    List<Map<String, Object>> cList = mailQueueRepo.getMailCIDs(campaignId);

                    try {
                        //body += "<img src='" + util.getMailTrackerServer() + AppUtil.toString(email.get(KEY.MAIL.mail_id)) + "' alt='' />";
                        SmtpMail newMail = new SmtpMail();
                        newMail.setMailId(mailQueueRecipientId);
                        newMail.setCampaignId(campaignId);
                        newMail.setRecipients(receivers);
                        newMail.setCc(cc);
                        newMail.setBcc(bcc);
                        newMail.setSubject(subject);
                        newMail.setBody(body);

                        if( aList!=null ){
                            newMail.setAttachments(aList);
                        }
                        if( cList!=null ){
                            newMail.setCids(cList);
                        }

                        newMail.setSenderEmail(senderEmail);
                        newMail.setSenderName(util.getSenderName());
                        
                        AppSmtp smtp = smtpList.get(s);
                        JavaMailSender cSender = mailUtil.getMailSender(smtp);

                        emailGateway.sendHtmlEmail(newMail, cSender, smtp.getId());
                        
                        s++;
                        if(s==smtpList.size()){ 
                            s=0; // re-init the index for randomize the smtp
                        }
                        
                    } catch (Exception ex) {
                        logger.info("Exception in email sending - Service Layer");
                        ex.printStackTrace();
                    }
                });
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
