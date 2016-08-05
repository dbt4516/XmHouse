package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Question;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by zhuangjy on 2016/7/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Test
    public void save() throws Exception {

    }

    @Test
    public void saveAuto() throws Exception {

    }

    @Test
    public void getQuestions() throws Exception {
        List<Question> list1 = questionService.getQuestions(1);
        List<Question> list2 = questionService.getQuestions(2);
        List<Question> list3 = questionService.getQuestions(3);
        for(Question question:list1)
            System.out.println(question);
        for(Question question:list2)
            System.out.println(question);
        for(Question question:list3)
            System.out.println(question);
    }

    @Test
    public void getQuestion() throws Exception {
        Question question = questionService.getQuestion(15l);
        System.out.println(question);
    }

    @Test
    public void offLine() throws Exception{
        questionService.offline(16l);
    }

    @Test
    public void getQuestionsCount(){
        System.out.println(questionService.getQuestionsCount(1));
        System.out.println(questionService.getQuestionsCount(2));
        System.out.println(questionService.getQuestionsCount(3));
    }

    @Test
    public void deleteQuestion() {
        questionService.deleteQuestion(1l);
    }


}