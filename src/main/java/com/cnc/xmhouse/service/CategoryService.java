package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Category;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.cnc.xmhouse.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by linsy1 on 2016/7/24.
 */
@Service
@Transactional
public class CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private BaseDao<Category> baseDao;

    public static final String UNCATEGORIED ="未分类";

    /**
     * 列表
     * */
    public List<Category> list(){

        return baseDao.find(Category.class);
    }
    /**
     * 列表(包括未分类情况显示)
     * */
    public List<Category> listAll(){

        List<Category> categories = baseDao.find(Category.class);
        Category category = new Category();
        category.setId(0);
        category.setName(CategoryService.UNCATEGORIED);
        categories.add(category);

        return categories;
    }

    /**
     * 列表（排除指定id)
     */
    public List<Category> list(List<Integer> ids){
        if(ids==null || ids.size() == 0)
            return list();
        String id = Joiner.on(" , ").join(ids);
        String hql = "FROM Category WHERE id NOT IN (" + id + ")";
        return baseDao.query(hql);
    }

    /**
     * 列表（分页）
     * */
    public List<Category> list(Integer page,Integer limit){

        String hql = "FROM Category";
        return baseDao.queryWithStartLimit(hql,null,page,limit);
    }
    /**
     * 计数
     * */
    public long getCount(){ return baseDao.queryHqlCount("SELECT COUNT(*) From Category");}

    /**
     * 保存
     * */
    public Category save(Category category){

        if(category == null|| category.getName() == null || ("").equals(category.getName())||sameName(category.getName()))
            return null;
        else
            return baseDao.save(category);
    }

    /**
     * 是否分类有重名
     * */
    public boolean sameName(String name){

        String hql = "SELECT COUNT(*) FROM Category WHERE name=:name";
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        long count = baseDao.queryHqlCountByMap(hql,map);

        return (count>0);
    }

    /**
     * 更新
     * */
    public void update(Category category){

        baseDao.update(category);
    }

    /**
     * 详情
     * */
    public Category detail(Integer categoryId){

        return (Category) baseDao.findUnique("id",categoryId,Category.class);
    }

    /**
     * 删除
     * */
    public void remove(Integer categoryId){

        //先将该分类下的所有课程变为未分类
        String hql = "UPDATE Course SET category_id = 0 WHERE category_id=:category_id";
        Map<String, Object> map = new HashMap<>();
        map.put("category_id",categoryId);
        baseDao.executeUpdateHqlByMap(hql,map);
        //删除该分类
        baseDao.deleteById(categoryId,Category.class);
    }
    /**
     * 通过id查找分类
     * */
    public Category getCategoryById(Integer categoryId){

        return (Category)baseDao.findUnique("id",categoryId,Category.class);
    }

}
