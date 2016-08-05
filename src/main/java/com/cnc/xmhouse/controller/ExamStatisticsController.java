package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.lagacy_entity.ExamStatistices;
import com.cnc.xmhouse.lagacy_entity.UserExamDetail;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.CourseService;
import com.cnc.xmhouse.service.ExamStatisService;
import com.cnc.xmhouse.util.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongzhan on 2016/7/29.
 */

@Controller
@RequestMapping("/examStatistics")
public class ExamStatisticsController {

    @Autowired
    private ExamStatisService esService;
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/manage")
    public String manage(Model model) {
        model.addAttribute("current", "examManage");
        return "manage";
    }

    @RequestMapping(value = "/result/list")
    public String resultList(Model model) {
        List<Course> courses = courseService.list();
        model.addAttribute("courses", courses);
        model.addAttribute("current", "exam");
        model.addAttribute("current", "examManage");
        return "/examStatistics/result_list";
    }

    @RequestMapping(value = "/exam/list")
    public String examList(Model model) {
        List<Course> courses = courseService.list();
        model.addAttribute("courses", courses);
        model.addAttribute("current", "exam");
        model.addAttribute("current", "examManage");
        return "/examStatistics/exam_list";
    }

    @RequestMapping(value = "/dept/list")
    public String deptList(Model model) {
        List<Course> courses = courseService.list();
        model.addAttribute("courses", courses);
        model.addAttribute("current", "exam");
        model.addAttribute("current", "examManage");
        return "/examStatistics/dept_list";
    }

    @RequestMapping(value = "/leader/list")
    public String leaderList(Model model) {
        List<Course> courses = courseService.list();
        model.addAttribute("courses", courses);
        model.addAttribute("current", "exam");
        model.addAttribute("current", "examManage");
        return "/examStatistics/leader";
    }

    @RequestMapping(value = "/exam/person/{examId}")
    public String listPersonExam(@PathVariable("examId") Long id, Model model) {
        List<UserExamDetail> exam = esService.queryByExamId(id);
        model.addAttribute("exams", exam);
        model.addAttribute("current", "exam");
        model.addAttribute("current", "examManage");
        return "/examStatistics/exam_person";
    }

    @RequestMapping(value = "ajax/baseQuery", method = RequestMethod.POST)
    @ResponseBody
    public Map query(Long st, Long et, String name, Integer course, Integer courseType, Integer status, Integer page, Integer perpage) {
        List<UserExamDetail> query = esService.query(st, et, name, course, courseType, status);
        Map<String, Object> ret = new HashMap<>();
        operateReturn(query, ret, page, perpage);
        return ret;
    }

    @RequestMapping(value = "ajax/baseQueryReport", method = RequestMethod.POST)
    public void report(Long st, Long et, String name, Integer course, Integer courseType, Integer status, HttpServletResponse response) throws IOException {
        response.setContentType("application/csv; charset=gbk");
        response.addHeader("content-disposition", "inline; filename=report.csv");
        List<UserExamDetail> query = esService.query(st, et, name, course, courseType, status);
        String report = ReportGenerator.gen(query);
        OutputStream os = response.getOutputStream();
        os.write(report.getBytes());
        os.flush();
    }

    @RequestMapping(value = "ajax/leaderQuery", method = RequestMethod.GET)
    @ResponseBody
    public Map leaderQuery(Long st, Long et, Integer course, Integer page, Integer perpage) {// st and et here is for exam create time
        List<UserExamDetail> query = esService.leaderQuery(st, et, course, getCurrentUserId());
        Map<String, Object> ret = new HashMap<>();
        operateReturn(query, ret, page, perpage);
        return ret;
    }

    @RequestMapping(value = "ajax/examQuery", method = RequestMethod.GET)
    @ResponseBody
    public Map examQuery(Long st, Long et, Integer course, Integer courseType, Integer page, Integer perpage) {
        List<ExamStatistices> query = esService.getExamStatistices(st, et, course, courseType);
        Map<String, Object> ret = new HashMap<>();
        operateReturn(query, ret, page, perpage);
        return ret;
    }

    @RequestMapping(value = "ajax/deptQuery", method = RequestMethod.GET)
    @ResponseBody
    public Map deptQuery(Long st, Long et, Integer course, Integer courseType, Integer page, Integer perpage) {
        List<ExamStatistices> query = esService.getDeptStatistices(st, et, course);
        Map<String, Object> ret = new HashMap<>();
        operateReturn(query, ret, page, perpage);
        return ret;
    }

    private int getCurrentUserId() {
        return ((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public void operateReturn(List query, Map<String, Object> ret, Integer page, Integer perpage) {
        if (query != null) {
            ret.put("count", query.size());
            ret.put("data", query.subList(page * perpage, (page + 1) * perpage > query.size() ? query.size() : (page + 1) * perpage));
        } else {
            ret.put("count", 0);
            ret.put("data", new ArrayList<>());
        }
    }
}
