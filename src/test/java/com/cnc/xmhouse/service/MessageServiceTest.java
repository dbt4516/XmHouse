package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Message;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by huangmh1 on 2016/7/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Test
    public  void courseIdQueryPage() throws Exception{
        List<Message> messageList=messageService.messageQueryByCourseId(2,0,4);
        System.out.println("---------------"+messageList.size());
    }

    @Test
    public  void userIdQueryPage() throws Exception{
        List<Message> messageList=messageService.messageQueryByCourseId(1,0,4);
        System.out.println("---------------"+messageList.size());
    }

    @Test
    public void courseIdQuery ()throws Exception{
       List<Message> messageList=messageService.messageQueryByCourseId(3);
        System.out.println("CourseId:");
        for(Message each:messageList) System.out.println(each.toString());

    }


    @Test
    public void userIdQuery ()throws Exception{
        System.out.println("UserId:");
        List<Message> messageList= messageService.messageQueryByUserId(5);
        for(Message each:messageList) System.out.println(each.toString());

    }

    @Test
    public void record ()throws Exception{
       System.out.println( messageService.messageCommit(7,"老师讲得非常好",8));

    }




}