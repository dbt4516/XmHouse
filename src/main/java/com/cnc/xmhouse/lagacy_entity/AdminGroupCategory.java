package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;

/**
 * Created by zhuangjy on 2016/7/28.
 */
//@Entity
//@Table(name = "admin_group_category", schema = "ws_online_test", catalog = "")
public class AdminGroupCategory implements Admin{
    private long id;
    private Integer adminGroupId;
    private Integer categoryId;

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
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminGroupCategory that = (AdminGroupCategory) o;

        if (id != that.id) return false;
        if (adminGroupId != null ? !adminGroupId.equals(that.adminGroupId) : that.adminGroupId != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (adminGroupId != null ? adminGroupId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        return result;
    }

    public AdminGroupCategory() {
    }

    public AdminGroupCategory(Integer adminGroupId, Integer categoryId) {
        this.adminGroupId = adminGroupId;
        this.categoryId = categoryId;
    }
}
