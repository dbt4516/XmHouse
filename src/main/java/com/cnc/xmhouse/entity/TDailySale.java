package com.cnc.xmhouse.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/4.
 */
@Entity
@Table(name = "t_daily_sale", schema = "xmhouse", catalog = "")
public class TDailySale {
    private int id;
    private String location;
    private Timestamp date;
    private Integer suiteCount;
    private Double areaSum;

    public TDailySale(String location, Integer suiteCount, Double areaSum) {
        this.location = location;
        this.suiteCount = suiteCount;
        this.areaSum = areaSum;
    }

    public TDailySale(Timestamp date, Integer suiteCount, Double areaSum) {
        this.date = date;
        this.suiteCount = suiteCount;
        this.areaSum = areaSum;
    }

    public TDailySale(String location){
        this.location=location;
        this.date=Timestamp.valueOf(LocalTime.MIDNIGHT.atDate(LocalDate.now()));

    }

    public TDailySale() {
    }

    public static List<TDailySale> getList(String[] locations){
        return Arrays.stream(locations).map(l->new TDailySale(l)).collect(Collectors.toList());
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "suite_count")
    public Integer getSuiteCount() {
        return suiteCount;
    }

    public void setSuiteCount(Integer suiteCount) {
        this.suiteCount = suiteCount;
    }

    @Basic
    @Column(name = "area_sum")
    public Double getAreaSum() {
        return areaSum;
    }

    public void setAreaSum(Double areaSum) {
        this.areaSum = areaSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TDailySale that = (TDailySale) o;

        if (id != that.id) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (suiteCount != null ? !suiteCount.equals(that.suiteCount) : that.suiteCount != null) return false;
        if (areaSum != null ? !areaSum.equals(that.areaSum) : that.areaSum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (suiteCount != null ? suiteCount.hashCode() : 0);
        result = 31 * result + (areaSum != null ? areaSum.hashCode() : 0);
        return result;
    }
}
