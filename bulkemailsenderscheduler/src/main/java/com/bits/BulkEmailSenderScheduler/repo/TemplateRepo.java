package com.bits.BulkEmailSenderScheduler.repo;

import com.bits.BulkEmailSenderScheduler.model.Template;
import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class TemplateRepo{

    @Autowired
    @Qualifier("msSchedulerJdbcTemplate")
    private JdbcTemplate jdb;
    
    @Autowired
    @Qualifier("namedSchedulerJdbcTemplate")
    private NamedParameterJdbcTemplate db;

    @Autowired
    AppUtil util;

    public List<Template> findAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT templates_id, name, description, html, text, created");
        sql.append(" FROM templates ");
        sql.append(" where order by name ");
        return db.query(sql.toString(), new BeanPropertyRowMapper(Template.class));
    }
    
    public int saveForId(Template template) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO templates( ");
        sql.append(" name, description, html, text, created)");
        sql.append(" VALUES ( ");
        sql.append(" :name, :description, :html, :text, :created)");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(template);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        db.update(sql.toString(), namedParameters, keyHolder);
        return keyHolder.getKey().intValue();
    }


}
