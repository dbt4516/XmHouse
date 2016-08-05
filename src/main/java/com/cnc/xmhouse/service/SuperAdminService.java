package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhuangjy on 2016/7/27.
 */
@Service
public class SuperAdminService {
    @Autowired
    BaseDao<Admin> baseDao;
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    BaseDao<AdminGroupCategory>adminGroupCategoryDao;
    @Autowired
    BaseDao<AdminGroupUser>adminGroupUserDao;

    public void createAdminGroup(AdminGroup adminGroup) {
        baseDao.save(adminGroup);
    }

    public void deleteAdminGroup(Integer adminGroupId) {
        baseDao.deleteById(adminGroupId, AdminGroup.class);
    }

    /**
     * 通过小组id来获取所有组员id
     *
     * @param group
     * @return
     */
    public List<Integer> getUsersIdsInGroup(Integer group) {
        String hql = "SELECT userId FROM AdminGroupUser WHERE adminGroupId=:group";
        Map<String, Object> hs = new HashedMap();
        hs.put("group", group);
        return baseDao.queryNoneType(hql, hs);
    }

    /**
     * 配合getUsersIdsInGroup()方法使用。获取该小组的所有成员后，判断这些用户id是否存在于表中。这个时候该表该小组字段应该已经删除。
     * 如果id仍然存在于表中说明该user也是其他组的管理员无需修改，如果不存在，那么就应该修改权限
     *
     * @param ids
     * @return 应该修改权限为ROLE_USER的用户id
     */
    public List<Integer> getUnExistUsers(List<Integer> ids) {
        List<Integer> unExistIds = new ArrayList<>();
        String hql = "SELECT COUNT(*) FROM AdminGroupUser WHERE userId=:id";
        for (Integer id : ids) {
            Map<String, Object> hs = new HashedMap();
            hs.put("id", id);
            if (baseDao.queryHqlCount(hql, hs) == 0)
                unExistIds.add(id);
        }
        return unExistIds;
    }

    //判断该用户是否在其他组中担任管理员: true是，false否
    public boolean isUserInOtherGroup(Integer userId, Integer groupId) {
        String hql = "SELECT COUNT(*) FROM AdminGroupUser WHERE userId=:userId AND adminGroupId !=:groupId";
        Map<String, Object> hs = new HashedMap();
        hs.put("userId", userId);
        hs.put("groupId", groupId);
        return baseDao.queryHqlCount(hql, hs) > 0 ? true : false;
    }

    public void deleteAdminUser(AdminGroupUser adminGroupUser) {
        String hql = "DELETE FROM AdminGroupUser WHERE adminGroupId=:adminGroupId AND userId=:userId";
        Map<String, Object> hs = new HashMap<>();
        hs.put("adminGroupId", adminGroupUser.getAdminGroupId());
        hs.put("userId", adminGroupUser.getUserId());
        baseDao.executeUpdateHqlByMap(hql, hs);
    }

    public void deleteAuth(AdminGroupCategory adminGroupCategory) {
        String hql = "DELETE FROM AdminGroupCategory WHERE adminGroupId=:adminGroupId AND categoryId=:categoryId";
        Map<String, Object> hs = new HashMap<>();
        hs.put("adminGroupId", adminGroupCategory.getAdminGroupId());
        hs.put("categoryId", adminGroupCategory.getCategoryId());
        baseDao.executeUpdateHqlByMap(hql, hs);
    }


    public void addAdminGroupMember(AdminGroupUser adminGroupUser) {
        baseDao.save(adminGroupUser);
    }

    public void addAuth(AdminGroupCategory adminGroupCategory) {
        baseDao.save(adminGroupCategory);
    }

    public List<AdminGroup> listAdminGroup() {
        return baseDao.queryNoneType("FROM AdminGroup");
    }

    public AdminGroup getAdminGroupById(Integer id) {
        return (AdminGroup) baseDao.loadById(id, AdminGroup.class);
    }

    public List<AdminGroupUser> getAdminGroupUsersByGroupId(Integer id) {
        String hql = "From AdminGroupUser WHERE adminGroupId=:adminGroupId";
        Map<String, Object> hs = new HashedMap();
        hs.put("adminGroupId", id);
        return baseDao.queryNoneType(hql, hs);
    }

    public List<AdminGroupCategory> getAdminGroupCategorysByGroupId(Integer id) {
        String hql = "From AdminGroupCategory WHERE adminGroupId=:adminGroupId";
        Map<String, Object> hs = new HashedMap();
        hs.put("adminGroupId", id);
        return baseDao.queryNoneType(hql, hs);
    }

    public boolean isAdminHasRightToCourse(Integer categoryId, Integer userId) {
        String hql = "SELECT COUNT(*) FROM admin_group_category x,admin_group_user y WHERE " +
                "x.admin_group_id=y.admin_group_id AND y.user_id=:user_id AND x.category_id=:category_id";
        Map<String, Object> hs = new HashMap<>();
        hs.put("category_id", categoryId);
        hs.put("user_id", userId);
        long count = baseDao.querySqlCountByMap(hql, hs);
        if (count > 0)
            return true;
        else
            return false;
    }

    //TODO 不能唯一
    public List<User>getAdminByCategory(int category){
        AdminGroupCategory agc = (AdminGroupCategory) adminGroupCategoryDao.findUnique("categoryId", category, AdminGroupCategory.class);
        return adminGroupUserDao.query("from AdminGroupUser where adminGroupId=?",agc.getAdminGroupId())
                .stream().map(agu->agu.getUserId()).map(uid->userService.getUserById(uid))
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesWhichAdminHasRight(Integer userId) {

        User user = userService.getUserById(userId);
        List<Course> courses = courseService.list();

        if ("ROLE_SADMIN".equals(user.getAuth())) {
            return courses;
        }
        if ("ROLE_ADMIN".equals(user.getAuth())) {

            List<Course> rightCourses = new ArrayList<>();
            for (Course course : courses) {
                if (isAdminHasRightToCourse(course.getCategoryId(), userId))
                    rightCourses.add(course);
            }
            return rightCourses;
        }
        return new ArrayList<>();

    }
}
