package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.*;
import com.cnc.xmhouse.enums.UserExamStatus;
import javafx.util.Pair;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hongzhan on 2016/7/28.
 */

@Service
public class UserExamService {

    @Autowired
    private BaseDao<Exam> examBaseDao;
    @Autowired
    private BaseDao<UserExamQuestion> ueqDao;
    @Autowired
    private BaseDao<UserExam> userExamDao;
    @Autowired
    private BaseDao<Object> uedDao;
    @Autowired
    private ExamService examService;
    @Autowired
    private UserExamQuestionService ueqService;
    @Autowired
    private BaseDao<User> userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ExamMailService examMailService;

    public boolean setExamUsers(long examId, List<Integer> users) {
        examMailService.notifyExamWithEmailInCreate(examId,users);
        boolean hasDuplicate=users.stream()
                .filter(id->userDao.query("from UserExam where examId=? and userId=?",examId,id).size()==0)
                .collect(Collectors.toList()).size()>0;

        boolean ret= users.stream().filter(id->userDao.query("from UserExam where examId=? and userId=?",examId,id).size()==0)
                .distinct().map(id->userExamDao.save(new UserExam(id, examId, 0)) != null)
                .reduce((b1,b2)->b1 && b2).orElse(true);
        return ret && hasDuplicate;
    }

    public List<Integer>emails2UserIds(List<String>emails){
        return emails.stream()
                .map(m -> ((User) userDao.findUnique("mail", m, User.class)).getId())
                .collect(Collectors.toList());
    }


    public List<UserExamDetail> getExamByLeaderId(int userId, Integer status, int page, int perpage) {
        if (status == null) {
            return UserExamDetail.parseForQuery(uedDao.querySql("select * from user_exam_detail where leader_id=? order by eid desc limit ?,? ",
                    userId, page * perpage, perpage));
        } else {
            return UserExamDetail.parseForQuery(uedDao.querySql("select * from user_exam_detail where leader_id=? and status=? order by eid desc limit ?,?  ",
                    userId, status, page * perpage, perpage));
        }
    }


    public Pair<Integer, List<UserExamDetail>> getExamByUserId(int userId, Integer status, int page, int perpage) {
        if (status != null) {
            Pair<Integer, List<Object>> p = uedDao.countQuerySql("select * from user_exam_detail where uid=? and status=? limit ?,?  ",
                    userId, status, page * perpage, perpage);
            return new Pair<>(p.getKey(), UserExamDetail.parseForQuery(p.getValue()));
        }
        Pair<Integer, List<Object>> p = uedDao.countQuerySql("select * from user_exam_detail where uid=? limit ?,?  ",
                userId, page * perpage, perpage);
        return new Pair<>(p.getKey(), UserExamDetail.parseForQuery(p.getValue()));

    }

    public void resetUserExam(int examId, List<Integer> users) {
        for (int u : users) {
            List<UserExam> rs = userExamDao.query("from UserExam where examId=? and userId=?", examId, u);
            if (rs.size() > 0) {
                UserExam r = rs.get(0);
                r.setStatus(UserExamStatus.NOT_YET_TAKEN.value);
                r.setFailCount(0);
                r.setStartTime(null);
                r.setEndTime(null);
                userExamDao.update(r);
                ueqService.clearOldUserExamResult(r.getId());
            }
        }
    }


    public boolean exempt(long examId, List<Integer> users) {
        return setUserExamStatus(examId, users, UserExamStatus.EXEMPT.value);
    }

    public boolean leave(long examId, List<Integer> users) {
        return setUserExamStatus(examId, users, UserExamStatus.LEAVE.value);
    }

    private boolean setUserExamStatus(long examId, List<Integer> users, int status) {
        for (int u : users) {
            List<UserExam> rs = userExamDao.query("from UserExam where examId=? and userId=?", examId, u);
            if (rs.size() > 0) {
                UserExam r = rs.get(0);
                r.setStatus(status);
                userExamDao.update(r);
            }else if(status==UserExamStatus.EXEMPT.value || status==UserExamStatus.LEAVE.value){
                userExamDao.save(new UserExam(u,examId,status));
            }
        }
        return true;
    }


    @Transactional
    public Pair<Long, List<UserExam>> getUserExamsByExam(long examId, Integer status, int page, int perpage) {
        List<SimpleExpression> condition = new LinkedList<>();
        condition.add(Restrictions.eq("examId", examId));
        if (status != null) {
            condition.add(Restrictions.eq("status", status));
        }
//        List<UserExam> userExams = userExamDao.queryWithStartLimit("From UserExam where examId=? and status=?", (page - 1) * perpage, perpage, examId, status);
//        Long count = userExamDao.queryHqlCount("select count(*) from UserExam where examId=? and status=?", examId, status);
        return userExamDao.critetiriaPaging(UserExam.class, condition, page, perpage);
    }



    public UserExamStatus userStartExam(long examId, int userId) {
        Exam exam = examService.getExam(examId);
        if (exam.getStartTime().after(new Date()) || exam.getEndTime().before(new Date())) {
            return UserExamStatus.NOT_IN_EXAM_TIME;
        }

        List<UserExam> rs = userExamDao.query("from UserExam where examId=? and userId=?", examId, userId);
        if (rs.size() > 0) {
            UserExam r = rs.get(0);

            if (r.getStatus() == UserExamStatus.LEAVE.value) {
                return UserExamStatus.LEAVE;
            } else if (r.getStatus() == UserExamStatus.EXEMPT.value) {
                return UserExamStatus.EXEMPT;
            } else if (r.getStatus() == UserExamStatus.PASSED.value) {
                return UserExamStatus.PASSED;
            }
            // here we have not_yet_taken & fail user
            // re-enter exam -> fail the old one
            if (r.getStartTime() != null && r.getEndTime() == null) {
                userFinishExam(examId, userId, null);
            }
            ueqService.clearOldUserExamResult(r.getId());
            r.setStartTime(new Timestamp(new Date().getTime()));
            r.setEndTime(null);
            userExamDao.update(r);
            return UserExamStatus.NOT_YET_TAKEN;
        } else {
            return UserExamStatus.NOT_IN_EXAM_LIST;
        }
    }

    public UserExam userFinishExam(long examId, int userId, List<UserExamQuestion> ansQues) {

        long endTime = new Date().getTime();
        List<UserExam> rs = userExamDao.query("From UserExam where userId=? and examId=?", userId, examId);
        if (rs.size() == 1) {
            UserExam userExam = rs.get(0);
            ueqService.clearOldUserExamResult(userExam.getId()); // in case user click submit multiple times
            int score = ueqService.saveUserExamResult(ansQues,userExam.getId());

            int passMark = ((Exam) examBaseDao.findUnique("id", examId, Exam.class)).getPassMark();
            if (score >= passMark) {
                userExam.setStatus(UserExamStatus.PASSED.value);
            } else {
                userExam.setStatus(UserExamStatus.FAILED.value);
                userExam.setFailCount(userExam.getFailCount() == null ? 1 : userExam.getFailCount() + 1);
                examMailService.notifyExamFail(userExam);
            }
            userExam.setEndTime(new Timestamp(endTime));
            userExam.setScore(score);
            userExamDao.update(userExam);
            if (((Exam) examBaseDao.findUnique("id", examId, Exam.class)).getIsMock() == 1) {
                ueqService.clearOldUserExamResult(userExam.getId());
            }
            return userExam;
        }
        return new UserExam();
    }

}
