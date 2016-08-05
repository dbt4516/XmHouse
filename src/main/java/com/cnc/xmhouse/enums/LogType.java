package com.cnc.xmhouse.enums;

/**
 * Created by zhuangjy on 2016/7/31.
 */
public enum LogType {
    DEBUG("DEBUG",0),INFO("INFO",1),WARN("WARN",2),ERROR("ERROR",3),FATAL("FATAL",4);

    private String name;
    private Integer value;

    LogType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static Integer getTypeValue(String name){
        for(LogType logType:LogType.values())
            if(logType.getName().equals(name))
                return logType.getValue();
        return null;
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
}
