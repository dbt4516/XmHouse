package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.*;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.*;
import com.cnc.xmhouse.util.FormatConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by hongzhan on 2016/7/26.
 */

@Controller
@RequestMapping("/exam")
public class ExamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);
    @Autowired
    private ExamService examService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SuperAdminService saService;
    @Autowired
    private UserExamQuestionService ueqService;
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<Course> authedCourseId = saService.getCoursesWhichAdminHasRight(getCurrentUserId());
        if (authedCourseId == null)
            return "deny";
        model.addAttribute("count", examService.getExamsCounts(authedCourseId));
        model.addAttribute("course",authedCourseId);
        model.addAttribute("current","exam");
        return "/exam/list";
    }

    /**
     * 考试列表
     */
    @RequestMapping("/{page}/{limit}")
    @ResponseBody
    public List<Exam> getCourses(@PathVariable("page") Integer page,
                                 @PathVariable("limit") Integer limit) {
        List<Course> authedCourseId = saService.getCoursesWhichAdminHasRight(getCurrentUserId());
        List<Exam> exams = examService.list(authedCourseId, page, limit);
        for (Exam exam : exams) {
            exam.setCourseName(courseService.getCourseById(exam.getCourseId()).getName());
            if (exam.getPassRate() == null) { // calculate passrate lazily
                exam.setPassRate(examService.calculatePassRate(exam.getId()));
            }
        }
        return exams;
    }

    /**
     * 修改考试显示页面
     */
    @RequestMapping(value = "/showUpdate/{examId}")
    public String showUpdate(@PathVariable("examId") Long examId, Model model) {
        model.addAttribute("exam", examService.getExam(examId));
        model.addAttribute("isExamed", examService.examHasBennTaken(examId));
        model.addAttribute("current","exam");
        return "exam/update";
    }


    /**
     * 设置考试人员
     */
    @RequestMapping(value = "/setExamUser/{examId}")
    public String setExamUser(@PathVariable("examId") Long examId, Model model) {
        model.addAttribute("exam", examService.getExam(examId));
        List<String> mails = userService.loadAllUserMails();
        model.addAttribute("mails", mails);
        model.addAttribute("current","exam");
        return "exam/setExamUser";
    }

    /**
     * 考卷页面
     */
    @RequestMapping(value = "/paper/{examId}")
    public String paper(@PathVariable("examId") Long examId, Model model) {
        model.addAttribute("exam", examService.getExam(examId));
        model.addAttribute("current","exam");
        return "exam/paper";
    }


    @RequestMapping(value = "ajax/addExam", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> addExam(long startTime, long endTime, String examName, int passMark, int duration, int courseId, int questionCount, Integer is_mock) {
        LOGGER.info("{} 添加考试",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(0, examService.addExam(getCurrentUserId(), startTime, endTime, examName, passMark, duration, courseId, questionCount, is_mock), "");
    }

    @RequestMapping(value = "ajax/getExam", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultInfo<Exam> getExam(long examId) {
        Exam exam = examService.getExam(examId);
        exam.setCourseName(courseService.getCourseById(exam.getCourseId()).getName());
        return new AjaxResultInfo<>(exam, true, "");
    }

    @RequestMapping(value = "ajax/editExam", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> editExam(long examId, long startTime, long endTime, String examName, int passMark, int duration, int questionCount, Integer is_mock) {
        return new AjaxResultInfo<>(0, examService.editExam(examId, startTime, endTime, examName, passMark, duration, questionCount, is_mock), "");
    }

    @RequestMapping(value = "ajax/setExamQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> setExamQuestion(long examId, @RequestParam("questionIds[]") String[] questionIds) {
        return new AjaxResultInfo<>(0, examService.setExamQuestion(examId, FormatConverter.stringArray2LongList(questionIds)), "");
    }


    private int getCurrentUserId() {
        return ((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    @RequestMapping(value = "ajax/getUserExamQuestions", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultInfo<List<UserExamQuestionDetail>> getUserExamQuestions(long userExamId) {
        List<UserExamQuestionDetail> list = examService.getUserExamQuestions(userExamId);
        return new AjaxResultInfo<>(list, list != null, "");
    }
    @RequestMapping(value = "detail/{userExamId}")
    public String getUserExamDetail(@PathVariable("userExamId") long userExamId,Model model) {
        model.addAttribute("userExamId",userExamId);
        model.addAttribute("current","myexam");
        return "/exam/detail";
    }

    @RequestMapping(value = "ajax/reJudge", method = RequestMethod.POST)
    @ResponseBody
    public Map reJudge(long ueqId, int isRight) {
        Map<String, Object> ret = new HashMap<>();
        int score = ueqService.reJudge(ueqId, isRight == 1);
        ret.put("score", score);
        ret.put("success", true);
        return ret;
    }
}
