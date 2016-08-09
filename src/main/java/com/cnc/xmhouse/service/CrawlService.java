package com.cnc.xmhouse.service;

import com.cnc.xmhouse.base.Const;
import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.entity.TCrawlLog;
import com.cnc.xmhouse.entity.TDailySale;
import com.cnc.xmhouse.entity.THouse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.fluent.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by hongzhan on 2016/8/4.
 */

@Service
@EnableScheduling
public class CrawlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);


    @Autowired
    private BaseDao<TCrawlLog>logDao;
    @Autowired
    private BaseDao<TDailySale>dailySaleDao;
    @Autowired
    private BaseDao<THouse>houseDao;

    @Scheduled(cron = "0 */10 8-19 * * *")
    public void crawl() {
        try {
            List<TDailySale>res=TDailySale.getList(Const.locations);
            String html = Request.Get("http://cloud.xm.gov.cn:88/xmzf/zf/newspfj.jsp")
                    .execute().returnContent().asString().trim();

            logDao.add(new TCrawlLog(new Timestamp(new Date().getTime()),200,isNew(html)?html:"--"));
            Document document = Jsoup.parse(html);

            List<Element> rows = document.select("tr:gt(0)").subList(0, 2);
            for(int i=0;i<rows.size();i++){
                List<Element> elements = rows.get(i).select("td:gt(0)");
                for(int j=0;j<elements.size();j++){
                    if(i==0){
                        res.get(j).setSuiteCount(Integer.valueOf(elements.get(j).text()));
                    }else{
                        res.get(j).setAreaSum(Double.valueOf(elements.get(j).text()));
                    }
                }
            }
            res.stream().forEach(h->{
                TDailySale ori = dailySaleDao.queryGetOne(" From TDailySale where location=? and date=?", h.getLocation(), h.getDate());
                if(ori==null){
                    dailySaleDao.save(h);
                }else {
                    h.setId(ori.getId());
                    dailySaleDao.save(h);
                    int suiteGap=h.getSuiteCount()-ori.getSuiteCount();
                    double areaGap=h.getAreaSum()-ori.getAreaSum();
                    for(int i=0;i<suiteGap;i++){
                        houseDao.add(new THouse(h.getLocation(),areaGap/suiteGap));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            logDao.add(new TCrawlLog(new Timestamp(new Date().getTime()),-1, ExceptionUtils.getStackTrace(e)));

        }
    }

    public boolean isNew(String html){
        List<TCrawlLog> tCrawlLogs = logDao.queryWithStartLimit("From TCrawlLog where html!='--' order by ts desc", 0, 1);
        if(tCrawlLogs.size()>0){
            if(html.equals(tCrawlLogs.get(0).getHtml()))
                return false;
        }
        return true;
    }

}
