package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huangmh1 on 2016/7/22.
 */
//@Entity
public class Message {
    private long id;
    private Integer courseId;
    private String content;
    private Integer userId;
    private Timestamp messageTime;
    private String userName;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_id")
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "message_time")
    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }

    @Transient
    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public Message(Integer courseId, String content, Integer userId, Timestamp messageTime, String userName) {
        this.courseId = courseId;
        this.content = content;
        this.userId = userId;
        this.messageTime = messageTime;
        this.userName = userName;
    }

    public Message(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (courseId != null ? !courseId.equals(message.courseId) : message.courseId != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (userId != null ? !userId.equals(message.userId) : message.userId != null) return false;
        if (messageTime != null ? !messageTime.equals(message.messageTime) : message.messageTime != null) return false;
        return userName != null ? userName.equals(message.userName) : message.userName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (messageTime != null ? messageTime.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", messageTime=" + messageTime +
                ", userName=" + userName +
                '}';
    }

}
