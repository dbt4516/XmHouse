package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;

/**
 * Created by zhuangjy on 2016/7/28.
 */
//@Entity
//@Table(name = "admin_group", schema = "ws_online_test")
public class AdminGroup implements Admin{
    private int id;
    private String name;
    private String desc;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminGroup that = (AdminGroup) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }

    public AdminGroup() {
    }

    public AdminGroup(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
