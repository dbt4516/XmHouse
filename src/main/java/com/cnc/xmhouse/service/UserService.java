package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.Dept;
import com.cnc.xmhouse.lagacy_entity.User;
import com.google.common.base.Joiner;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuangjy on 2016/7/26.
 */
@Service
public class UserService {
    @Autowired
    private BaseDao<User> baseDao;
    @Autowired
    private BaseDao<Dept> deptDao;

    public List<String> loadAllUserMails() {
        String hql = "SELECT mail FROM User";
        return baseDao.queryNoneType(hql);
    }

    //增加过滤部分用户
    public List<String> loadAllUserMails(List<Integer> ids){
        if(ids == null || ids.size() == 0)
            return loadAllUserMails();
        String id = Joiner.on(" , ").join(ids);
        String hql = "SELECT mail FROM User WHERE id NOT  IN (" + id + ")";
        return baseDao.queryNoneType(hql);
    }

    public List<Integer> loadAllUserIds() {
        String hql = "SELECT id FROM User";
        return baseDao.queryNoneType(hql);
    }

    /**
     * 列表
     */
    public List<User> getUsers() {
        return baseDao.find(User.class);
    }

    /**
     * 列表 （根据多个id）
     */
    public List<User> getUsers(List<Integer> ids) {
        if (ids == null || ids.size() == 0)
            return new ArrayList<>();
        else {
            String id = Joiner.on(" , ").join(ids);
            String hql = "FROM User WHERE id in (" + id + ")";
            return baseDao.query(hql);
        }
    }

    /**
     * 列表（分页）
     */
    public List<User> getUsers(Integer page, Integer limit) {
        String hql = "FROM User";
        return baseDao.queryWithStartLimit(hql, null, page, limit);
    }

    /**
     * 保存
     */
    public User save(User user) {
        if (!isSameMail(user.getMail()))
            return (User) baseDao.save(user);
        else
            return null;
    }

    /**
     * 修改
     */
    public void updateUser(User user) {
        baseDao.update(user);
    }

    public void updateUserAuth(Integer userId,String auth){
        String hql = "UPDATE User SET auth=:auth WHERE id=:id";
        Map<String,Object> hs = new HashedMap();
        hs.put("auth",auth);
        hs.put("id",userId);
        baseDao.executeUpdateHqlByMap(hql,hs);
    }

    //批量修改
    public void updateUserAuth(List<Integer> userIds,String auth){
        if(userIds == null || userIds.size() == 0)
            return;
        String hql = "UPDATE User SET auth=:auth WHERE id=:id";
        Map<String,Object> hs = new HashedMap();
        hs.put("auth",auth);
        for(Integer id:userIds){
            hs.put("id",id);
            baseDao.executeUpdateHqlByMap(hql,hs);
        }
    }

    /**
     * 判断邮箱是否重复
     */
    public boolean isSameMail(String mail) {
        String hql = "SELECT COUNT(*) FROM User WHERE mail=:mail";
        Map<String, Object> map = new HashMap<>();
        map.put("mail", mail);
        long count = baseDao.queryHqlCountByMap(hql, map);
        return (count > 0);
    }

    /**
     * 删除
     */
    public void removeUser(Integer userId) {
        baseDao.deleteById(userId, User.class);
    }

    /**
     * 计数
     */
    public long getUsersCount() {
        return baseDao.queryHqlCount("SELECT COUNT(*) From User");
    }


    /**
     * 通过id查找用户
     */
    public User getUserById(Integer userId) {
        return (User) baseDao.findUnique("id", userId, User.class);
    }

    public User getUserByMail(String mail) {
        return (User) baseDao.findUnique("mail", mail, User.class);
    }

    public List<User> getUsersByDept(int deptId) {
        return baseDao.query(" from User where deptId=?", deptId);
    }


    public List<User> getsUserByName(String name) {
        return baseDao.query(" from User where userName like '%" + name + "%'");
    }

    public List<Dept> getDepts() {
        return deptDao.query("from Dept");
    }

    public User getUserByNumber(String num) {
        return (User) baseDao.findUnique("number", num, User.class);
    }


}
