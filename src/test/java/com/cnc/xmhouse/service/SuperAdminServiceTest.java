package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by zhuangjy on 2016/7/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class SuperAdminServiceTest {


    @Autowired
    private SuperAdminService superAdminService;

/*    @Test
    public void createAdminGroup() throws Exception {
        AdminGroup adminGroup = new AdminGroup("name","group");
        superAdminService.createAdminGroup(adminGroup);
    }

    @Test
    public void addAdminGroupMember() throws Exception {
        AdminGroupUser adminGroupUser = new AdminGroupUser(1,1);
        superAdminService.addAdminGroupMember(adminGroupUser);
    }
*/
    @Test
    public void getUsersCount() throws Exception{
//        System.out.println(superAdminService.getUsersCount());
    }
    @Test
    public void isSameMail() throws Exception{
//        System.out.println(superAdminService.isSameMail("zhuangjy2@chinanetcenter.com"));
    }
/*    @Test
    public void getUsers() throws Exception{
        for(User user:superAdminService.getUsers(1,1)){
            System.out.println(user.getMail());
        }
    }
    @Test
    public void removeUser() throws Exception {
        superAdminService.removeUser(1);
    }*/
    @Test
    public void isAdminHasRightToCourse() throws Exception{
        System.out.println(superAdminService.isAdminHasRightToCourse(2,2));
    }
    @Test
    public void getCategoryIdWhichAdminHasRight()throws Exception{

        List<Course> courses = superAdminService.getCoursesWhichAdminHasRight(1);
        for(Course course:courses)
            System.out.println(course);
    }

    @Test
    public void listAdminGroup() throws Exception {
        Assert.assertEquals(superAdminService.listAdminGroup().size(),3);
    }


    @Test
    public void isUserInOtherGroup() {
        Integer groupId1 = 31;
        //不在其他组
        int userId1 = 2;
        //在其他组
        int userId2 = 1;

        Assert.assertTrue(!superAdminService.isUserInOtherGroup(userId1,groupId1));
        Assert.assertTrue(superAdminService.isUserInOtherGroup(userId2,groupId1));
    }
}