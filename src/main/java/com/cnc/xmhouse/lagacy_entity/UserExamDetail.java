package com.cnc.xmhouse.lagacy_entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hongzhan on 2016/7/26.
 */

public class UserExamDetail {
    private int uid;
    private Long eid;
    private String eName;
    private String cName;
    private Integer score;
    private Timestamp ust;
    private Timestamp uet;
    private String dName;
    private Integer leaderId;
    private Integer status;
    private Long ueid;
    private Timestamp est;
    private Timestamp eet;
    private int cType;
    private String uName;
    private Timestamp eCreateTime;
    private Integer cid;
    private String mail;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Timestamp geteCreateTime() {
        return eCreateTime;
    }

    public void seteCreateTime(Timestamp eCreateTime) {
        this.eCreateTime = eCreateTime;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Timestamp getUst() {
        return ust;
    }

    public void setUst(Timestamp ust) {
        this.ust = ust;
    }

    public Timestamp getUet() {
        return uet;
    }

    public void setUet(Timestamp uet) {
        this.uet = uet;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUeid() {
        return ueid;
    }

    public void setUeid(Long ueid) {
        this.ueid = ueid;
    }

    public Timestamp getEst() {
        return est;
    }

    public void setEst(Timestamp est) {
        this.est = est;
    }

    public Timestamp getEet() {
        return eet;
    }

    public void setEet(Timestamp eet) {
        this.eet = eet;
    }

    public int getcType() {
        return cType;
    }

    public void setcType(int cType) {
        this.cType = cType;
    }

    static public List<UserExamDetail> parseForQuery(List<Object> src) {
        List<UserExamDetail> ret = new LinkedList<>();
        for (Object objs : src) {
            Object[] obj = (Object[]) objs;
            UserExamDetail ued = new UserExamDetail();
            ued.setUid((Integer) obj[0]);
            ued.setEid(((BigInteger) obj[1]).longValue());
            ued.seteName((String) obj[2]);
            ued.setcName((String) obj[3]);
            ued.setScore((Integer) obj[4]);
            ued.setUst((Timestamp) obj[5]);
            ued.setUet((Timestamp) obj[6]);
            ued.setStatus((Integer) obj[7]);
            ued.setdName((String) obj[8]);
            ued.setLeaderId((Integer) obj[9]);
            ued.setUeid(((BigInteger)obj[10]).longValue());
            ued.setEst((Timestamp)obj[11]);
            ued.setEet((Timestamp)obj[12]);
            ued.setcType((Byte)obj[13]);
            ued.setuName((String)obj[14]);
            ued.seteCreateTime((Timestamp)obj[15]);
            ued.setCid((Integer) obj[16]);
            ued.setMail((String) obj[17]);
            ret.add(ued);
        }
        return ret;
    }

}
