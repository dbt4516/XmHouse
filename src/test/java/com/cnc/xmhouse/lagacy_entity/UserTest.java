//package com.cnc.wsOnlineExamination.entity;
//
//import com.cnc.xmhouse.dao.BaseDao;
//import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.jws.soap.SOAPBinding;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//
///**
// * Created by zhuangjy on 2016/7/21.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
//@WebAppConfiguration
//@EnableTransactionManagement
//public class UserTest {
//    @Autowired
//    private BaseDao<User> baseDao;
//
//    @Test
//    public void insertTest(){
//        User user = new User("001","user","123456","zhuangjy2@chinanetcenter.com","zhuangjy2",1);
//        baseDao.save(user);
//    }
//
//    @Test
//    public void selectTest(){
//        String mail = "zhuangjy@chinanetcenter.com";
//        Map<String,Object> hs = new HashMap<>();
//        hs.put("mail",mail);
//        User user = (User) baseDao.uniqueResult("From User WHERE mail=:mail",hs);
//        System.out.println(user);
//    }
//
//}