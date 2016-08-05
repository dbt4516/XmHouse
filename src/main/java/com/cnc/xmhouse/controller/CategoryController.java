package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.Category;
import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.CourseService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cnc.xmhouse.service.CategoryService;


import java.util.*;

/**
 * Created by linsy1 on 2016/7/24.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseService courseService;

    @RequestMapping("/list")
    public String list(Model model){
        Long count = categoryService.getCount();
        model.addAttribute("count",count);
        model.addAttribute("current","course");
        return "/course/list";
    }
    /**
     * 列表
     * */
    @RequestMapping("/{page}/{limit}")
    @ResponseBody
    public List<Category> list(@PathVariable("page") Integer page,@PathVariable("limit") Integer limit) {

        return categoryService.list(page,limit);

    }

    /**
     * 添加分类
     * */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> save(String name){

        Category category = new Category(name);
        Category savedCategory = categoryService.save(category);
        if(null != savedCategory) {
            LOGGER.info("{}  新增分类",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            return new AjaxResultInfo<>(1, true, "");
        }
        else
            return new AjaxResultInfo<>(0,false,"分类名不可重复或为空！");

    }
    /**
     * 修改分类显示页面
     * */
    @RequestMapping(value = "/showUpdate/{categoryId}")
    public String showUpdate(@PathVariable("categoryId")
                                         Integer categoryId,Model model) {

        Category category = categoryService.detail(categoryId);
        model.addAttribute("category",category);
        model.addAttribute("current","course");
        return "category/update";
    }
    /**
     * 修改分类
     * */
    @RequestMapping(value = "/update")
    @ResponseBody
    public AjaxResultInfo<Integer> update(Category category){

        categoryService.update(category);
        LOGGER.info("{}  修改分类",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(1,true,"");
    }
    /**
     * 删除分类
     * */
    @RequestMapping("/remove/{categoryId}")
    @ResponseBody
    public AjaxResultInfo<Integer> remove(@PathVariable("categoryId")
                                 Integer categoryId) {

        categoryService.remove(categoryId);
        LOGGER.info("{}  删除分类",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return new AjaxResultInfo<>(1,true,"");
    }

    /**
     * 分类下的课程列表
     * */
    @RequestMapping("/detail/{categoryId}")
    @ResponseBody
    public AjaxResultInfo<List<Course>> getCoursesByCategoryId(@PathVariable("categoryId")
                                                                 Integer categoryId){
        //查询该分类下的所有课程
        List<Course> courses = courseService.getCoursesByCategoryId(categoryId);

        return new AjaxResultInfo<>(courses,true,"");
    }

}
