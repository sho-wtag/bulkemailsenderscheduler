/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler;

import com.bits.BulkEmailSenderScheduler.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 *
 * @author mdshahadat.sarker
 */
public class AppInit implements ApplicationRunner{
    
    @Autowired
    MailSenderService mailSenderService;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //mailSenderService.sendEmail();
        //mailSenderService.emailService();
    }
    
}
