package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.lagacy_entity.Question;
import com.cnc.xmhouse.enums.QuestionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuangjy on 2016/7/24.
 */
@Service
public class QuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);
    @Autowired
    private BaseDao<Question> baseDao;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CourseService courseService;

    public void save(Question question) {
        baseDao.save(question);
    }

    public boolean saveAuto(Integer courseId, QuestionType type, String title, String option, String ans) throws IOException {
        Map<String, String> mapOption = null;
        if (type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE) {
            //组装option
            mapOption = new TreeMap<>();
            for (String opt : option.split(" "))
                mapOption.put(opt.split("\\.")[0], opt.split("\\.")[1]);
        }
        StringWriter sw = new StringWriter();
      //  mapper.writeValue(sw, mapOption);
        //解析ans
        ans = regexAnswer(ans);
        //默认上线
        Question question = new Question(courseId, type.getValue(), title, sw.toString(), ans, 1);
        baseDao.save(question);
        return true;
    }

    public List<Question> getQuestions(Integer courseId,Integer page, Integer limit) {
        String hql = "FROM Question";
        List<Question> questions = null;
        if(courseId!=null) {
            hql += " WHERE courseId=:courseId";
            Map<String, Object> hs = new HashedMap();
            hs.put("courseId",courseId);
            questions = baseDao.queryWithStartLimit(hql,hs,page,limit);

        }else{
            questions = baseDao.queryWithStartLimit(hql, null, page, limit);
        }
        iteratorCourseName(questions);
        return questions;
    }

    public List<Question> getOnlineQuestions(Integer page, Integer limit) {
        String hql = "FROM Question WHERE isOnline=1";
        List<Question> questions = baseDao.queryWithStartLimit(hql, null, page, limit);
        iteratorCourseName(questions);
        return questions;
    }

    public Long getQuestionsCount(Integer courseId) {
        if(courseId != null){
            Map<String,Object> hs = new HashedMap();
            hs.put("courseId",courseId);
            String hql = "SELECT COUNT(*) FROM Question WHERE courseId=:courseId";
            return baseDao.queryHqlCount(hql,hs);
        }
        String hql = "SELECT COUNT(*) FROM Question";
        return baseDao.queryHqlCount(hql);
    }

    public List<Question> getQuestions(Integer courseId) {
        List<Question> questions;
        if(courseId != null){
            Map<String,Object> hs = new HashedMap();
            hs.put("courseId",courseId);
            String hql = "FROM Question WHERE courseId=:courseId";
            questions = baseDao.query(hql,hs);
        }else {
            String hql = "FROM Question";
            questions = baseDao.query(hql);
        }
        iteratorCourseName(questions);
        return questions;
    }

    public Question getQuestion(Long questionId) {
        String hql = "FROM Question WHERE id=:id";
        Map<String, Object> hs = new HashMap<>();
        hs.put("id", questionId);
        Question question = (Question) baseDao.uniqueResult(hql, hs);
        setCourseName(question);
        return question;
    }

    public boolean deleteQuestion(long questionId) {
        baseDao.deleteById(questionId, Question.class);
        return true;
    }

    public void offline(Long questionId) {
        String hql = "UPDATE Question SET isOnline=0 WHERE id=:id";
        Map<String, Object> hs = new HashMap<>();
        hs.put("id", questionId);
        baseDao.executeUpdateHqlByMap(hql, hs);
    }

    public void outPutQuestions(File file) {
        List<Question> questions = getQuestions(null);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Question question : questions) {
                Course course = courseService.getCourseById(question.getCourseId());
                bufferedWriter.write(course.getName());
                bufferedWriter.newLine();
                bufferedWriter.write(QuestionType.getQuestionTypeByValue(question.getType()).getName());
                bufferedWriter.newLine();
                bufferedWriter.write(question.getTitle());
                bufferedWriter.newLine();
                //单选题或者多选题要输出选项
                if (question.getType() == 0 || question.getType() == 1) {
                    Map<String, String> optionMap = null;//mapper.readValue(question.getOption(), Map.class);
                    bufferedWriter.write(parseToString(optionMap));
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("[" + question.getAns() + "]");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

    public String regexAnswer(String answer) {
        //答案 正则提取
        String regex = "(?<=\\[).*(?=\\])";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(answer);
        if (m.find()) {
            return m.group();
        } else
            return null;
    }

    public String parseToString(Map<String, String> option) {
        StringBuffer sb = new StringBuffer("");
        Iterator iterator = option.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append(key + "." + value + " ");
        }
        return sb.toString();
    }

    //遍历设置question name
    public void iteratorCourseName(List<Question> questions) {
        for (Question question : questions) {
            setCourseName(question);
        }
    }

    public void setCourseName(Question question) {
        Course course = courseService.getCourseById(question.getCourseId());
        question.setCourse(course.getName());
    }
}
