package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.lagacy_entity.Dept;
import com.cnc.xmhouse.lagacy_entity.Exam;
import com.cnc.xmhouse.lagacy_entity.UserExam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hongzhan on 2016/8/1.
 */


@Service
@EnableScheduling
public class ExamMailService {

    @Autowired
    private BaseDao<Exam>examBaseDao;
    @Autowired
    private BaseDao<UserExam>userExamDao;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private BaseDao<Dept>deptDao;
    @Autowired
    private ExamService examService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SuperAdminService superAdminService;
    @Autowired
    private BaseDao<Course>courseDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamMailService.class);

  /*  @Scheduled(cron = "0 0 0 * * ?")
    public void notifyExamWithEmailWhentime() {
      *//*  String title="考试通知";
        String contentFomat="课程 %s 的考试将在 %s 开始，请知悉。";
        long today = Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
        long tomorrow = today + 24 * 60 * 60;
        long dayAfterTomorrow=tomorrow+ 24 * 60 * 60;
        List<Exam> exams = examBaseDao.query("from Exam where startTime>=FROM_UNIXTIME(?) and startTime<FROM_UNIXTIME(?)", tomorrow, dayAfterTomorrow);
        for(Exam exam:exams){
            Course course= (Course) courseDao.findUnique("id",exam.getCourseId(),Course.class);
            String content=String.format(contentFomat,course.getName(),exam.getStartTime().toLocalDateTime().format(DateTimeFormatter.ISO_DATE));
            List<UserExam> ues = userExamDao.query("from UserExam where examId=? and status=0", exam.getId());
            List<String> emails = ues.stream().map(ue -> userService.getUserById(ue.getUserId())).map(u -> u.getMail()).collect(Collectors.toList());
            mailService.sendEmails(title,content,emails,null);
        }*//*
    }*/

    public void notifyExamFail(UserExam ue){
        String userName=userService.getUserById(ue.getUserId()).getUserName();
        String examName=examService.getExam(ue.getExamId()).getName();
        String examCreator=userService.getUserById(examService.getExam(ue.getExamId()).getCreator()).getMail();
        List<String>targets=new LinkedList<>();
        if(ue.getFailCount()>=1){
            targets.add(userService.getUserById(ue.getUserId()).getMail());
        }
        try {
            if (ue.getFailCount() >= 2) {
                String email = userService.getUserById(((Dept) deptDao.findUnique("id", userService.getUserById(ue.getUserId()).getDeptId(), Dept.class)).getLeaderId()).getMail();
                targets.add(email);
            }
            if (ue.getFailCount() >= 3) {
                Integer categoryID = courseService.getCourseById(examService.getExam(ue.getExamId()).getCourseId()).getCategoryId();
                List<String> adminEmails = superAdminService.getAdminByCategory(categoryID).stream().map(u -> u.getMail()).collect(Collectors.toList());
                targets.addAll(adminEmails);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
        String title="考试不合格通知";
        String content=String.format("%s 在考试 %s 中不合格 %d 次，请知悉。",userName,examName,ue.getFailCount());
        mailService.sendEmails(title,content,targets,examCreator);
    }

    public void notifyExamWithEmailInCreate(long examId,List<Integer>targerts) {
        String title="考试通知";
        String contentFomat="课程 %s 的考试将在 %s 开始，请知悉。";
        Exam exam= (Exam) examBaseDao.findUnique("id",examId,Exam.class);
        Course course= (Course) courseDao.findUnique("id",exam.getCourseId(),Course.class);
        String content=String.format(contentFomat,course.getName(),exam.getStartTime().toLocalDateTime().format(DateTimeFormatter.ISO_DATE));
        List<String> emails = targerts.stream().map(ue -> userService.getUserById(ue)).map(u -> u.getMail()).collect(Collectors.toList());
        mailService.sendEmails(title,content,emails,null);
    }
}
