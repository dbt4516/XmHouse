package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by zhuangjy on 2016/7/31.
 */
//@Entity
public class Log {
    private long id;
    private String user;
    private Integer type;
    private String content;
    private String time;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (id != log.id) return false;
        if (user != null ? !user.equals(log.user) : log.user != null) return false;
        if (type != null ? !type.equals(log.type) : log.type != null) return false;
        if (content != null ? !content.equals(log.content) : log.content != null) return false;
        if (time != null ? !time.equals(log.time) : log.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    public Log(String user, Integer type, String content, String time) {
        this.user = user;
        this.type = type;
        this.content = content;
        this.time = time;
    }

    public Log() {
    }

    public Log(String user, Integer type) {
        this.user = user;
        this.type = type;
    }
}
