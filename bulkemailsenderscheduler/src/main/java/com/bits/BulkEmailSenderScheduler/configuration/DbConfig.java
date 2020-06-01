/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.configuration;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author mdshahadat.sarker
 */
@Configuration
public class DbConfig {
    
    @Bean(name = "msSchDB")
    @Primary
    @ConfigurationProperties(prefix = "spring.dsmain")
    public DataSource mysqlSchedulerDataSource() {
            return DataSourceBuilder.create().build();
    }

    @Bean(name = "msSchedulerJdbcTemplate")
    public JdbcTemplate mysqlSchJdbcTemplate(@Qualifier("msSchDB") DataSource dsCustomMySQL) {
            return new JdbcTemplate(dsCustomMySQL);
    }
    
    @Bean(name = "namedSchedulerJdbcTemplate")
    public NamedParameterJdbcTemplate namedSchJdbcTemplate(@Qualifier("msSchDB") DataSource dsCustomMySQL) {
            return new NamedParameterJdbcTemplate(dsCustomMySQL);
    }
    
    
    
//    @Bean(name = "h2DB")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.mql")
//    public DataSource h2SchedulerDataSource() {
//            return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "h2SchedulerJdbcTemplate")
//    public JdbcTemplate mysqlH2JdbcTemplate(@Qualifier("h2DB") DataSource dsCustomMySQL) {
//            return new JdbcTemplate(dsCustomMySQL);
//    }
}

