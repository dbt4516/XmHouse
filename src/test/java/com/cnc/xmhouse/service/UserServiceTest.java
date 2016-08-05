package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.User;
import com.cnc.xmhouse.enums.Role;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangjy on 2016/7/26.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void loadAllUserIds(){
        System.out.println(userService.loadAllUserIds());
    }


    @Test
    public void searchUser(){
        List<User> users = userService.getsUserByName("b");
        System.out.println();
    }

    @Test
    public void getUsers(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
//        Assert.assertEquals(userService.getUsers(list).size(),2);
        System.out.println(userService.getUsers(list));

    }

    @Test
    public void updateUserAuth(){
        Integer id = 1;
        String auth = Role.USER.getName();
//        userService.updateUserAuth(id,auth);

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        userService.updateUserAuth(ids, Role.ADMIN.getName());
    }
}
