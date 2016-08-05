package com.cnc.xmhouse.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhuangjy on 2016/7/28.
 */
public class ValidUtil {
    /**
     * 验证传入字段不可为空
     * @param parms
     * @return  true不为空
     */
    public static boolean validEmpty(String ...parms){
        for(String param:parms){
            if(StringUtils.isEmpty(param))
                return false;
        }
        return true;
    }

    public static boolean validNull(Object ...parms){
        for(Object o:parms)
            if(o == null)
                return false;
        return true;
    }
}
