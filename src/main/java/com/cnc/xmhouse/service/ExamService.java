package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.*;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hongzhan on 2016/7/24.
 */
@Service
public class ExamService {

    @Autowired
    private BaseDao<Exam> examBaseDao;
    @Autowired
    private BaseDao<ExamQuestion> examQuestionBaseDao;
    @Autowired
    private BaseDao<UserExamQuestion> ueqDao;
    @Autowired
    private BaseDao<Question> questionDao;
    @Autowired
    private BaseDao<UserExam> userExamDao;
    @Autowired
    private BaseDao<Object> uedDao;

    static final private String SPLITTER = ";";

    public long getExamsCounts(List<Course> courseList) {
        List<Integer> cids = courseList.stream().map(c -> c.getId()).collect(Collectors.toList());
        String cidsString = Joiner.on(" , ").join(cids);
        if (cidsString.equals(""))
            cidsString = "-1";
        return examBaseDao.queryHqlCount("select count (*) from Exam where courseId in (" + cidsString + ")");
    }

    public List<Exam> list(List<Course> courseList, int page, int perpage) {
        List<Integer> cids = courseList.stream().map(c -> c.getId()).collect(Collectors.toList());
        String cidsString = Joiner.on(" , ").join(cids);
        if (cidsString.equals(""))
            cidsString = "-1";
        return examBaseDao.queryWithStartLimit("from Exam where courseId in (" + cidsString + ")", page, perpage);
    }


    public boolean addExam(int creator, long startTime, long endTime, String examName, int passMark, int duration, int courseId, int questionCount,Integer is_mock) {
        Exam exam = new Exam(creator, courseId, startTime, examName, endTime, duration, passMark, questionCount,is_mock);

        return (examBaseDao.save(exam) != null);
    }


    public boolean editExam(long examId, long startTime, long endTime, String examName, int passMark, int duration, int questionCount,Integer is_mock) {
        return examBaseDao.executeUpdateHql("update Exam set startTime=?,endTime=?,name=?,duration=?,questionCount=?,passMark=?,isMock=?  where id=?",
                new Timestamp(startTime), new Timestamp(endTime), examName, duration, questionCount, passMark, is_mock==1? 1:0, examId) != 0;
    }

    public boolean examHasBennTaken(long examId){
        return userExamDao.find(" from UserExam where examId=? and status!=0",examId).size()!=0;
    }

    public Double calculatePassRate(long examId){
        Exam exam= (Exam) examBaseDao.findUnique("id",examId,Exam.class);
        if(new Date().after(new Date(exam.getEndTime().getTime()))){ //exam is not yet ended
            long passed=uedDao.queryHqlCount("select count(*) from UserExam where status=1");
            long failed=uedDao.queryHqlCount("select count(*) from UserExam where status=-1");
            double passRate=(passed+failed==0)?1:(passed+0.0)/(passed+failed);
            exam.setPassRate(passRate);
            examBaseDao.update(exam);
            return passRate;
        }else{
            return null;
        }
    }

    public Exam getExam(long examId) {
        return (Exam) examBaseDao.findUnique("id", examId, Exam.class);
    }

    public boolean setExamQuestion(long examId, List<Long> questions) {
        if (questions == null) {
            return false;
        }
        boolean ret=true;
        for (long q : questions) {
            if (examQuestionBaseDao.query("from ExamQuestion where examId=? and questionId=?",examId,q).size() ==0)
                    ret= null != examQuestionBaseDao.save(new ExamQuestion(examId, q));
        }
        return ret;
    }


    public List<Question> getExamQuestions(long examId) {
        List<ExamQuestion> eqs = examQuestionBaseDao.query("from ExamQuestion where examId=?", examId);
        List<Question> ret = new LinkedList<>();
        for (ExamQuestion eq : eqs) {
            Question q = (Question) questionDao.findUnique("id", eq.getQuestionId(), Question.class);
            q.setAns(null);
            ret.add(q);
        }
        return ret;
    }


    public List<UserExamQuestionDetail> getUserExamQuestions(long userExamId) {
        List<UserExamQuestion> ueqs = ueqDao.query(" From UserExamQuestion where userExamId=?", userExamId);
        List<UserExamQuestionDetail> ret = new LinkedList<>();
        for (UserExamQuestion ueq : ueqs) {
            Question q = (Question) questionDao.findUnique("id", ueq.getQuestionId(), Question.class);
            ret.add(new UserExamQuestionDetail(ueq, q));
        }
        return ret;
    }

    public boolean questionIsInUse(long questionId) {
        List<ExamQuestion> query = examQuestionBaseDao.query(" from ExamQuestion where questionId=?", questionId);
        return query != null && query.size() > 0;
    }
    /**
     * 通过课程id查找考试列表
     * */
    public List<Exam> getExamsByCourseId(Integer courseId){

        String hql = "FROM Exam WHERE course_id=:course_id";
        Map<String, Object> map = new HashMap<>();
        map.put("course_id",courseId);
        List<Exam> list = examBaseDao.query(hql,map);
        return list;
    }

}
