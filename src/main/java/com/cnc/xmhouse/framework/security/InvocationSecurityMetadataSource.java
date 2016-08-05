//package com.cnc.wsOnlineExamination.framework.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//
///**
// * Created by zhuangjy on 2016/7/21.
// */
//public class InvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
//    private UrlMatcher urlMatcher = new AntUrlPathMatcher();
//    private static Map<String,Collection<ConfigAttribute>> resourceMap = null;
//
//    //tomcat启动时实例化一次
//    public InvocationSecurityMetadataSource() {
//        loadResourceDefine();
//    }
//
//    //tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系
//    private void loadResourceDefine() {
//        resourceMap = new HashMap<>();
//        //所有非匿名用户可以访问的
//        ConfigAttribute user = new SecurityConfig("ROLE_USER");
//        ConfigAttribute admin = new SecurityConfig("ROLE_ADMIN");
//        ConfigAttribute sadmin = new SecurityConfig("ROLE_SADMIN");
//
//        Collection<ConfigAttribute> noAnonymousAuth = new ArrayList<>();
//        noAnonymousAuth.add(user);
//        noAnonymousAuth.add(admin);
//        noAnonymousAuth.add(sadmin);
//
//        Collection<ConfigAttribute> onlySuperAdmin = new ArrayList<>();
//        onlySuperAdmin.add(sadmin);
//
//        Collection<ConfigAttribute> adminAndSuperAdmin = new ArrayList<>();
//        adminAndSuperAdmin.add(sadmin);
//        adminAndSuperAdmin.add(admin);
//
//        //所有人都可以访问
//        resourceMap.put("/**", noAnonymousAuth);
//
//        //只有管理员和超级管理员能访问的
//        resourceMap.put("/course/save",adminAndSuperAdmin);
//        resourceMap.put("/course/update",adminAndSuperAdmin);
//        resourceMap.put("/course/remove/**",adminAndSuperAdmin);
//        resourceMap.put("/course/online/**",adminAndSuperAdmin);
//        resourceMap.put("/course/downline/**",adminAndSuperAdmin);
//        resourceMap.put("/course/removeCourseFromCategory/**",adminAndSuperAdmin);
//
//        resourceMap.put("/exam/add",adminAndSuperAdmin);
//        resourceMap.put("/exam/showUpdate/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/setExamUser/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/addExam/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/setExamUser/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/setExamUsersByEmails/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/setExamExemptUsersByEmails/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/setExamLeaveUsersByEmails/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/reJudge/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/editExam",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/setExamQuestion",adminAndSuperAdmin);
//
//        resourceMap.put("/exam/ajax/examStatistics/result/list",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/examStatistics/exam/list",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/examStatistics/dept/list",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/examStatistics/exam/person/**",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/examStatistics/ajax/baseQueryReport",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/examStatistics/ajax/examQuery",adminAndSuperAdmin);
//        resourceMap.put("/exam/ajax/examStatistics/ajax/deptQuery",adminAndSuperAdmin);
//
//        resourceMap.put("/question/**",adminAndSuperAdmin);
//
//        //只有超级管理员
//        resourceMap.put("/category/**",onlySuperAdmin);
//        resourceMap.put("/super/**",onlySuperAdmin);
//        resourceMap.put("/log/**",onlySuperAdmin);
//    }
//
//
//    //参数是要访问的url，返回这个url对于的所有权限（或角色）
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        // 将参数转为url
//        String url = ((FilterInvocation)object).getRequestUrl();
//        Iterator<String> iterator = resourceMap.keySet().iterator();
//        while (iterator.hasNext()) {
//            String resURL = iterator.next();
//            if (urlMatcher.pathMatchesUrl(resURL, url)) {
//                return resourceMap.get(resURL);
//            }
//        }
//        return null;
//    }
//    public boolean supports(Class clazz) {
//        return true;
//    }
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//}
