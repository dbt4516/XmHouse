package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.bean.Notification;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by zhuangjy on 2016/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;

    @Test
    public void notifyUser() throws Exception {
        Notification notification1 = new Notification("您有新的美团外卖订单");
        Notification notification2 = new Notification("您有新的百度外卖订单");
        Notification notification3 = new Notification("您有新消息");
        notificationService.notifyUser(notification1,2);
        notificationService.notifyUser(notification2,2);
        notificationService.notifyUser(notification3,2);
    }


    @Test
    public void userUnread() throws Exception {
        Long nums = notificationService.userUnread(1);
    }

    @Test
    public void lastNotification() throws Exception {
        List<Notification> list = notificationService.lastNotification(1,0,10);
        System.out.println(list);
    }

    @Test
    public void notifyAllUser() {
        Notification notification = new Notification("哈哈");
        notificationService.notifyAllUser(notification);
    }


    @Test
    public void allRead(){
        notificationService.allRead(1);
    }

    @Test
    public void list(){
        System.out.println(notificationService.list(2));
    }

}