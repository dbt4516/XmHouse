package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;

/**
 * Created by hongzhan on 2016/7/24.
 */
//@Entity
//@Table(name = "exam_question", schema = "ws_online_test")
public class ExamQuestion {
    private long id;
    private Long examId;
    private Long questionId;

    public ExamQuestion(){

    }

    public ExamQuestion(Long examId, Long questionId) {
        this.examId = examId;
        this.questionId = questionId;
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
    @Column(name = "exam_id", nullable = true)
    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    @Basic
    @Column(name = "question_id", nullable = true)
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamQuestion that = (ExamQuestion) o;

        if (id != that.id) return false;
        if (examId != null ? !examId.equals(that.examId) : that.examId != null) return false;
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (examId != null ? examId.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        return result;
    }
}
