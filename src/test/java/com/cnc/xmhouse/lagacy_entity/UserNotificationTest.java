package com.cnc.xmhouse.lagacy_entity;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by zhuangjy on 2016/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class UserNotificationTest {
    @Autowired
    private BaseDao<UserNotification> baseDao;

    @Test
    public void save(){
        UserNotification userNotification = new UserNotification(1,"1");
        baseDao.save(userNotification);
    }

}