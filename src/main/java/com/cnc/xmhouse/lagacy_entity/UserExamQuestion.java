package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;

/**
 * Created by hongzhan on 2016/7/24.
 */
//@Entity
//@Table(name = "user_exam_question", schema = "ws_online_test", catalog = "")
public class UserExamQuestion {
    private long id;
    private long userExamId;
    private long questionId;
    private String ans;
    private byte isRight;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_exam_id", nullable = false)
    public long getUserExamId() {
        return userExamId;
    }

    public void setUserExamId(long userExamId) {
        this.userExamId = userExamId;
    }

    @Basic
    @Column(name = "question_id", nullable = false)
    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "ans", nullable = true, length = -1)
    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @Basic
    @Column(name = "is_right", nullable = false)
    public byte getIsRight() {
        return isRight;
    }

    public void setIsRight(byte isRight) {
        this.isRight = isRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserExamQuestion that = (UserExamQuestion) o;

        if (id != that.id) return false;
        if (userExamId != that.userExamId) return false;
        if (questionId != that.questionId) return false;
        if (isRight != that.isRight) return false;
        if (ans != null ? !ans.equals(that.ans) : that.ans != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userExamId ^ (userExamId >>> 32));
        result = 31 * result + (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (ans != null ? ans.hashCode() : 0);
        result = 31 * result + (int) isRight;
        return result;
    }
}
