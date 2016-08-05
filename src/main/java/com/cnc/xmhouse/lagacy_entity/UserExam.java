package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by hongzhan on 2016/7/24.
 */
//@Entity
//@Table(name = "user_exam", schema = "ws_online_test", catalog = "")
public class UserExam {
    private long id;
    private Integer userId;
    private Long examId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer score;
    private Integer status;
    private Integer failCount;

    public UserExam() {
    }

    public UserExam(Integer userId, Long examId, Integer status) {
        this.userId = userId;
        this.examId = examId;
        this.status = status;
    }

    public UserExam(Integer userId, Long examId, Timestamp startTime, Timestamp endTime, Integer score) {
        this.userId = userId;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "exam_id", nullable = true)
    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserExam userExam = (UserExam) o;

        if (id != userExam.id) return false;
        if (userId != null ? !userId.equals(userExam.userId) : userExam.userId != null) return false;
        if (examId != null ? !examId.equals(userExam.examId) : userExam.examId != null) return false;
        if (startTime != null ? !startTime.equals(userExam.startTime) : userExam.startTime != null) return false;
        if (endTime != null ? !endTime.equals(userExam.endTime) : userExam.endTime != null) return false;
        if (score != null ? !score.equals(userExam.score) : userExam.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (examId != null ? examId.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "fail_count")
    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }
}
