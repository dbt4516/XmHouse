package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.Resource;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linsy1 on 2016/7/25.
 */
@Service
@Transactional
public class ResourceService {
    @Autowired
    private BaseDao<Resource> baseDao;

    /*保存*/
    public boolean save(Resource resource){

        return (baseDao.save(resource)!=null);

    }
    /*修改*/
    public void update(Resource resource){

        baseDao.update(resource);
    }
    /*通过id查找资源*/
    public Resource getResourceById(long resourceId){

        return (Resource)baseDao.findUnique("id",resourceId,Resource.class);
    }
    /*通过课程id查找资源列表*/
    public List<Resource> getResourcesByCourseId(Integer courseId){

        String hql = "FROM Resource WHERE course_id =:course_id";
        Map<String, Object> map = new HashMap<>();
        map.put("course_id",courseId);
        List<Resource> list = baseDao.query(hql,map);
        return list;
    }
    /*通过id删除数据库资源数据*/
    /*public void removeResource(long resourceId){
        baseDao.deleteById(resourceId,Resource.class);
    }*/
    /*通过id删除文件夹内文件*/
    public AjaxResultInfo<Integer> remove(long resourceId,String filePath) {
        Resource resource = getResourceById(resourceId);
        if(null == resource)
            return new AjaxResultInfo(0,false,"该资源不存在!");
        removeFile(filePath+resource.getSource());
        baseDao.deleteById(resourceId,Resource.class);//删除数据库资源数据
        return new AjaxResultInfo<>(1,true,"");
    }

    /*通过课程id删除数据库资源列表数据*/
    public int removeResourcesByCourseId(Integer courseId){

        String hql = "DELETE FROM Resource WHERE course_id=:course_id";
        Map<String, Object> map = new HashMap<>();
        map.put("course_id",courseId);
        return baseDao.executeUpdateHqlByMap(hql,map);

    }
    /*通过课程id删除该课程文件夹内文件列表*/
    public AjaxResultInfo<Integer> removeByCourseId(Integer courseId,String filePath) {

        List<Resource> resources = getResourcesByCourseId(courseId);
        //如果存在资源列表
        if(resources != null){
            for (Resource resource : resources) {
                System.out.println(filePath + resource.getSource());removeFile(filePath + resource.getSource());}
        }
        return new AjaxResultInfo(1,true,"");
    }

    /*删除文件夹内文件*/
    public AjaxResultInfo<Integer> removeFile(String source){

       File file = new File(source);
        try {
            FileUtils.forceDelete(file);
        }catch(IOException ex){
            return new AjaxResultInfo<>(0,false,"资源删除失败");
        }
        return new AjaxResultInfo<>(1,true,"");
    }

}
