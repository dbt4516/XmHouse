//package com.cnc.wsOnlineExamination.service;
//
//import com.cnc.xmhouse.lagacy_entity.Log;
//import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
//import org.hibernate.annotations.LazyToOneOption;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import ch.qos.logback.classic.Logger;
//import org.hibernate.annotations.common.util.impl.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//
///**
// * Created by huangmh1 on 2016/7/28.
// */
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
//@WebAppConfiguration
//public class LogServiceTest {
//    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceTest.class);
//    @Autowired
//    private LogService logService;
//
//    @Test
//    public void parseToDb() {
//        logService.parseToDb();
//    }
//
//    @Test
//    public void loggerDel(){
//        logService.loggerDel();
//    }
//
//    @Test
//    public void find(){
//        Log log = new Log();
////        log.setType();
//    }
//
//    public static void main(String[] args) {
//        LOGGER.info("haa");
//    }
//}
//
