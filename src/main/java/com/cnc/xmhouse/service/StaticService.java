package com.cnc.xmhouse.service;

import com.cnc.xmhouse.base.Const;
import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.entity.Highchart;
import com.cnc.xmhouse.entity.HouseStatis;
import com.cnc.xmhouse.entity.TDailySale;
import com.cnc.xmhouse.entity.THouse;
import com.cnc.xmhouse.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
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
    private Function<Timestamp,String> ts2DateStringCN = TimeUtil::ts2DateStringCN;
    private Function<Timestamp,String> ts2DateString = TimeUtil::ts2DateString;
    private Function<Long,String>long2DateString= TimeUtil::long2DateString;

    public HouseStatis getText(long start, long end) {
        HouseStatis hs = new HouseStatis();
        List<TDailySale> locSum = objDao
                .querySql("select location,sum(suite_count),sum(area_sum) from t_daily_sale where date >= ? and date <= ?  GROUP BY location;", new Timestamp(start), new Timestamp(end))
                .stream().map(a -> (Object[]) a)
                .map(arr -> new TDailySale((String) arr[0], ((BigDecimal) arr[1]).intValue(), (Double) arr[2]))
                .collect(Collectors.toList());
        TDailySale mostSale = locSum.stream().reduce((a, b) -> a.getSuiteCount() > b.getSuiteCount() ? a : b).orElse(new TDailySale());
        hs.setMostSaleLoc(mostSale.getLocation());
        hs.setMostSaleLocSuite(mostSale.getSuiteCount());
        TDailySale sum=locSum.stream().reduce((a, b) -> new TDailySale(a.getLocation(), a.getSuiteCount() + b.getSuiteCount(), a.getAreaSum() + b.getAreaSum())).orElse(new TDailySale());
        hs.setSaleSuite(sum.getSuiteCount());
        hs.setSaleArea(Math.round(sum.getAreaSum()));
        hs.setMostInAllRatio((int) (Math.round((mostSale.getSuiteCount()+0.0)/sum.getSuiteCount()*100)));
        hs.setLeastSaleLoc(locSum.stream().min(Comparator.comparing(a->a.getSuiteCount())).orElse(new TDailySale()).getLocation());

        List<TDailySale> dailySum = objDao
                .querySql("select date,sum(suite_count),sum(area_sum) from t_daily_sale where date >= ? and date <= ?  GROUP BY date;", new Timestamp(start), new Timestamp(end))
                .stream().map(a -> (Object[]) a)
                .map(arr -> new TDailySale((Timestamp) arr[0], ((BigDecimal) arr[1]).intValue(), (Double) arr[2]))
                .collect(Collectors.toList());
        TDailySale mostSaleDay = dailySum.stream().max(Comparator.comparing((a -> a.getSuiteCount()))).orElse(new TDailySale());
        hs.setMostSaleDay(ts2DateStringCN.apply(mostSaleDay.getDate()));
        hs.setMostSaleDaySuite(mostSaleDay.getSuiteCount());

        List<THouse> house = houseDao.query("from THouse where ts>=? and ts<=?", new Timestamp(start), new Time(end));
        THouse biggest = house.stream().max(Comparator.comparing(a -> a.getArea())).orElse(new THouse());
        hs.setBiggestLoc(biggest.getLocation());
        hs.setBiggesDate(ts2DateStringCN.apply(biggest.getTs()));
        hs.setBiggestArea((int) Math.round(biggest.getArea()));
        Double areaSum = house.stream().map(a -> a.getArea()).reduce(0d, (a, b) -> a + b);
        hs.setAvgArea((int) Math.round(areaSum / house.size()));
        hs.setBigAvgRatio(String.format("%.2f",hs.getBiggestArea()/(0.0+hs.getAvgArea())));
        if(hs.getBiggestArea()/(0.0+hs.getAvgArea())>2){
            hs.setAreaCommet("%s倍，筒子们这是什么，将近100倍啊！");
        }else{
           hs.setAreaCommet("似乎差别也没有很大，毕竟能买房的都是壕。");
        }
        return hs;
    }

    //成交面积分布图
    public Highchart getAreaDis(long start,long end){
        List<String> xAxisList=new LinkedList<>();
        List<Long>data=new LinkedList<>();
        List<THouse> house = houseDao.query("from THouse where ts>=? and ts<=?", new Timestamp(start), new Time(end));
        for(int i=0;i<Const.areaRange.length;i++){
            int sa=Const.areaRange[i];
            int ea=i==Const.areaRange.length-1?Integer.MAX_VALUE:Const.areaRange[i+1];
            long count = house.stream().filter(a -> a.getArea() > sa && a.getArea() <= ea).count();
            data.add(count);
            xAxisList.add(String.format("%d %s 平米",sa,ea==Integer.MAX_VALUE?"以上":"- "+ea));
        }

        Map<String,Object>serie=new HashMap<>();
        serie.put("name","面积");
        serie.put("data",data);
        List<Object> series = Collections.singletonList(serie);
        return new Highchart(xAxisList,series,"column");
    }

    //饼图
    public Highchart getSalePie(long start,long end){
        List<Map<String, Object>> data = objDao
                .querySql("select location,sum(suite_count),sum(area_sum) from t_daily_sale where date >= ? and date <= ?  GROUP BY location;", new Timestamp(start), new Timestamp(end))
                .stream().map(a -> (Object[]) a)
                .map(arr -> new TDailySale((String) arr[0], ((BigDecimal) arr[1]).intValue(), (Double) arr[2]))
                .map(d -> {
                    Map<String, Object> kv = new HashMap<String, Object>();
                    kv.put("name", d.getLocation());
                    kv.put("y", d.getSuiteCount());
                    return kv;
                }).collect(Collectors.toList());
        Map<String,Object>kv=new HashMap<>();
        kv.put("name","售出套房数");
        kv.put("data",data);
        List<Object>series=new LinkedList<>();
        series.add(kv);
        return new Highchart(null,series,"pie");
    }

    //趋势曲线
    public Highchart getTrendLine(long start,long end){
        List<TDailySale> daily = dailySaleDao.query("from TDailySale where date>=? and date<=?", new Timestamp(start), new Timestamp(end));
        List<Object>series=new LinkedList<>();
        List<String> xAxis = daily.stream().map(a -> a.getDate()).distinct().map(a -> ts2DateString.apply(a)).collect(Collectors.toList());
        for(String l: Const.locations){
            List<Integer> collect = daily.stream().filter(a -> a.getLocation().equals(l)).map(a -> a.getSuiteCount()).collect(Collectors.toList());
            Map<String,Object>line=new HashMap<>();
            line.put("name",l);
            line.put("data",collect);
            series.add(line);
        }
        return new Highchart(xAxis,series,"line");
    }

}
