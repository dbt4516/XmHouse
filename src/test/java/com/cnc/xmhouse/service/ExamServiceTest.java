package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Exam;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by linsy1 on 2016/7/31.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class ExamServiceTest {

    @Autowired
    private ExamService examService;

    @Test
    public void getExamsByCourseId()throws Exception{

        List<Exam> exams = examService.getExamsByCourseId(1);

        for(Exam e:exams)
            System.out.println(e.getName());
    }
 }
