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
 * Created by zhuangjy on 2016/7/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class QuestionTest {
    @Autowired
    private BaseDao<Question> baseDao;

    @Test
    public void testAddQuestion() {

        //Integer courseId, Byte type, String title, String option, String ans)
        Question question1 = new Question(1, 1, "title", "option", "ans",1);
//        Question question2 = new Question(1, 2, "title", "ans");

        baseDao.save(question1);
//        baseDao.save(question2);
    }
}