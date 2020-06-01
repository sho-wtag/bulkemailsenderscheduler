/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.utils;

import com.bits.BulkEmailSenderScheduler.model.AppSmtp;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author mdshahadat.sarker
 */

@PropertySource(value = "classpath:application.properties")
@Component
public class MailUtil {
    
    @Value("${mail.bounceaddr}")
    private String MAIL_BOUNCEADDR;
    
    Properties props = new Properties();
    
    public void getInbox(){
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.debug", "true");
    }
    
    
    public JavaMailSender getMailSender( AppSmtp smtp ){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setProtocol(smtp.getProtocol());
        mailSender.setHost(smtp.getHost());
        mailSender.setPort(AppUtil.toInt(smtp.getPort()));
        mailSender.setUsername(smtp.getSmtp_user());
        mailSender.setPassword(smtp.getSmtp_pass());

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.from", MAIL_BOUNCEADDR);
        javaMailProperties.put("mail.smtp.starttls.enable", smtp.isIs_tls_enable());
        javaMailProperties.put("mail.smtp.ssl.enable", smtp.isIs_ssl_enable());
        javaMailProperties.put("mail.smtp.auth", smtp.isIs_smtp_auth());
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
        
    }
    
}
