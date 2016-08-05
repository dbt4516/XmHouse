package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.dao.RedisDao;
import com.cnc.xmhouse.lagacy_entity.UserNotification;
import com.cnc.xmhouse.lagacy_entity.bean.Notification;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuangjy on 2016/7/26.
 */
@Service
public class NotificationService {
    @Autowired
    private UserService userService;
    @Autowired
    private BaseDao<UserNotification> baseDao;

    private RedisDao redisDao;

    public Long userUnread(Integer userId) {
        return 1l;
//        Long unRead = redisDao.getUnreadCount(userId);
//        if (unRead == null) {
//            String hql = "SELECT COUNT(*) FROM UserNotification WHERE userId=:userId AND State=0";
//            Map<String, Object> hs = new HashMap<>();
//            hs.put("userId", userId);
//            unRead = baseDao.queryHqlCount(hql, hs);
//            redisDao.putUnreadCount(userId, unRead);
//        }
//        return unRead;
    }

    /**
     * 标志该用户所以信息均为已读
     *
     * @param userId
     */
    public void allRead(Integer userId) {
        //先获取未读数量如果不为0才执行更改才做
        Long num = redisDao.getUnreadCount(userId);
        if (num != 0) {
            String hql = "UPDATE UserNotification SET state=1 WHERE userId=:userId";
            Map<String, Object> hs = new HashedMap();
            hs.put("userId", userId);
            baseDao.executeUpdateHqlByMap(hql, hs);
            redisDao.putUnreadCount(userId, 0l);
        }
    }

    /**
     * 获取用户所有消息
     * @param id
     * @return
     */
    public List<Notification> list(Integer id){
        String hql = "FROM UserNotification WHERE userId=:userId";
        Map<String,Object> hs = new HashedMap();
        hs.put("userId",id);
        List<UserNotification> userNotifications = baseDao.query(hql,hs);
        List<Notification> res = new ArrayList<>();
        for(UserNotification u:userNotifications){
            Notification n = redisDao.getNotification(u.getNotificationId());
            //TODO 过期数据MYSQL 删除
            if(n != null) {
                n.setTime(u.getTime());
                res.add(n);
            }
        }
        return res;
    }

    /**
     * 获取最新数据
     *
     * @param userId 用户id
     * @param page   页码
     * @param limit  每页数量
     * @return
     */
    public List<Notification> lastNotification(Integer userId, Integer page, Integer limit) {
        String sql = "SELECT `notification_id` AS `notificationId`,`time` AS `time` FROM `user_notification` WHERE `user_id` =:userId ORDER BY `id` DESC LIMIT :page,:limit";
        Map<String, Object> hs = new HashedMap();
        hs.put("userId", userId);
        hs.put("page", page);
        hs.put("limit", limit);
        List<Map> userNotification = baseDao.querySqlMapByMap(sql, hs);
        List<Notification> res = new ArrayList<>();
        for (Map<String, Object> map : userNotification) {
            Notification notification = redisDao.getNotification((String) map.get("notificationId"));
            notification.setTime((Timestamp) map.get("time"));
            if (notification != null)
                res.add(notification);
        }
        return res;
    }


    public void notifyAllUser(Notification notification) {
        redisDao.putNotification(notification);
        List<Integer> users = userService.loadAllUserIds();
        UserNotification userNotification = null;
        for (Integer id : users) {
            userNotification = new UserNotification(id, notification.getId());
            baseDao.save(userNotification);
        }
        redisDao.plusUnreadToMany(users, 1l);

    }

    public void notifyUser(Notification notification, Integer userId) {
        redisDao.putNotification(notification);
        redisDao.plusUnreadToUser(userId, 1l);
        UserNotification userNotification = new UserNotification(userId, notification.getId());
        baseDao.save(userNotification);
    }

    public void notifyUsers(Notification notification, List<Integer> ids) {
        UserNotification userNotification = null;
        for (Integer id : ids) {
            userNotification = new UserNotification(id, notification.getId());
            baseDao.save(userNotification);
        }
        redisDao.plusUnreadToMany(ids, 1l);
    }
}
