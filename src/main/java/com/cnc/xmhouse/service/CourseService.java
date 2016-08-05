package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.Category;
import com.cnc.xmhouse.lagacy_entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linsy1 on 2016/7/24.
 */
@Service
@Transactional
public class CourseService {
    private Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private BaseDao<Course> baseDao;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     * */
    public List<Course> list(){
        return baseDao.find(Course.class);
    }

    /**
     * 列表（分页）
     * */
    public List<Course> list(Integer page,Integer limit){
        String hql = "FROM Course";
        return baseDao.queryWithStartLimit(hql,null,page,limit);
    }
    /**
     * 计数
     * */
    public long getCount(){ return baseDao.queryHqlCount("SELECT COUNT(*) From Course");}

    /**
     * 上线列表
     * */
    public List<Course> onlineList(){

        String hql = "FROM Course WHERE is_online=1";
        List<Course> list = baseDao.query(hql);
        return list;

    }
    /**
     * 上线列表（分页）
     * */
    public List<Course> onlineList(Integer page,Integer limit){

        String hql = "FROM Course WHERE is_online=1";
        List<Course> list = baseDao.queryWithStartLimit(hql,null,page,limit);
        return list;

    }
    /**
     * 上线列表计数
     * */
    public long getOnlineCount(){
        long count = baseDao.queryHqlCount("SELECT COUNT(*) From Course Where is_online=1");
        return count;
    }
    /**
     * 详情
     * */
    public Course detail(Integer courseId){

        Course course =  (Course)baseDao.findUnique("id",courseId,Course.class);
        if(0 == course.getCategoryId())
            course.setCategoryName("未分类");
        else {
            Category category = categoryService.getCategoryById(course.getCategoryId());
            course.setCategoryName(null != category.getName()?category.getName():"");
        }
        return setCategoryToCourse(course);
    }


    /**
     * 保存
     * */
    public Course save(Course course){

        if(!sameName(course.getName()))
            return (Course)baseDao.save(course);
        else
            return null;
    }
    /**
     * 是否课程有重名
     * */
    public boolean sameName(String name){

        String hql = "SELECT COUNT(*) FROM Course WHERE name=:name";
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        long count = baseDao.queryHqlCountByMap(hql,map);

        return (count>0);
    }
    /**
     * 修改
     * */
    public void update(Course course){

        baseDao.update(course);
    }
    /**
     * 物理删除课程
     * */
    public boolean remove(Integer courseId,String filePath){

        Map<String, Object> map = new HashMap<>();
        map.put("course_id",courseId);
        if(baseDao.queryHqlCountByMap(getHqlFromTableName("Message"),map)>0
                ||baseDao.queryHqlCountByMap(getHqlFromTableName("Exam"),map)>0
                ||baseDao.queryHqlCountByMap(getHqlFromTableName("Question"),map)>0)
            return false;
        resourceService.removeByCourseId(courseId,filePath);//删除资源
        baseDao.deleteById(courseId,Course.class);
        return true;
    }

    /**
     * 上线
     * */
    public void online(Integer courseId){

        String hql = "UPDATE Course SET is_online = 1 WHERE id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id",courseId);
        baseDao.executeUpdateHqlByMap(hql,map);
    }
    /**
     * 下线
     * */
    public void downline(Integer courseId){

        String hql = "UPDATE Course SET is_online = 0 WHERE id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id",courseId);
        baseDao.executeUpdateHqlByMap(hql,map);
    }

    /**
     * 通过分类id查找课程
     * */
    public List<Course> getCoursesByCategoryId(Integer categoryId){

        String hql = "FROM Course WHERE category_id=:category_id";
        Map<String, Object> map = new HashMap<>();
        map.put("category_id",categoryId);
        List<Course> list = baseDao.query(hql,map);
        return list;
    }

    /**
     * 通过id查找课程
     * */
    public Course getCourseById(Integer courseId){
        return (Course)baseDao.findUnique("id",courseId,Course.class);
    }

    /**
     * 通过name查找课程
     * */
    public Course getCourseByName(String name){

        String hql = "FROM Course WHERE name=:name";
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);

        return (Course) baseDao.uniqueResult(hql,map);
    }
    /**
     * 设置课程的分类名
     * @param course
     * */
    public Course setCategoryToCourse(Course course){

        Category category;
        if(0 == course.getCategoryId())
            category = new Category(CategoryService.UNCATEGORIED);
        else
            category = categoryService.getCategoryById(course.getCategoryId());
        if(null == category)
            course.setCategoryName(CategoryService.UNCATEGORIED);
        else
            course.setCategoryName(category.getName());
        return course;
    }
    /**
     *将该课程从分类中删除
     * */
    public void removeCourseFromCategory(Integer courseId){

        String hql = "UPDATE Course SET category_id = 0 where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id",courseId);

        baseDao.executeUpdateHqlByMap(hql,map);
    }
    /**
     * 判断课程下是否有留言/考试/题目的Hsql语句获取
     * @param tableName
     * @return
     */
    public String getHqlFromTableName(String tableName){
        return "SELECT COUNT(*) FROM "+tableName+" WHERE course_id=:course_id";
    }

}
