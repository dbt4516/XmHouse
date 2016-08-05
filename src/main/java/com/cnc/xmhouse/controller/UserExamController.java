package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.UserExam;
import com.cnc.xmhouse.lagacy_entity.UserExamDetail;
import com.cnc.xmhouse.enums.UserExamStatus;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.ExamService;
import com.cnc.xmhouse.service.UserExamService;
import com.cnc.xmhouse.util.FormatConverter;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongzhan on 2016/7/28.
 */


@Controller
@RequestMapping("/userexam")
public class UserExamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserExamController.class);
    @Autowired
    private ExamService examService;
    @Autowired
    private UserExamService ueService;


    @RequestMapping(value = "ajax/setExamUsers", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> setExamUsers(long examId, @RequestParam("userIds[]") String[] userIds, HttpServletRequest request){
        LOGGER.info("{} 设置考试人员",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(0,ueService.setExamUsers(examId, FormatConverter.stringArray2IntList(userIds)),"");
    }

    @RequestMapping(value = "ajax/setExamUsersByEmails", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> setExamUsersByEmails(long examId, @RequestParam("emails[]") String[] emails, HttpServletRequest request){
        LOGGER.info("{} 设置考试人员",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(0,ueService.setExamUsers(examId, ueService.emails2UserIds(FormatConverter.stringArray2StringList(emails))),"");
    }

    private int getCurrentUserId() {
        return ((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    // un-documented
    @RequestMapping(value = "ajax/getSubordinatesExam", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultInfo<List<UserExamDetail>> getSubordinatesExam(int page, int perpage, Integer status, HttpServletRequest request) {
        return new AjaxResultInfo<>(ueService.getExamByLeaderId(getCurrentUserId(), status, page, perpage), true, "");
    }

    @RequestMapping(value = "ajax/userStartExam", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> userStartExam(long examId) {
        int userId = getCurrentUserId();
        UserExamStatus userExamStatus = ueService.userStartExam(examId, userId);
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", userExamStatus.value);
        if (userExamStatus == UserExamStatus.NOT_YET_TAKEN) {
            ret.put("questions", examService.getExamQuestions(examId));
        }
        LOGGER.info("{} 考试开始",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return ret;
    }

    @RequestMapping(value = "ajax/userFinishExam", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<UserExam>userFinishExam(long examId, @RequestParam("list[]") String[] list){
        int userId=getCurrentUserId();
        LOGGER.info("{} 考试结束",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(ueService.userFinishExam(examId,userId,FormatConverter.stringArray2UEQList(list)),true,"");
    }

    @RequestMapping(value = "ajax/setExamExemptUsers", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer>setExamExemptUsers(long examId, @RequestParam("userIds[]") String[] userIds,HttpServletRequest request){
        LOGGER.info("{} 设置免考人员",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

                return new AjaxResultInfo<>(0,ueService.exempt(examId,FormatConverter.stringArray2IntList(userIds)),"");
    }

    @RequestMapping(value = "ajax/setExamLeaveUsers", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer>setExamLeaveUsers(long examId, @RequestParam("userIds[]") String[] userIds,HttpServletRequest request){
        LOGGER.info("{} 设置请假人员",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(0,ueService.leave(examId,FormatConverter.stringArray2IntList(userIds)),"");
    }


    @RequestMapping(value = "ajax/setExamExemptUsersByEmails", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer>setExamExemptUsersByEmails(long examId, @RequestParam("emails[]") String[] emails,HttpServletRequest request){
        LOGGER.info("{} 设置免考人员",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());


        return new AjaxResultInfo<>(0,ueService.exempt(examId,ueService.emails2UserIds(FormatConverter.stringArray2StringList(emails))),"");
    }

    @RequestMapping(value = "ajax/setExamLeaveUsersByEmails", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer>setExamLeaveUsersByEmails(long examId, @RequestParam("emails[]") String[] emails,HttpServletRequest request){
        LOGGER.info("{} 设置请假人员",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(0,ueService.leave(examId,ueService.emails2UserIds(FormatConverter.stringArray2StringList(emails))),"");
    }


    @RequestMapping(value = "ajax/getUserByExam", method = RequestMethod.GET)
    @ResponseBody
    public Map getUserByExam(long examId, int page, int perpage, int status, HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();
        Pair<Long, List<UserExam>> p = ueService.getUserExamsByExam(examId, status, page, perpage);
        ret.put("count", p.getKey());
        ret.put("data", p.getValue());
        ret.put("success", true);
        return ret;
    }


    /**
     * 查看当前登录用户的考试情况的跳转
     */
    @RequestMapping(value = "/detail")
    public String detail(Model model) {
        model.addAttribute("current", "myexam");
        return "userexam/detail";
    }

    @RequestMapping(value = "ajax/getUserExam", method = RequestMethod.GET)
    @ResponseBody
    public Map getUserExam(int page, int perpage, Integer status, HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();
        Pair<Integer, List<UserExamDetail>> p = ueService.getExamByUserId(getCurrentUserId(), status, page, perpage);
        ret.put("count", p.getKey());
        ret.put("data", p.getValue());
        ret.put("success", true);
        return ret;
    }
}
