package com.cnc.xmhouse.util;

import com.cnc.xmhouse.lagacy_entity.bean.DateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuangjy on 2016/7/24.
 */
public class DateUtil {

    public static final String[] PATTERN = new String[]{"yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH", "yyyy-MM-dd"};

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(Object obj) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        if (Number.class.isAssignableFrom(obj.getClass())) {
            return new Date(Long.valueOf(obj.toString()));
        }
        String datestr = obj.toString();
        try {
            Date date = org.apache.commons.lang3.time.DateUtils.parseDate(datestr, PATTERN);
            return date;
        } catch (ParseException e) {
            logger.error("error parsing date [" + datestr + "] ", e);
            return null;
        }
    }

    public static Date dayStart(String datestr) {
        Date date = parse(datestr);
        return dayStart(date);
    }

    public static Date dayStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setCalendarDate(c, 0, 0, 0, 0);
        return c.getTime();
    }

    public static Date nextDayStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setCalendarDate(c, 0, 0, 0, 0);
        return plus(c.getTime(), Calendar.DAY_OF_YEAR, 1);
    }

    public static Date dayEnd(String datestr) {
        Date date = parse(datestr);
        return dayEnd(date);
    }

    public static Date dayEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setCalendarDate(c, 23, 59, 59, 999);
        return c.getTime();
    }

    public static Date plus(Date date, int field, int plus) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, plus);
        return c.getTime();
    }

    private static void setCalendarDate(Calendar c, int hour, int minute, int second, int milli) {
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, milli);
    }

    public static List<DateRange> splitDate(Date day, int interval) {
        Date start = dayStart(day);
        Date end = nextDayStart(day);
        Date tmp;
        List<DateRange> list = new ArrayList<DateRange>();
        while (start.compareTo(end) < 0) {
            tmp = plus(start, Calendar.MINUTE, interval);
            list.add(new DateRange(start, tmp));
            start = tmp;
        }
        return list;
    }

    public static int[] dif(Date start, Date end) {
        long t = (end.getTime() - start.getTime()) / 1000;
        int[] r = new int[4];
        r[0] = (int) t / 86400;//天
        r[1] = (int) (t % 86400) / 3600;//小时
        r[2] = (int) (t % 3600) / 60;//分
        r[3] = (int) t % 60;//秒
        return r;
    }
}
