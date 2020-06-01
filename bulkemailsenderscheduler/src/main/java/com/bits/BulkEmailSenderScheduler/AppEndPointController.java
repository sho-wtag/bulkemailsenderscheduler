/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler;

import com.bits.BulkEmailSenderScheduler.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mdshahadat.sarker
 */
@RestController
public class AppEndPointController {
    
    @Autowired
    MailSenderService mailSenderService;
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index() throws Exception{
        return "Scheduler has been started successfully!!!";
    }
    
}
