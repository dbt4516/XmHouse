package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by zhuangjy on 2016/7/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Test
    public void getCourseByName() throws Exception {
        courseService.getCourseByName("CDN原理");
    }
    @Test
    public void getCourseById() throws Exception{
        Course course = courseService.getCourseById(1);
        System.out.println(course);
    }
    @Test
    public void list() throws Exception {
        List<Course> courses = courseService.list(2,2);
        if(null != courses){
            for(Course course:courses)
                System.out.println(course.getName());}
    }
    @Test
    public void onlineList() throws Exception {
        List<Course> courses = courseService.onlineList(2,2);
        if(null != courses)
            for(Course course:courses) System.out.println(course.getName());
    }
    @Test
    public void detail() throws Exception {
        System.out.println(courseService.detail(2).getCategoryName());
    }
/*    @Test
    public void remove() throws Exception{
        courseService.remove(1);
    }*/
     @Test
     public void count() throws Exception {
        System.out.println(courseService.getCount());
     }
    @Test
    public void isSame()throws Exception{
        System.out.println(courseService.sameName("1"));
    }
    @Test
    public void getCoursesByCategoryId()throws Exception{
        List<Course> courses = courseService.getCoursesByCategoryId(1);
        if(null != courses){
            for(Course course:courses) System.out.println(course);
        }
    }


}
