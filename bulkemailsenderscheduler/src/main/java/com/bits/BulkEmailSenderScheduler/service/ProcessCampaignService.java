/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.service;

import com.bits.BulkEmailSenderScheduler.model.Campaign;
import com.bits.BulkEmailSenderScheduler.model.MailQueue;
import com.bits.BulkEmailSenderScheduler.repo.CampaignRepo;
import com.bits.BulkEmailSenderScheduler.repo.CommonRepo;
import com.bits.BulkEmailSenderScheduler.repo.MailQueueRepo;
import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
import com.bits.BulkEmailSenderScheduler.utils.KEY;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mdshahadat.sarker
 */
@Service
public class ProcessCampaignService {

    Logger logger = LoggerFactory.getLogger(ProcessCampaignService.class);

    @Autowired
    MailQueueRepo mailQueueRepo;

    @Autowired
    CampaignRepo campaignRepo;

    @Autowired
    CommonRepo commonRepo;

    @Autowired
    AppUtil util;

    @Transactional
    public void processCampaigns() {
        List<Campaign> list = campaignRepo.getUnProcessedCampaigns(AppUtil.getDateTimeString());
        if (list.isEmpty()) {
            logger.info("No campaign found: " + AppUtil.getDateTimeString());
        }

        list.forEach(campaign -> {
            MailQueue mailQueue = new MailQueue();
            mailQueue.setMail_subject(campaign.getSubject());

            String body = util.processEmbededImages(campaign.getCampaign_id(), campaign.getHtml_prepared());
            mailQueue.setMail_body(body);
            mailQueue.setCampaign_id(campaign.getCampaign_id());

            long mailQueueId = mailQueueRepo.saveForId(mailQueue);
            List<Map<String, Object>> contactList = campaignRepo.getCampaignContacts(campaign.getCampaign_id());
            mailQueueRepo.mapMailQueueRecipients(campaign.getCampaign_id(), mailQueueId, contactList);
            campaignRepo.markAsProcessed(campaign);
            logger.info("Success");
            try{
                Thread.sleep(500);
            } catch(Exception e){}
        });
    }

    public void processActiveSmtpServerConn() {
        commonRepo.setActiveSmtpServerConn(1); // mark as success of active smtp server connection
    }

    public void processCampaignBouncedEmail() {
        //receiveExchangeMail(util.getBouncedAddr(), util.getBouncedPwd(), util.getBouncedHost());
        receiveEmail(util.getBouncedHost(), AppUtil.toInt(util.getBouncedPort()), util.getBouncedAddr(), util.getBouncedPwd());
    }
    
    private void receiveExchangeMail(String userName, String password, String host) {
        try {
            
            ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
            ExchangeCredentials credentials = new WebCredentials(userName, password);
            service.setCredentials(credentials);
            //service.autodiscoverUrl(emailAddress);
            service.setUrl(new java.net.URI("https://bblmail.bracbank.com/EWS/Exchange.asmx"));
            service.setTraceEnabled(true);
            service.setPreAuthenticate(true);
            
            ItemView view = new ItemView(20);
            FindItemsResults<Item> findResults;
            
            do {
                findResults = service.findItems(WellKnownFolderName.RecoverableItemsDeletions, view);
                for (Item item : findResults.getItems()) {
                    System.out.println(item);
                }
                
                view.setOffset(view.getOffset() + 20);
            } while (findResults.isMoreAvailable());
            
            logger.error("----------SUCCESS-----------: ");
        } catch (Exception ex) {
            logger.error("----------ERROR-----------: " + ex.getMessage());
        }
    }

    private void receiveEmail(String host, final int port, final String user, final String password) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            
            properties.setProperty("mail.imaps.socketFactory.port", port + "");
            properties.setProperty("mail.imaps.starttls.enable", "true");
            properties.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.imaps.socketFactory.fallback", "false");
            properties.setProperty("mail.imaps.auth.plain.disable", "true");
            properties.setProperty("mail.imaps.auth.ntlm.disable", "true");
            properties.setProperty("mail.imaps.auth.gssapi.disable", "true");
            properties.setProperty("username", user);   
            properties.setProperty("password", password);
            
            //Session emailSession = Session.getDefaultInstance(properties);
            Session emailSession = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });
            emailSession.setDebug(true);
            
            Store emailStore = emailSession.getStore("imaps");
            emailStore.connect(host, port, user, password);

            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);

            Message[] messages = emailFolder.search(unseenFlagTerm);
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String actualSubject = "", actualRecipient = "";
                MimeMessage payload = (MimeMessage) message;
                Multipart mp = (Multipart) payload.getContent();
                for (int j = 0; j < mp.getCount(); j++) {
                    BodyPart bodyPart = mp.getBodyPart(j);
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(bodyPart.getInputStream(), writer);
                    String[] strArr = writer.toString().split("\n");
                    for (String str : strArr) {
                        if (str.contains(KEY.MAIL.SUBJECT)) {
                            actualSubject = str.replace(KEY.MAIL.SUBJECT, "");
                            actualSubject = actualSubject.trim();
                        } else if (str.contains(KEY.MAIL.RECIPIENT)) {
                            actualRecipient = str.replace(KEY.MAIL.RECIPIENT, "");
                            actualRecipient = actualRecipient.trim();
                        }
                    }
                }
                message.setFlag(Flags.Flag.SEEN, true);
                markBouncedMail(actualSubject, actualRecipient);
            }
            emailFolder.close(true);
            emailStore.close();

            logger.info("-------------BOUNCE EMAIL SUCCESS");
        } catch (Exception e) {
            logger.info("-------------BOUNCE ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void markBouncedMail(String str, String addr) {
        try {
            long mailQueueId = mailQueueRepo.getMailQueueId(str);
            mailQueueRepo.markAsbounced(mailQueueId, addr);
            logger.info("SUBJECT: " + str);
            logger.info("RECEPIENT: " + addr +"; mailQueueId: " + mailQueueId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
