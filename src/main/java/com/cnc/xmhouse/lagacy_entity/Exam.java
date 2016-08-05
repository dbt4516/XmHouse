package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by hongzhan on 2016/7/24.
 */
//@Entity
public class Exam {
    private long id;
    private Timestamp startTime;
    private String name;
    private Timestamp endTime;
    private Integer duration;
    private Integer passMark;
    private Integer questionCount;
    private Double passRate;
    private Integer courseId;
    private Integer creator;
    private Timestamp createTime;
    private String courseName;
    private Integer isMock;

    public Exam(){

    }

    public Exam(Integer creator,Integer courseId, long startTime, String name, long endTime, Integer duration, Integer passMark, Integer questionCount,Integer is_mock) {
        this.creator=creator;
        this.courseId = courseId;
        this.startTime = new Timestamp(startTime);
        this.name = name;
        this.endTime = new Timestamp(endTime);
        this.duration = duration;
        this.passMark = passMark;
        this.questionCount = questionCount;
        this.createTime=new Timestamp(System.currentTimeMillis());
        this.isMock=is_mock;
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
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "duration", nullable = true)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "pass_mark", nullable = true)
    public Integer getPassMark() {
        return passMark;
    }

    public void setPassMark(Integer passMark) {
        this.passMark = passMark;
    }

    @Basic
    @Column(name = "question_count", nullable = true)
    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    @Basic
    @Column(name = "pass_rate", nullable = true, precision = 0)
    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }

    @Basic
    @Column(name = "course_id", nullable = true)
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Transient
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exam exam = (Exam) o;

        if (id != exam.id) return false;
        if (startTime != null ? !startTime.equals(exam.startTime) : exam.startTime != null) return false;
        if (name != null ? !name.equals(exam.name) : exam.name != null) return false;
        if (endTime != null ? !endTime.equals(exam.endTime) : exam.endTime != null) return false;
        if (duration != null ? !duration.equals(exam.duration) : exam.duration != null) return false;
        if (passMark != null ? !passMark.equals(exam.passMark) : exam.passMark != null) return false;
        if (questionCount != null ? !questionCount.equals(exam.questionCount) : exam.questionCount != null)
            return false;
        if (passRate != null ? !passRate.equals(exam.passRate) : exam.passRate != null) return false;
        if (courseId != null ? !courseId.equals(exam.courseId) : exam.courseId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (passMark != null ? passMark.hashCode() : 0);
        result = 31 * result + (questionCount != null ? questionCount.hashCode() : 0);
        result = 31 * result + (passRate != null ? passRate.hashCode() : 0);
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "creator")
    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "is_mock")
    public Integer getIsMock() {
        return isMock;
    }

    public void setIsMock(Integer isMock) {
        this.isMock = isMock;
    }
}
