/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.model;

/**
 *
 * @author mdshahadat.sarker
 */
public class AppSmtp {
    private int id;
    private String protocol;
    private String host;
    private String port;
    private String smtp_user;
    private String smtp_pass;
    private boolean is_smtp_auth;
    private boolean is_tls_enable;
    private boolean is_ssl_enable;
    private boolean active;
    private boolean server_conn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSmtp_user() {
        return smtp_user;
    }

    public void setSmtp_user(String smtp_user) {
        this.smtp_user = smtp_user;
    }

    public String getSmtp_pass() {
        return smtp_pass;
    }

    public void setSmtp_pass(String smtp_pass) {
        this.smtp_pass = smtp_pass;
    }

    public boolean isIs_smtp_auth() {
        return is_smtp_auth;
    }

    public void setIs_smtp_auth(boolean is_smtp_auth) {
        this.is_smtp_auth = is_smtp_auth;
    }

    public boolean isIs_tls_enable() {
        return is_tls_enable;
    }

    public void setIs_tls_enable(boolean is_tls_enable) {
        this.is_tls_enable = is_tls_enable;
    }

    public boolean isIs_ssl_enable() {
        return is_ssl_enable;
    }

    public void setIs_ssl_enable(boolean is_ssl_enable) {
        this.is_ssl_enable = is_ssl_enable;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isServer_conn() {
        return server_conn;
    }

    public void setServer_conn(boolean server_conn) {
        this.server_conn = server_conn;
    }
    
}
