package com.bits.BulkEmailSenderScheduler.repo;

import com.bits.BulkEmailSenderScheduler.model.Campaign;
import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Repository
public class CampaignRepo {
    
    @Autowired
    @Qualifier("msSchedulerJdbcTemplate")
    private JdbcTemplate jdb;
    
    @Autowired
    @Qualifier("namedSchedulerJdbcTemplate")
    private NamedParameterJdbcTemplate db;

    @Autowired
    AppUtil util;
    
    
    public List<Campaign> getUnProcessedCampaigns(String currentDateTime) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT campaign_id, cid, name, description, template_id, last_check, check_status, reply_from, reply_to, subject, html, html_prepared, status, ");
        sql.append(" tracking_disabled, CONVERT(datetime, scheduled) scheduled, status_change, delivered, opened, clicks, unsubscribed, bounced, complained, created ");
        sql.append(" FROM campaigns ");
        sql.append(" where status=1 and scheduled<=:currentDateTime order by scheduled asc ");
        Map<String, Object> params = new HashMap<>();
        params.put("currentDateTime", currentDateTime);
        return db.query(sql.toString(), params, new BeanPropertyRowMapper(Campaign.class));
    }
    
    public List<Map<String, Object>> getCampaignContacts(long campaignId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select distinct c.email ");
        sql.append(" from campaign_contacts cc ");
        sql.append(" join contacts c on cc.contact_id=c.contact_id ");
        sql.append(" where cc.campaign_id=:campaign_id ");
        sql.append(" order by c.email ");
        Map<String, Object> params = new HashMap<>();
        params.put("campaign_id", campaignId);
        return db.queryForList(sql.toString(), params);
    }
    
    public void saveCampaignCIDs(long campaignId, String file) {
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into campaign_attachments (campaign_id, filename, type) values (?,?,2) ");
        jdb.update(sql.toString(), campaignId, file);
    }
    
    public synchronized boolean markAsProcessed(Campaign campaign) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE campaigns SET  ");
        sql.append(" status=2, ");
        sql.append(" check_status='Processed by system', ");
        sql.append(" status_change=getdate() ");
        sql.append(" WHERE campaign_id=:campaign_id; ");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(campaign);
        return db.update(sql.toString(), namedParameters) == 1;
    }
}
