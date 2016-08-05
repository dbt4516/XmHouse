package com.cnc.xmhouse.controller;


import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.*;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.*;
import com.cnc.xmhouse.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by linsy1 on 2016/7/24.
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @Autowired
    private SuperAdminService superAdminService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("/list")
    public String list(Model model){
        Long count = courseService.getCount();
        model.addAttribute("count",count);
        List<Category> categories = categoryService.list();//查找目前所有的分类
        model.addAttribute("categories", getRightCategories(categories));
        model.addAttribute("current","course");
        return "/course/list";
    }
    /**
     * 课程列表
     * */
    @RequestMapping("/{page}/{limit}")
    @ResponseBody
    public List<Course> getCourses(@PathVariable("page") Integer page,
                                   @PathVariable("limit") Integer limit) {

        List<Course> courses = courseService.list(page,limit);
        for(Course course:courses)
            courseService.setCategoryToCourse(course);

        return courses;
    }

    @RequestMapping("/onlineList")
    public String onlineList(Model model){
        Long count = courseService.getOnlineCount();
        model.addAttribute("count",count);
        model.addAttribute("current","course");
        return "/course/onlineList";
    }

    /**
     * 上线课程列表
     * */
    @RequestMapping("/online/{page}/{limit}")
    @ResponseBody
    public List<Course> getOnlineCourses(@PathVariable("page") Integer page,@PathVariable("limit") Integer limit) {

        List<Course> courses = courseService.onlineList(page,limit);
        for(Course course:courses)
            courseService.setCategoryToCourse(course);
        return courses;
    }

    /**
     * 课程详情
     * */
    @RequestMapping("/detail/{courseId}")
    public String detail(@PathVariable("courseId") Integer courseId,
                         Model model) {
        Course course = courseService.detail(courseId);
        List<Exam> exams = examService.getExamsByCourseId(courseId);
        List<Resource> resources = resourceService.getResourcesByCourseId(courseId);
        List<Category> categories = categoryService.list();//查找目前所有的分类
        Long count = messageService.getCount(courseId);

        model.addAttribute("course",course);
        model.addAttribute("exams",exams);
        model.addAttribute("resources",resources);
        model.addAttribute("categories", getRightCategories(categories));
        model.addAttribute("count", count);
        model.addAttribute("current","course");

        return "course/detail";
    }

    /**
     * 添加课程
     * */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> save(@RequestParam("resources") MultipartFile[] resources,
                                                     @RequestParam("img") MultipartFile img,
                                                     HttpServletRequest request,
                                                     Course course
                                                     )throws IOException {
        if(!isAdminHasRight(course.getCategoryId()))
            return new AjaxResultInfo<>(0,false,"您没有对这课程的该项权限");
        //上传图片
        String rootPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        String imgPath = rootPath + "img" + "/course/";
        File file = FileUtil.uploadImg(img,imgPath);
        //设置图片路径
        course.setImgSource(file.getName());
        //保存课程
        Course savedCourse = courseService.save(course);

        if (null != savedCourse) {
            LOGGER.info("{} 新增课程",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            return uploadResources(resources, rootPath, savedCourse.getId());
        }else
            return new AjaxResultInfo<>(0,false,"课程名不能有重复！");

    }

    /**
     * 修改课程
     * */
    @RequestMapping(value = "/update")
    @ResponseBody
    public AjaxResultInfo<Integer> update(@RequestParam("resources") MultipartFile[] resources,
                          @RequestParam("img") MultipartFile img,
                          HttpServletRequest request,
                          Course course) throws IOException{

        if(!isAdminHasRight(course.getCategoryId()))
            return new AjaxResultInfo<>(0,false,"您没有对这课程的该项权限");

        String rootPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        File file = FileUtil.uploadImg(img,rootPath + "img" + "/course/");
        //设置图片路径
        course.setImgSource(file.getName());
        courseService.update(course);
        //FIXME 主题图片的查找与删除
        resourceService.removeResourcesByCourseId(course.getId());
        resourceService.removeByCourseId(course.getId(),rootPath+"file" + "/course/");

        LOGGER.info("{} 修改课程",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return uploadResources(resources,rootPath,course.getId());

    }

    /**
    物理删除课程
     */
    @RequestMapping("/remove/{courseId}")
    @ResponseBody
    public AjaxResultInfo<Integer> remove(@PathVariable("courseId") Integer courseId,
                                          HttpServletRequest request) {


        Course course = courseService.getCourseById(courseId);
        if(!isAdminHasRight(course.getCategoryId()))
            return new AjaxResultInfo<>(0,false,"您没有对这课程的该项权限");
        String rootPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        String imgPath = courseService.getCourseById(courseId).getImgSource();
        if(!courseService.remove(courseId,rootPath+"file" + "/course/"))
            return new AjaxResultInfo<>(0,false,"");
        resourceService.removeFile(rootPath+"img" + "/course/"+imgPath);
        LOGGER.info("{} 删除课程",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return new AjaxResultInfo<>(1,true,"");


    }

    /**
     * 课程上线
     * */
    @RequestMapping("/online/{courseId}")
    @ResponseBody
    public AjaxResultInfo<Integer> online(@PathVariable("courseId")
                                 Integer courseId) {

        Course course = courseService.getCourseById(courseId);
        if(!isAdminHasRight(course.getCategoryId()))
            return new AjaxResultInfo<>(0,false,"您没有对这课程的该项权限");

        courseService.online(courseId);

        LOGGER.info("{} 上线课程",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return new AjaxResultInfo<>(1,true,"");

    }

    /**
     * 课程下线
     * */
    @RequestMapping("/downline/{courseId}")
    @ResponseBody
    public AjaxResultInfo<Integer> downline(@PathVariable("courseId")
                                   Integer courseId) {

        Course course = courseService.getCourseById(courseId);
        if(!isAdminHasRight(course.getCategoryId()))
            return new AjaxResultInfo<>(0,false,"您没有对这课程的该项权限");

        courseService.downline(courseId);

        LOGGER.info("{} 下线课程",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return new AjaxResultInfo<>(1,true,"");

    }

    /**
     * 将课程从分类中删除
     * */
    @RequestMapping("/removeCourseFromCategory/{courseId}")
    @ResponseBody
    public AjaxResultInfo<Integer> removeCourseFromCategory(@PathVariable("courseId")
                                                   Integer courseId) {
        Course course = courseService.getCourseById(courseId);
        if(!isAdminHasRight(course.getCategoryId()))
            return new AjaxResultInfo<>(0,false,"您没有对这课程的该项权限");

        courseService.removeCourseFromCategory(courseId);
        LOGGER.info("{} 移除课程",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(1,true,"");
    }
    /**
     * 执行课程增删改操作的用户权限判定
     * @param categoryId
     * */
    public boolean isAdminHasRight(Integer categoryId){

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userInfo.getId());

        if("ROLE_SADMIN".equals(user.getAuth()))
            return true;
        if("ROLE_ADMIN".equals(user.getAuth()) &&
                superAdminService.isAdminHasRightToCourse(categoryId, user.getId()))
            return true;
        else
            return false;
    }

    /**
     * 获得该管理员有权限的分类列表
     * @param categories
     * @return
     */
    public List<Category> getRightCategories(List<Category> categories){

        List<Category> rightCategories = new ArrayList<>();
        for(Category category:categories) {
            if(isAdminHasRight(category.getId()))
                rightCategories.add(category);
        }
        return rightCategories;
    }
    /**
     * 上传多个资源
     * @param resources
     * @param rootPath
     * @param courseId
     * @return
     * */
    public AjaxResultInfo<Integer> uploadResources(MultipartFile[] resources,String rootPath,Integer courseId){

        if(null != resources && resources.length>0){
            String filePath = rootPath + "file" + "/course/";
            StringBuffer fileName = null;
            StringBuffer postfix = null;
            File file = null;
            //遍历文件列表
            for(int i = 0;i<resources.length;i++){
                MultipartFile resource = resources[i];
                postfix = new StringBuffer(resource.getOriginalFilename().substring(resource.getOriginalFilename().indexOf(".")));
                fileName = new StringBuffer(Math.round(Math.random() * 1000000) + "" + new Date().getTime() + postfix.toString());
                try {
                    file = FileUtil.transferToFile(filePath,fileName.toString(),resource);
                    if(null == file)
                        return new AjaxResultInfo<>(0,false,"资源上传出错，请联系管理员！");
                    else
                        resourceService.save(new Resource(courseId,String.valueOf(fileName)));
                } catch (IOException ex) {
                    LOGGER.error("{} 资源上传错误信息： {}", ((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),ex);
                    return new AjaxResultInfo<>(0,false,"未知错误，请联系管理员！");
                }
            }
        }
        return new AjaxResultInfo<>(1,true,"");
    }

}
