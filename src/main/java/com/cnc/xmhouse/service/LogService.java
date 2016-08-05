package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.Log;
import com.cnc.xmhouse.enums.LogType;
import com.cnc.xmhouse.util.FileUtil;
import com.google.common.base.Joiner;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangmh1 on 2016/7/27.
 */
@Service
@PropertySource(value = {"classpath:service.properties"})
@Configuration
public class LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    @Autowired
    private BaseDao<Log> baseDao;
    @Value("${log.path}")
    private String path;

    public List<Log> find(Log log, String startTime, String endTime, Integer page, Integer limit) {
        Map<String, Object> hs = new HashedMap();
        StringBuffer hql = new StringBuffer("FROM Log WHERE 1=1");
        if (StringUtils.isNotEmpty(log.getUser())) {
            hql.append(" AND user=:user");
            hs.put("user", log.getUser());
        }
        if (log.getType() != null) {
            hql.append(" AND type=:type");
            hs.put("type", log.getType());
        }
        if (StringUtils.isNotEmpty(startTime)) {
            hql.append(" AND time>=:startTime");
            hs.put("startTime", startTime);
        }
        if (StringUtils.isNotEmpty(endTime)) {
            hql.append(" AND time<=:endTime");
            hs.put("endTime", endTime);
        }
        return baseDao.queryWithStartLimit(hql.toString(), hs,page, limit);
    }

    public long findCount(Log log,String startTime,String endTime){
        Map<String, Object> hs = new HashedMap();
        StringBuffer hql = new StringBuffer("SELECT COUNT(*) FROM Log WHERE 1=1");
        if (StringUtils.isNotEmpty(log.getUser())) {
            hql.append(" AND user=:user");
            hs.put("user", log.getUser());
        }
        if (log.getType() != null) {
            hql.append(" AND type=:type");
            hs.put("type", log.getType());
        }
        if (StringUtils.isNotEmpty(startTime)) {
            hql.append(" AND time>=:startTime");
            hs.put("startTime", startTime);
        }
        if (StringUtils.isNotEmpty(endTime)) {
            hql.append(" AND time<=:endTime");
            hs.put("endTime", endTime);
        }
        return baseDao.queryHqlCount(hql.toString(),hs);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void parseToDb() {
        File file = FileUtil.getYesterdayLog(path);
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                String time = s[0] + " " + s[1];
                Integer type = LogType.getTypeValue(s[3]);
                String user = "";
                //解析邮箱提取发送人
                Pattern regex = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
                Matcher matcher = regex.matcher(line);
                if (matcher.find())
                    user = matcher.group(0);
                Log log = new Log(user, type, line, time);
                baseDao.save(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void loggerDel() {
        LOGGER.info("开始删除过期日志数据...");
        String sql = "SELECT id FROM `log` WHERE (SELECT datediff(now(),`time`) > 10)";
        List<Integer> ids = baseDao.querySqlNoneType(sql);
        if(ids.size() != 0) {
            String id = Joiner.on(" , ").join(ids);
            sql = "DELETE FROM `log` WHERE `id` IN (" + id + ")";
            baseDao.executeUpdateSql(sql);
            LOGGER.info("过期日志删除成功!");
        }else {
            LOGGER.info("没有发现过期日志,没有进行任何删除工作!");
        }
    }
}
