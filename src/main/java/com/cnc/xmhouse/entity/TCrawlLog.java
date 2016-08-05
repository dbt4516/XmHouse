package com.cnc.xmhouse.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/8/4.
 */
@Entity
@Table(name = "t_crawl_log", schema = "xmhouse", catalog = "")
public class TCrawlLog {

    private int id;
    private Timestamp ts;
    private int code;
    private String html;

    public TCrawlLog(){

    }

    public TCrawlLog(Timestamp ts, int code, String html) {
        this.ts = ts;
        this.code = code;
        this.html = html;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ts")
    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    @Basic
    @Column(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "html")
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCrawlLog tCrawlLog = (TCrawlLog) o;

        if (id != tCrawlLog.id) return false;
        if (code != tCrawlLog.code) return false;
        if (ts != null ? !ts.equals(tCrawlLog.ts) : tCrawlLog.ts != null) return false;
        if (html != null ? !html.equals(tCrawlLog.html) : tCrawlLog.html != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ts != null ? ts.hashCode() : 0);
        result = 31 * result + code;
        result = 31 * result + (html != null ? html.hashCode() : 0);
        return result;
    }
}
