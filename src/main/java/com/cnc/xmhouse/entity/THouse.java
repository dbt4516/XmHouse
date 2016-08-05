package com.cnc.xmhouse.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/4.
 */
@Entity
@Table(name = "t_house", schema = "xmhouse", catalog = "")
public class THouse {
    private int id;
    private String location;
    private Double area;
    private Timestamp ts;

    public THouse(String location,double area){
        this.location=location;
        this.area=area;
        this.ts=new Timestamp(new Date().getTime());
    }

    public THouse() {
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
    @Column(name = "area")
    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    @Basic
    @Column(name = "ts")
    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        THouse tHouse = (THouse) o;

        if (id != tHouse.id) return false;
        if (location != null ? !location.equals(tHouse.location) : tHouse.location != null) return false;
        if (area != null ? !area.equals(tHouse.area) : tHouse.area != null) return false;
        if (ts != null ? !ts.equals(tHouse.ts) : tHouse.ts != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (ts != null ? ts.hashCode() : 0);
        return result;
    }
}
