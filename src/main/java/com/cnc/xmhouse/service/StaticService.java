package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.entity.HouseStatis;
import com.cnc.xmhouse.entity.TDailySale;
import com.cnc.xmhouse.entity.THouse;
import com.cnc.xmhouse.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hongzhan on 2016/8/8.
 */

@Service
public class StaticService {

    @Autowired
    private BaseDao<TDailySale> dailySaleDao;
    @Autowired
    private BaseDao<Object> objDao;
    @Autowired
    private  BaseDao<THouse>houseDao;
    private Function<Timestamp,String>ts2DateString= TimeUtil::ts2DateString;

    public HouseStatis get(long start, long end) {
        HouseStatis hs = new HouseStatis();
        List<TDailySale> dailyLocSum = objDao
                .querySql("select location,sum(suite_count),sum(area_sum) from t_daily_sale where date >= ? and date <= ?  GROUP BY location;", new Timestamp(start), new Timestamp(end))
                .stream().map(a -> (Object[]) a)
                .map(arr -> new TDailySale((String) arr[0], ((BigDecimal) arr[1]).intValue(), (Double) arr[2]))
                .collect(Collectors.toList());
        TDailySale mostSale = dailyLocSum.stream().reduce((a, b) -> a.getSuiteCount() > b.getSuiteCount() ? a : b).orElse(new TDailySale());
        hs.setMostSaleLoc(mostSale.getLocation());
        hs.setMostSaleLocSuite(mostSale.getSuiteCount());
        TDailySale sum=dailyLocSum.stream().reduce((a, b) -> new TDailySale(a.getLocation(), a.getSuiteCount() + b.getSuiteCount(), a.getAreaSum() + b.getAreaSum())).orElse(new TDailySale());
        hs.setSaleSuite(sum.getSuiteCount());
        hs.setSaleArea(Math.round(sum.getAreaSum()));
        hs.setMostInAllRatio((int) (Math.round((mostSale.getSuiteCount()+0.0)/sum.getSuiteCount()*100)));

        List<TDailySale> dailySum = objDao
                .querySql("select date,sum(suite_count),sum(area_sum) from t_daily_sale where date >= ? and date <= ?  GROUP BY date;", new Timestamp(start), new Timestamp(end))
                .stream().map(a -> (Object[]) a)
                .map(arr -> new TDailySale((Timestamp) arr[0], ((BigDecimal) arr[1]).intValue(), (Double) arr[2]))
                .collect(Collectors.toList());
        TDailySale mostSaleDay = dailySum.stream().max(Comparator.comparing((a -> a.getSuiteCount()))).orElse(new TDailySale());

        hs.setMostSaleDay(ts2DateString.apply(mostSaleDay.getDate()));
        hs.setMostSaleDaySuite(mostSaleDay.getSuiteCount());

        List<THouse> house = houseDao.query("from THouse where ts>=? and ts<=?", new Timestamp(start), new Time(end));
        THouse biggest = house.stream().max(Comparator.comparing(a -> a.getArea())).orElse(new THouse());
        hs.setBiggestLoc(biggest.getLocation());
        hs.setBiggesDate(ts2DateString.apply(biggest.getTs()));
        hs.setBiggestArea((int) Math.round(biggest.getArea()));
        Double areaSum = house.stream().map(a -> a.getArea()).reduce(0d, (a, b) -> a + b);
        hs.setAvgArea((int) Math.round(areaSum / house.size()));
        hs.setBigAvgRatio(String.format("%.1f",hs.getBiggestArea()/(0.0+hs.getAvgArea())));
        if(hs.getBiggestArea()/(0.0+hs.getAvgArea())>2){
            hs.setAreaCommet("%s倍，筒子们这是什么，将近100倍啊！");
        }else{
           hs.setAreaCommet("似乎差别也没有很大，毕竟能买房的都是壕。");
        }

        System.out.println();
        return null;
    }

}
