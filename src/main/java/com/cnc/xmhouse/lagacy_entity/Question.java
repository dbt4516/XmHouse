package com.cnc.xmhouse.lagacy_entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by zhuangjy on 2016/7/22.
 */
//@Entity
public class Question {
    private long id;
    private Integer courseId;
    private int type;
    private String title;
    private String option;
    private String ans;
    private int isOnline = 1; // 0不上线 1上线 默认上线
    private Double passRate = 0.0d;
    private String course;

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
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "title")
    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "option")
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Basic
    @Column(name = "ans")
    @NotEmpty
    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @Basic
    @Column(name = "is_online")
    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    @Basic
    @Column(name = "pass_rate")
    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }

    @Transient
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Question(Integer courseId, int type, String title, String option, String ans, Integer isOnline) {
        this.courseId = courseId;
        this.type = type;
        this.title = title;
        this.option = option;
        this.ans = ans;
        this.isOnline = isOnline;
    }

    public Question(Integer courseId, int type, String title, String ans,Integer isOnline) {
        this.courseId = courseId;
        this.type = type;
        this.title = title;
        this.ans = ans;
        this.isOnline = isOnline;
    }

    public Question() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (type != question.type) return false;
        if (isOnline != question.isOnline) return false;
        if (courseId != null ? !courseId.equals(question.courseId) : question.courseId != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (option != null ? !option.equals(question.option) : question.option != null) return false;
        if (ans != null ? !ans.equals(question.ans) : question.ans != null) return false;
        return passRate != null ? passRate.equals(question.passRate) : question.passRate == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (option != null ? option.hashCode() : 0);
        result = 31 * result + (ans != null ? ans.hashCode() : 0);
        result = 31 * result + isOnline;
        result = 31 * result + (passRate != null ? passRate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", option='" + option + '\'' +
                ", ans='" + ans + '\'' +
                ", isOnline=" + isOnline +
                ", passRate=" + passRate +
                ", course=" + course +
                '}';
    }
}
