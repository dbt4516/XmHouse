package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.entity.THouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hognzhan on 2016/8/5.
 */
@Service
public class HouseService {
    @Autowired
    private BaseDao<THouse> houseDao;

    public List<THouse> getToday(){
        Timestamp midnight = Timestamp.valueOf(LocalTime.MIDNIGHT.atDate(LocalDate.now()));
        return houseDao.query("From THouse where ts>?", midnight);
    }

    public List<THouse>getThatDay(long thatDay){
        Timestamp midnight = new Timestamp(thatDay);
        Timestamp nextMidNight=new Timestamp(thatDay+24*60*60*1000);
        return houseDao.query("From THouse where ts>? and ts<?", midnight,nextMidNight);
    }
}
