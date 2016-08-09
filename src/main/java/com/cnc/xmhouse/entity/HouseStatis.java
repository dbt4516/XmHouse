package com.cnc.xmhouse.entity;

import java.util.Map;

/**
 * Created by hongzhan on 2016/8/8.
 */
public class HouseStatis {
    private int saleSuite;                  //总套数
    private long saleArea;                   //总成交面积

    private String mostSaleLoc;             //卖出最多套房的地区
    private int mostSaleLocSuite;          //卖出最多套房的地区的套数
    private int mostInAllRatio;             //卖出最多套房的地区的套数占总套数的比例
    private String leastSaleLoc;            //售出最少套房的地区

    public String getLeastSaleLoc() {
        return leastSaleLoc;
    }

    public void setLeastSaleLoc(String leastSaleLoc) {
        this.leastSaleLoc = leastSaleLoc;
    }

    public int getSaleSuite() {
        return saleSuite;
    }

    public void setSaleSuite(int saleSuite) {
        this.saleSuite = saleSuite;
    }

    public long getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(long saleArea) {
        this.saleArea = saleArea;
    }

    private String mostSaleDay;     //售出最多房子的日期
    private int mostSaleDaySuite;   //售出最多房子的那天的套数

    private String biggestLoc;     //售出最大房子的地区
    private int biggestArea;       //最大房子的面积
    private String biggesDate;     //最大的房子瑜何时售出
    private int avgArea;            //平均面积
    private String bigAvgRatio;     //最大面积为平均面积的倍数
    private String areaCommet;      //面积倍数评论

    public String getMostSaleLoc() {
        return mostSaleLoc;
    }

    public void setMostSaleLoc(String mostSaleLoc) {
        this.mostSaleLoc = mostSaleLoc;
    }

    public int getMostSaleLocSuite() {
        return mostSaleLocSuite;
    }

    public void setMostSaleLocSuite(int mostSaleLocSuite) {
        this.mostSaleLocSuite = mostSaleLocSuite;
    }

    public int getMostInAllRatio() {
        return mostInAllRatio;
    }

    public void setMostInAllRatio(int mostInAllRatio) {
        this.mostInAllRatio = mostInAllRatio;
    }

    public String getMostSaleDay() {
        return mostSaleDay;
    }

    public void setMostSaleDay(String mostSaleDay) {
        this.mostSaleDay = mostSaleDay;
    }

    public int getMostSaleDaySuite() {
        return mostSaleDaySuite;
    }

    public void setMostSaleDaySuite(int mostSaleDaySuite) {
        this.mostSaleDaySuite = mostSaleDaySuite;
    }

    public String getBiggestLoc() {
        return biggestLoc;
    }

    public void setBiggestLoc(String biggestLoc) {
        this.biggestLoc = biggestLoc;
    }

    public int getBiggestArea() {
        return biggestArea;
    }

    public void setBiggestArea(int biggestArea) {
        this.biggestArea = biggestArea;
    }

    public String getBiggesDate() {
        return biggesDate;
    }

    public void setBiggesDate(String biggesDate) {
        this.biggesDate = biggesDate;
    }

    public int getAvgArea() {
        return avgArea;
    }

    public void setAvgArea(int avgArea) {
        this.avgArea = avgArea;
    }

    public String getBigAvgRatio() {
        return bigAvgRatio;
    }

    public void setBigAvgRatio(String bigAvgRatio) {
        this.bigAvgRatio = bigAvgRatio;
    }

    public String getAreaCommet() {
        return areaCommet;
    }

    public void setAreaCommet(String areaCommet) {
        this.areaCommet = areaCommet;
    }
}
