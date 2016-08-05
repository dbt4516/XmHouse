package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.ExamStatistices;
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
public class ExamStatisServiceTest {
    @Autowired
    private ExamStatisService examStatisService;

    @Test
    public void queryExamList()throws Exception{
        List<ExamStatistices> list = examStatisService.getExamStatistices(Long.parseLong("1467306061000"),Long.parseLong("1469984461000"),null,null);
        if(null != list)
            for(ExamStatistices exam:list){System.out.println(exam);}
    }
    @Test
    public void queryExamDeptList()throws Exception{

        List<ExamStatistices> list = examStatisService.getDeptStatistices(null,null,1);
        if(null != list)
            for(ExamStatistices exam:list){System.out.println(exam);}
    }

}
