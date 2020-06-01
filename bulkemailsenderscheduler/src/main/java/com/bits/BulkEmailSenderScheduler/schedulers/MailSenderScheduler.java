/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.schedulers;

import com.bits.BulkEmailSenderScheduler.service.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author mdshahadat.sarker
 */

@Component
public class MailSenderScheduler {
    Logger logger = LoggerFactory.getLogger(MailSenderScheduler.class);
    
    @Autowired
    MailSenderService mailSender;
    
    // 5 seconds
    @Scheduled(fixedRate = 5000)
    public void sendingUnsentEmail() {
        logger.info("Email Sending - RESUMED");
        mailSender.sendUnsentEmail();
        logger.info("Email Sending - COMPLETED");
    }
    
}
