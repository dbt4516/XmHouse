package com.cnc.xmhouse.enums;

/**
 * Created by hongzhan on 2016/7/26.
 */
public enum UserExamStatus {

    FAILED(-1),
    NOT_YET_TAKEN(0),
    PASSED(1),
    EXEMPT(2),
    LEAVE(3),
    NOT_IN_EXAM_TIME(4),
    NOT_IN_EXAM_LIST(5);

    public int value;

    public static String statusName(int index){
        switch (index){
            case 0:
                return "未参加";
            case 1:
                return "通过";
            case 2:
                return "免考";
            case 3:
                return "请假";
            case -1:
                return "不合格";
        }
        return null;
    }

    UserExamStatus(int value){
        this.value=value;
    }
}
