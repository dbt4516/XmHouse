package com.cnc.xmhouse.service;



/**
 * Created by huangmh1 on 2016/7/22.
 */


import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.Message;
import com.cnc.xmhouse.lagacy_entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageService {

    @Autowired
    private BaseDao<Message> baseDao;
    @Autowired
    private UserService userService;


    //通过courseId查询，分页
    public List<Message> messageQueryByCourseId(Integer courseId,Integer page, Integer limit) {
        return baseDao.queryWithStartLimit("from Message where courseId= "+courseId+" order by message_time desc", page, limit);
    }



   //通过userId查询，分页
    public List<Message> messageQueryByUserId(Integer userId,Integer page, Integer limit){
        return baseDao.queryWithStartLimit("from Message where userId="+userId+" order by message_time desc", page, limit);
    }

    public long getCount(Integer courseId){
        return baseDao.queryHqlCount("SELECT COUNT(*) From Message Where course_id="+courseId);}

    //通过课程ID查询留言列表
    public List<Message> messageQueryByCourseId(Integer courseId){
            return baseDao.query("from Message where courseId=?",courseId);
    }


    //通过用户ID查询留言列表
    public List<Message> messageQueryByUserId(Integer userId){
        return baseDao.query("from Message where userId=?",userId);
    }

    //提交留言，进行保存
    public boolean messageCommit(Integer courseId, String content, Integer userId){
        Message message=new Message();
        message.setCourseId(courseId);
        message.setUserId(userId);
        message.setContent(content);

        java.util.Date  date=new java.util.Date();
        java.sql.Timestamp  data1=new java.sql.Timestamp(date.getTime());
        message.setMessageTime(data1);
        baseDao.save(message);
        return true;
    }
    /**
     * 设置留言的用户名
     * @param message
     * */
    public Message setUserNameToMessage(Message message){
        User user = userService.getUserById(message.getUserId());
        message.setUserName(user.getUserName());
        return message;
    }

}
