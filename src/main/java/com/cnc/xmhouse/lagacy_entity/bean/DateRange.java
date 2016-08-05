package com.cnc.xmhouse.lagacy_entity.bean;

import java.util.Date;

/**
 * Created by zhuangjy on 2016/7/24.
 */
public class DateRange {

    private Date start;
    private Date end;

    public DateRange(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
