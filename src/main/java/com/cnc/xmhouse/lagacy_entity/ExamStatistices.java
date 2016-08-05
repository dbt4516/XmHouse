package com.cnc.xmhouse.lagacy_entity;

import java.sql.Timestamp;

/**
 * Created by linsy1 on 2016/7/30.
 */
public class ExamStatistices {
    private Long eid;
    private String eName;
    private Integer count;
    private Integer failCount;
    private Integer passCount;
    private Double passRate;
    private Integer leaveCount;
    private Timestamp ust;
    private Timestamp uet;
    private String dName;

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Integer getPassCount() {
        return passCount;
    }

    public void setPassCount(Integer passCount) {
        this.passCount = passCount;
    }

    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }

    public Integer getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(Integer leaveCount) {
        this.leaveCount = leaveCount;
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

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {this.eid = eid;}

    public String getdName() {return dName;}

    public void setdName(String dName) {this.dName = dName;}

    public ExamStatistices(Long eid, String eName, Integer count, Integer failCount, Integer passCount,
                           Double passRate, Integer leaveCount, Timestamp ust, Timestamp uet) {
        this.eid = eid;
        this.eName = eName;
        this.count = count;
        this.failCount = failCount;
        this.passCount = passCount;
        this.passRate = passRate;
        this.leaveCount = leaveCount;
        this.ust = ust;
        this.uet = uet;
        this.dName = null;
    }
    public ExamStatistices(Long eid, String eName, Integer count, Integer failCount, Integer passCount,
                           Double passRate, Integer leaveCount, Timestamp ust, Timestamp uet,String dName) {
        this.eid = eid;
        this.eName = eName;
        this.count = count;
        this.failCount = failCount;
        this.passCount = passCount;
        this.passRate = passRate;
        this.leaveCount = leaveCount;
        this.ust = ust;
        this.uet = uet;
        this.dName = dName;
    }
    public ExamStatistices(){

    }
    @Override
    public String toString(){
        return "ExamStatistices[eid:"+eid+",eName:"+eName+",dName:"+dName+",count:"+count+",failCount:"+failCount+
                ",passCount:"+passCount+",passRate:"+passRate+",leaveCount:"+leaveCount+
                ",ust:"+ust+",uet"+uet+"]";
    }


}
