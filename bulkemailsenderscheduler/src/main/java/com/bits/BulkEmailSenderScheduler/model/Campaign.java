package com.bits.BulkEmailSenderScheduler.model;

import java.io.Serializable;
import java.util.Date;

public class Campaign implements Serializable{
    private Long campaign_id;
    private String cid;
    private String tempName;
    private String tempDescription;
    private String tempHtml;
    private String name;
    private String description;
    private Integer template_id;
    private String reply_from;
    private String reply_to;
    private String subject;
    private String html;
    private String html_prepared;
    private Short status;
    private Date last_check;
    private String check_status;
    private Short tracking_disabled;
    private int scheduled_type;
    private String scheduled;
    private Date status_change;
    private Integer delivered;
    private Integer opened;
    private Integer clicks;
    private Integer unsubscribed;
    private Integer bounced;
    private Integer complained;
    private Date created;

    
    private String segment_ids;
    
    public Long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(Long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getTempDescription() {
        return tempDescription;
    }

    public void setTempDescription(String tempDescription) {
        this.tempDescription = tempDescription;
    }

    public String getTempHtml() {
        return tempHtml;
    }

    public void setTempHtml(String tempHtml) {
        this.tempHtml = tempHtml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    public String getReply_from() {
        return reply_from;
    }

    public void setReply_from(String reply_from) {
        this.reply_from = reply_from;
    }

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml_prepared() {
        return html_prepared;
    }

    public void setHtml_prepared(String html_prepared) {
        this.html_prepared = html_prepared;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getLast_check() {
        return last_check;
    }

    public void setLast_check(Date last_check) {
        this.last_check = last_check;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public Short getTracking_disabled() {
        return tracking_disabled;
    }

    public void setTracking_disabled(Short tracking_disabled) {
        this.tracking_disabled = tracking_disabled;
    }

    public int getScheduled_type() {
        return scheduled_type;
    }

    public void setScheduled_type(int scheduled_type) {
        this.scheduled_type = scheduled_type;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public Date getStatus_change() {
        return status_change;
    }

    public void setStatus_change(Date status_change) {
        this.status_change = status_change;
    }

    public Integer getDelivered() {
        return delivered;
    }

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Integer getUnsubscribed() {
        return unsubscribed;
    }

    public void setUnsubscribed(Integer unsubscribed) {
        this.unsubscribed = unsubscribed;
    }

    public Integer getBounced() {
        return bounced;
    }

    public void setBounced(Integer bounced) {
        this.bounced = bounced;
    }

    public Integer getComplained() {
        return complained;
    }

    public void setComplained(Integer complained) {
        this.complained = complained;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSegment_ids() {
        return segment_ids;
    }

    public void setSegment_ids(String segment_ids) {
        this.segment_ids = segment_ids;
    }

}
