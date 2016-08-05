//package com.cnc.wsOnlineExamination.dao;
//
//import com.cnc.xmhouse.lagacy_entity.bean.Notification;
//import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.validation.constraints.AssertTrue;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by zhuangjy on 2016/7/26.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
//@WebAppConfiguration
//public class RedisDaoTest {
//    @Autowired
//    RedisDao redisDao;
//
//    @Test
//    public void getNotification() throws Exception {
//        System.out.println(redisDao.getNotification("2"));
//    }
//
//    @Test
//    public void putNotification() throws Exception {
//        Notification notification = new Notification("second");
//        redisDao.putNotification(notification);
//    }
//
//    @Test
//    public void putUnreadCount(){
//        Integer userId = 0;
//        Long num = 10l;
//        System.out.println(redisDao.putUnreadCount(userId,num));
//    }
//
//    @Test
//    public void getUnreadCount(){
//        Integer userId1 = 0;
//        Integer userId2 = 1;
//        Assert.assertTrue(redisDao.getUnreadCount(0) == 10);
//        Assert.assertTrue(redisDao.getUnreadCount(1) == null);
//    }
//
//    @Test
//    public void plusReadToMany(){
//        List<Integer> userIds = new ArrayList<>();
//        userIds.add(0);
//        userIds.add(1);
//
//        redisDao.plusUnreadToMany(userIds,10l);
//        Assert.assertTrue(redisDao.getUnreadCount(0) == 20);
//        Assert.assertNull(redisDao.getUnreadCount(1));
//    }
//
//}