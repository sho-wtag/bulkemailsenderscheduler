package com.bits.BulkEmailSenderScheduler.model;

import java.io.Serializable;
import java.util.Date;

public class Template implements Serializable{
    private Long templates_id;
    private String name;
    private String description;
    private String html;
    private String text;
    private Date created;

    public Template() {
    }

    public Template(String name, String html, String description) {
        this.name = name;
        this.html = html;
        this.description = description;
    }

    public Long getTemplates_id() {
        return templates_id;
    }

    public void setTemplates_id(Long templates_id) {
        this.templates_id = templates_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
