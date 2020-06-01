/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.repo;

import com.bits.BulkEmailSenderScheduler.model.MailQueue;
import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mdshahadat.sarker
 */

@Repository
public class MailQueueRepo {
    @Autowired
    @Qualifier("msSchedulerJdbcTemplate")
    private JdbcTemplate db;
    
    @Autowired
    @Qualifier("namedSchedulerJdbcTemplate")
    private NamedParameterJdbcTemplate nDB;
    
    public List<Map<String, Object>> getQueueRecipientList(long mailQueueId){
        StringBuilder sql = new StringBuilder();
        sql.append(" select top 100 qr.mail_queue_recipient_id, qr.mail_queue_id, qr.campaign_id, qr.recipient_to, qr.recipient_cc, qr.recipient_bcc, qr.mail_uuid, ");
        sql.append(" mq.mail_subject, mq.mail_body, c.reply_from");
        sql.append(" from mail_queue_recipients qr ");
        sql.append(" join mail_queue mq on qr.mail_queue_id=mq.mail_queue_id ");
        sql.append(" join campaigns c on qr.campaign_id=c.campaign_id");
        sql.append(" where qr.is_sent=0 and qr.is_processing=0 and qr.active=1 ");
        sql.append(" order by qr.mail_queue_recipient_id ");
        
        return db.queryForList(sql.toString());
    }
    
    public List<Map<String, Object>> getMailAttachments(long campaignId){
        try{
            StringBuilder sql = new StringBuilder();
            sql.append(" select filename ");
            sql.append(" from campaign_attachments ");
            sql.append(" where campaign_id=:campaign_id and type=1 ");
            Map<String, Object> params = new HashMap<>();
            params.put("campaign_id", campaignId);
            return nDB.queryForList(sql.toString(), params);
        } catch(Exception e){
            return null;
        }
    }
    
    public List<Map<String, Object>> getMailCIDs(long campaignId){
        try{
            StringBuilder sql = new StringBuilder();
            sql.append(" select filename ");
            sql.append(" from campaign_attachments ");
            sql.append(" where campaign_id=:campaign_id and type=2 ");
            Map<String, Object> params = new HashMap<>();
            params.put("campaign_id", campaignId);
            return nDB.queryForList(sql.toString(), params);
        } catch(Exception e){
            return null;
        }
    }
    
    public long saveForId(MailQueue mailQueue) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO mail_queue( ");
        sql.append(" campaign_id, mail_subject, mail_body, active)");
        sql.append(" VALUES ( ");
        sql.append(" :campaign_id, :mail_subject, :mail_body, 1)");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(mailQueue);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        nDB.update(sql.toString(), namedParameters, keyHolder);
        return keyHolder.getKey().longValue();
    }
    
    public long getMailQueueId(String str) {
        String sql = " select top 1 mail_queue_id from mail_queue where mail_subject like '" + str + "'";
        return db.queryForObject(sql, Long.class);
    }
    
    public boolean markAsbounced(long mailQueueId, String recipient) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE mail_queue_recipients SET ");
        sql.append(" is_bounced=1, is_processing=0, ");
        sql.append(" updated_on=getdate() ");
        sql.append(" WHERE mail_queue_id=? and recipient_to=? ");
        return db.update(sql.toString(), mailQueueId, recipient) == 1;
    }
    
    public boolean markAsSent(long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE mail_queue_recipients SET ");
        sql.append(" is_sent=1, is_processing=0, ");
        sql.append(" updated_on=getdate() ");
        sql.append(" WHERE mail_queue_recipient_id=? ");
        return db.update(sql.toString(), id) == 1;
    }
    
    public boolean markAsProcessingCompleted(long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE mail_queue_recipients SET ");
        sql.append(" is_processing=0, ");
        sql.append(" updated_on=getdate() ");
        sql.append(" WHERE mail_queue_recipient_id=? ");
        return db.update(sql.toString(), id) == 1;
    }
    
    public synchronized void mapMailQueueRecipients(final long campaignId, final long mailQueueId, final List<Map<String, Object>> contactList) {
        StringBuilder sql = new StringBuilder();

        sql.append(" insert into mail_queue_recipients (campaign_id, mail_queue_id, recipient_to, active) values (?,?,?,1) ");

        db.batchUpdate(sql.toString(),
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, campaignId );
                    ps.setLong(2, mailQueueId );
                    ps.setString(3, AppUtil.toString(contactList.get(i).get("email")) );
                }

                @Override
                public int getBatchSize() {
                    return contactList.size();
                }
            });
    }
    
    
    public synchronized void setMailQueueRecipientAsProcessing(final List<Map<String, Object>> queueList) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE mail_queue_recipients set is_processing=? where mail_queue_recipient_id=? ");

        db.batchUpdate(sql.toString(),
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setBoolean(1, true);
                    ps.setLong(2, AppUtil.toLong(queueList.get(i).get("mail_queue_recipient_id")) );
                }

                @Override
                public int getBatchSize() {
                    return queueList.size();
                }
            });
    }
    
}
