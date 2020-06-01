/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.schedulers;

import com.bits.BulkEmailSenderScheduler.service.ProcessCampaignService;
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
public class ProcessCampaignScheduler {
    Logger logger = LoggerFactory.getLogger(ProcessCampaignScheduler.class);
    
    @Autowired
    ProcessCampaignService processCampaignService;
    
    // 30 seconds
    @Scheduled(fixedDelayString = "${scheduler.camp_delay}")
    public void processEmailCampaigns() {
        logger.info("Process campaign - RESUMED");
        try{
            processCampaignService.processCampaigns();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        logger.info("Process campaign - COMPLETED");
    }
    
    
    // 120 seconds
    @Scheduled(fixedDelay = 120000)
    public void processActiveSmtpServerConn() {
        logger.info("Process active smtp server conn - RESUMED");
        try{
            processCampaignService.processActiveSmtpServerConn();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        logger.info("Process active smtp server conn - COMPLETED");
    }
    
    // 300000 seconds = 5 minutes
    @Scheduled(fixedDelay = 60000) // 1 minutes
    public void processCampaignBouncedEmail() {
        logger.info("Process bounced email - RESUMED");
        try{
            processCampaignService.processCampaignBouncedEmail();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        logger.info("Process bounced email - COMPLETED");
    }
    
}
