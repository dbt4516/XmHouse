package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.entity.THouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hongzhan on 2016/8/9.
 */

@Service
public class HistoryService {

    @Autowired
    private BaseDao<THouse>houseDao;

    public List<THouse> getDay(long time){
        return houseDao.query("from THouse where ts>=? and ts<=?", new Timestamp(time), new Time(time+24*60*60*1000));
    }
}
