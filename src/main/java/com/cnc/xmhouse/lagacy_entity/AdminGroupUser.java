package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;

/**
 * Created by zhuangjy on 2016/7/28.
 */
@Entity
@Table(name = "admin_group_user", schema = "ws_online_test", catalog = "")
public class AdminGroupUser implements Admin{
    private long id;
    private Integer adminGroupId;
    private Integer userId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "admin_group_id")
    public Integer getAdminGroupId() {
        return adminGroupId;
    }

    public void setAdminGroupId(Integer adminGroupId) {
        this.adminGroupId = adminGroupId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminGroupUser that = (AdminGroupUser) o;

        if (id != that.id) return false;
        if (adminGroupId != null ? !adminGroupId.equals(that.adminGroupId) : that.adminGroupId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (adminGroupId != null ? adminGroupId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    public AdminGroupUser(Integer adminGroupId, Integer userId) {
        this.adminGroupId = adminGroupId;
        this.userId = userId;
    }

    public AdminGroupUser() {
    }
}
