package com.bits.BulkEmailSenderScheduler.repo;

import com.bits.BulkEmailSenderScheduler.model.AppSmtp;
import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Repository
public class CommonRepo {
    
    @Autowired
    @Qualifier("msSchedulerJdbcTemplate")
    private JdbcTemplate jdb;
    
    @Autowired
    @Qualifier("namedSchedulerJdbcTemplate")
    private NamedParameterJdbcTemplate db;

    @Autowired
    AppUtil util;
    
    
    public List<AppSmtp> getAllSmtp() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select id, protocol, host, port, smtp_user, smtp_pass, is_smtp_auth, is_tls_enable, is_ssl_enable, active, server_conn ");
        sql.append(" FROM smtp_settings ");
        sql.append(" where active=1 and server_conn=1 ");
        return db.query(sql.toString(), new BeanPropertyRowMapper(AppSmtp.class));
    }
    
    public void setSmtpConnection(int appSmtpId, int status) {
        StringBuilder sql = new StringBuilder();
        sql.append(" update smtp_settings set server_conn=:server_conn ");
        sql.append(" where id=:appSmtpId ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("server_conn", status);
        params.put("appSmtpId", appSmtpId);
        db.update(sql.toString(), params);
    }
    
    public void setActiveSmtpServerConn(int status) {
        StringBuilder sql = new StringBuilder();
        sql.append(" update smtp_settings set server_conn=:server_conn ");
        sql.append(" where active=1 ");
        
        Map<String, Object> params = new HashMap<>();
        params.put("server_conn", status);
        db.update(sql.toString(), params);
    }
    
}
