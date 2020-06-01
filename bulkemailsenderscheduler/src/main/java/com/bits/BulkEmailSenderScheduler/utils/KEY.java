/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.utils;

/**
 *
 * @author sarker
 */
public class KEY {
    public static final String USER="USER";
    
    public static final String user_id="user_id";
    public static final String note="note";
    public static final String type="type";
    public static final String is_selected="is_selected";
    public static final String active="active";
    public static final String START="START";
    public static final String LIMIT="LIMIT";
    public static final String INDEX="INDEX";
    
    public class BATCH{
        public static final String LIST="LIST";
        public static final String ATM_1="ATM_1";
        public static final String ATM_2="ATM_2";
        public static final String ATM_3="ATM_3";
        public static final String ATM_4="ATM_4";
        public static final String ATM_5="ATM_5";
        public static final String ATM_6="ATM_6";
        public static final String ATM_7="ATM_7";
        public static final String ATM_8="ATM_8";
    }
    
    public class MAIL{
        public static final String mail_queue_id="mail_queue_id";
        public static final String mail_queue_recipient_id="mail_queue_recipient_id";
        public static final String campaign_id="campaign_id";
        public static final String mail_subject="mail_subject";
        public static final String mail_body="mail_body";
        public static final String reply_from="reply_from";
        public static final String reply_to="reply_to";
        public static final String mail_id="mail_uuid";
        public static final String active="active";
        public static final String is_sent="is_sent";
        public static final String is_bounced="is_bounced";
        public static final String opened_count="opened_count";
        public static final String created_on="created_on";
        public static final String updated_on="updated_on";
        
        public static final String recipient_to="recipient_to";
        public static final String recipient_cc="recipient_cc";
        public static final String recipient_bcc="recipient_bcc";
        
        public static final String SUBJECT="Subject: ";
        public static final String RECIPIENT="Recipient Address: ";
        
    }
    
    public class STATUS{
        public static final int IN_QUEUE=-1;
        public static final int DOWNLOADED=0;
        public static final int PARTIAL=1;
        public static final int FAILED=2;
    }
    
}
