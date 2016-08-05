package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.lagacy_entity.Question;
import com.cnc.xmhouse.enums.QuestionType;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.CourseService;
import com.cnc.xmhouse.service.ExamService;
import com.cnc.xmhouse.service.QuestionService;
import com.cnc.xmhouse.util.FileUtil;
import com.cnc.xmhouse.util.ValidUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * Created by zhuangjy on 2016/7/23.
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private CourseService courseService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExamService examService;

    @RequestMapping("/add")
    public String gotoAddQuestion(Model model) {
        List<Course> courses = courseService.list();
        model.addAttribute("questionType", QuestionType.values());
        model.addAttribute("courses", courses);
        model.addAttribute("current","exam");
        return "/question/add";
    }


    @RequestMapping(value = "/editor/{question}", method = RequestMethod.GET)
    public String gotoUpdateQuestion(@PathVariable("question") Long questionId, Model model) {
        Question question = questionService.getQuestion(questionId);
        boolean isExamed = examService.questionIsInUse(questionId);
        List<Course> courses = courseService.list();
        model.addAttribute("isExamed", isExamed);
        model.addAttribute("question", question);
        model.addAttribute("questionType", QuestionType.values());
        model.addAttribute("courses", courses);
        model.addAttribute("current","exam");
        return "/question/editor";
    }

    @RequestMapping("/list")
    public String list(Integer courseId,Long examId, Model model) {
        Long count = questionService.getQuestionsCount(courseId);
        model.addAttribute("count", count);
        if(courseId != null) {
            Course course = courseService.getCourseById(courseId);
            model.addAttribute("course",course);
        }
        if(examId!=null)
            model.addAttribute("examId",examId);
        model.addAttribute("current","exam");
        return "/question/list";
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public boolean addQuestion(Question question) {
        if (!ValidUtil.validEmpty(question.getAns(), question.getTitle()))
            return false;
        //如果是修改判断是否题目被用过了
        if (question.getId() != 0l) {
            if (examService.questionIsInUse(question.getId()))
                return false;
        }
        questionService.save(question);
        LOGGER.info("{} 添加题目",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return true;
    }


    @RequestMapping(value = "/automatic", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> addQuestionAuto(@RequestParam(value = "file") MultipartFile origin, HttpServletRequest request) throws IOException {
        String rootPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        String filePath = rootPath + "file" + "/question/";
        File file = FileUtil.transferToFile(filePath, new Date().getTime() + ".txt", origin);
        int num = 0;
        try (
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read)) {
            String line, title, option = null, ans, courseName;
            while ((line = bufferedReader.readLine()) != null) {
                //TODO 判断错误情况
                courseName = line;
                Course course = courseService.getCourseByName(courseName);
                line = bufferedReader.readLine();
                QuestionType type = QuestionType.getQuestionTypeByName(line);
                title = "<p>" + bufferedReader.readLine() + "</p>";
                if (type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE)
                    option = bufferedReader.readLine();
                ans = bufferedReader.readLine();
                bufferedReader.readLine();
                questionService.saveAuto(course.getId(), type, title, option, ans);
                num++;
            }
        } catch (IOException ex) {

            LOGGER.error("{} 文件导入题目错误",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

            return new AjaxResultInfo<>(null, false, "未知错误请联系管理员!");
        }
        LOGGER.info("{} 文件导入题目成功",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(num, true, "OK");
    }

    @RequestMapping(value = "/{page}/{limit}", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getQuestions(Integer courseId, @PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        return questionService.getQuestions(courseId, page, limit);
    }

    @RequestMapping(value = "/{question}", method = RequestMethod.GET)
    @ResponseBody
    public Question getQuestion(@PathVariable("question") Long question) {
        return questionService.getQuestion(question);
    }

    @RequestMapping(value = "/dismissal", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteQuestion(Long questionId) {
        if (!ValidUtil.validNull(questionId) || examService.questionIsInUse(questionId))
            return false;
        LOGGER.info("{} 删除题目",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return questionService.deleteQuestion(questionId);
    }

    @RequestMapping(value = "/output", method = RequestMethod.GET)
    public ResponseEntity<byte[]> output(HttpServletRequest request) throws IOException {
        String savePath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/") + "/file/output/";
        File file = FileUtil.createFile(savePath, new Date().getTime() + ".txt");
        HttpHeaders headers = FileUtil.downloadHeader(file);
        questionService.outPutQuestions(file);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
