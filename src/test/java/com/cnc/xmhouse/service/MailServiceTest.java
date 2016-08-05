//package com.cnc.wsOnlineExamination.service;
//
//import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.mail.MailSender;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.*;
//
///**
// * Created by zhuangjy on 2016/8/1.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
//@WebAppConfiguration
//public class MailServiceTest {
//    @Autowired
//    private MailService mailService;
//
//    @Test
//    public void sendEmails() throws Exception {
//        List<String> list = new ArrayList<>();
//        list.add("249602015@qq.com");
//        list.add("zhuangjy@chinanetcenter.com");
//        list.add("suhq@chinanetcenter.com");
//        list.add("huangmh1@chinanetcenter.com");
//        list.add("linsy1@chinanetcenter.com");
//        list.add("wanghz1@chinanetcenter.com");
//        mailService.sendEmails("多线程测试","加入多线程后，异步发送速度更快!",list,"zhuangjy");
//        System.out.println("正常执行");
//        TimeUnit.SECONDS.sleep(10);
//    }
//
//}