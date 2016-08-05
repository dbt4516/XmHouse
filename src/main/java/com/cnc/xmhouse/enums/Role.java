package com.cnc.xmhouse.enums;

/**
 * Created by zhuangjy on 2016/7/31.
 */
public enum Role {
    USER("ROLE_USER"),ADMIN("ROLE_ADMIN"),SADMIN("S_ADMIN");
    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
