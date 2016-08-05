package com.cnc.xmhouse.lagacy_entity;

/**
 * Created by hongzhan on 2016/7/28.
 */

public class UserExamQuestionDetail {
    private long userExamid;
    private String title;
    private int type;
    private String option;
    private String correctAns;
    private String userAns;
    private byte isRight;

    public UserExamQuestionDetail(UserExamQuestion ueq,Question q){
        userExamid=ueq.getUserExamId();
        title=q.getTitle();
        type=q.getType();
        option=q.getOption();
        correctAns=q.getAns();
        userAns=ueq.getAns();
        isRight=ueq.getIsRight();
    }

    public long getUserExamid() {
        return userExamid;
    }

    public void setUserExamid(long userExamid) {
        this.userExamid = userExamid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }


    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getUserAns() {
        return userAns;
    }

    public void setUserAns(String userAns) {
        this.userAns = userAns;
    }

    public byte getIsRight() {
        return isRight;
    }

    public void setIsRight(byte isRight) {
        this.isRight = isRight;
    }
}
