package com.cnc.xmhouse.lagacy_entity.bean;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by zhuangjy on 2016/7/26.
 */
public class Notification {
    private String id;
    private String content;
    private Timestamp time;

    public Notification(String content) {
        this.id = UUID.randomUUID().toString().replaceAll("-","");
        this.content = content;
    }

    public Notification(String content,Timestamp time){
        this.content = content;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
