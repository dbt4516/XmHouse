package com.cnc.xmhouse.dao;

import com.cnc.xmhouse.lagacy_entity.bean.Notification;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by zhuangjy on 2016/7/26.
 */
public class RedisDao {



    public RedisDao(String ip, int port) {

    }

    public Notification getNotification(String id) {
        return null;
    }

    public String putNotification(Notification n) {
         return null;
    }

    public Long getUnreadCount(Integer userId) {

        return null;
    }

    public String putUnreadCount(Integer userId, Long num) {
         return null;
    }

    public void plusUnreadToMany(List<Integer> ids, Long num) {

    }

    public void plusUnreadToUser(Integer id,Long num){

    }

    public void plusUnread(Jedis jedis, Long num, Integer id) {

    }
}


