package com.cnc.xmhouse.framework.security;

/**
 * Created by zhuangjy on 2016/7/21.
 */
public interface UrlMatcher{
    Object compile(String paramString);
    boolean pathMatchesUrl(Object paramObject, String paramString);
    String getUniversalMatchPattern();
    boolean requiresLowerCaseUrl();
}