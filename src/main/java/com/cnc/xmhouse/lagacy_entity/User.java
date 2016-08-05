package com.cnc.xmhouse.lagacy_entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by zhuangjy on 2016/7/22.
 */
//@Entity
public class User {
    private int id;
    private String number;
    private String auth;
    private String passWord;
    private String mail;
    private String userName;
    private Integer deptId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number")
    @NotEmpty
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "auth")
    @NotEmpty
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Basic
    @Column(name = "passWord")
    @NotEmpty
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Basic
    @Column(name = "mail")
    @NotEmpty
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "user_name")
    @NotEmpty
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "dept_id")
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (number != null ? !number.equals(user.number) : user.number != null) return false;
        if (auth != null ? !auth.equals(user.auth) : user.auth != null) return false;
        if (passWord != null ? !passWord.equals(user.passWord) : user.passWord != null) return false;
        if (mail != null ? !mail.equals(user.mail) : user.mail != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (deptId != null ? !deptId.equals(user.deptId) : user.deptId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (auth != null ? auth.hashCode() : 0);
        result = 31 * result + (passWord != null ? passWord.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (deptId != null ? deptId.hashCode() : 0);
        return result;
    }

    public User() {
    }

    public User(String number, String auth, String passWord, String mail, String userName, int deptId) {
        this.number = number;
        this.auth = auth;
        this.passWord = passWord;
        this.mail = mail;
        this.userName = userName;
        this.deptId = deptId;
    }

}
