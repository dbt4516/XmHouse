package com.cnc.xmhouse.enums;


/**
 * Created by zhuangjy on 2016/7/24.
 */
public enum QuestionType {
    SINGLE_CHOICE("单选题",0),
    MULTIPLE_CHOICE("多选题",1),
    FILE_BLANK("填空题",2),
    JUDGE_QUESTION("判断题",3),
    ;
    private String name;
    private Integer value;

    QuestionType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static QuestionType getQuestionTypeByValue(Integer value){
        for(QuestionType type:QuestionType.values()){
            if(type.value == value)
                return type;
        }
        return null;
    }

    public static QuestionType getQuestionTypeByName(String name){
        for(QuestionType type:QuestionType.values()){
            if(type.name.equals(name))
                return type;
        }
        return null;
    }
}
