package com.cnc.xmhouse.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hongzhan on 2016/8/8.
 */
public class TimeUtil {
    static public String ts2DateStringCN(Timestamp ts){
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(ts.getTime()));
    }
  static public String ts2DateString(Timestamp ts){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(ts.getTime()));
    }

      static public String long2DateString(long ts){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(ts));
    }


}
