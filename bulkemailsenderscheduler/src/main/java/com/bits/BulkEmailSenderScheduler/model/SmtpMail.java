/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author mdshahadat.sarker
 */
public class SmtpMail {
    private long mailId;
    private long campaignId;
    private String senderEmail;
    private String senderName;
    private String[] recipients;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String body;
    private List<Map<String, Object>> attachments;
    private List<Map<String, Object>> cids;
    private UUID mail_uuid;

    public long getMailId() {
        return mailId;
    }

    public void setMailId(long mailId) {
        this.mailId = mailId;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Map<String, Object>> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Map<String, Object>> attachments) {
        this.attachments = attachments;
    }

    public List<Map<String, Object>> getCids() {
        return cids;
    }

    public void setCids(List<Map<String, Object>> cids) {
        this.cids = cids;
    }

    public UUID getMail_uuid() {
        return mail_uuid;
    }

    public void setMail_uuid(UUID mail_uuid) {
        this.mail_uuid = mail_uuid;
    }
    
}
