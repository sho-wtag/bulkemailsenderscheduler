/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.model;

import java.util.Date;

/**
 *
 * @author mdshahadat.sarker
 */
public class MailQueue {
    private long mail_queue_id;
    private long campaign_id;
    private String mail_subject;
    private String mail_body;
    private boolean active;
    private Date created_on;
    private Date updated_on;

    public long getMail_queue_id() {
        return mail_queue_id;
    }

    public void setMail_queue_id(long mail_queue_id) {
        this.mail_queue_id = mail_queue_id;
    }

    public long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getMail_subject() {
        return mail_subject;
    }

    public void setMail_subject(String mail_subject) {
        this.mail_subject = mail_subject;
    }

    public String getMail_body() {
        return mail_body;
    }

    public void setMail_body(String mail_body) {
        this.mail_body = mail_body;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }
    
    
}
