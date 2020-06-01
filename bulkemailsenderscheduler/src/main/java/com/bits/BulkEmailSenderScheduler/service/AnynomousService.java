/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.service;

import com.bits.BulkEmailSenderScheduler.repo.CampaignRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mdshahadat.sarker
 * This service cannot be import AppUtil class------- BECAREFULL-----------
 */

@Service
public class AnynomousService {
    @Autowired
    CampaignRepo campaignRepo;
    
    public void saveCampaignCIDs(long campaignId, String file){
        try{
           campaignRepo.saveCampaignCIDs(campaignId, file);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
