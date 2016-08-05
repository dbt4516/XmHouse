package com.cnc.xmhouse.controller;


import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.Course;
import com.cnc.xmhouse.lagacy_entity.Message;
import com.cnc.xmhouse.lagacy_entity.User;
import com.cnc.xmhouse.lagacy_entity.bean.Notification;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.CourseService;
import com.cnc.xmhouse.service.MessageService;
import com.cnc.xmhouse.service.NotificationService;
import com.cnc.xmhouse.service.SuperAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


/**
 * Created by huangmh1 on 2016/7/22.
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SuperAdminService superAdminService;
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public AjaxResultInfo<Integer> recordMessage(Integer courseId, String content) {
        Integer userId =  ((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (null != content && null!=courseId  && null!=userId ) {
            messageService.messageCommit(courseId, content, userId);
            Course course = courseService.getCourseById(courseId);
            List<User> users = superAdminService.getAdminByCategory(course.getCategoryId());
            if(null != users){
                for(User user:users){
                    Notification notification = new Notification("您有新留言");//提示所有有权限的管理员有新留言
                    notificationService.notifyUser(notification,user.getId());
                }
            }
            LOGGER.info("{} 添加留言",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

           return new AjaxResultInfo<>(1,true,"");
        }
        return new AjaxResultInfo<>(0,false,"留言内容不能为空或者该课程不存在！");
    }

    @RequestMapping(value = "/course/{courseId}/{page}/{limit}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getMessageByCourseId(@PathVariable("courseId") Integer courseId, @PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        List<Message> messages = messageService.messageQueryByCourseId(courseId,page,limit);
        if(null != messages)
            for(Message message:messages) messageService.setUserNameToMessage(message);
        return messages;
    }

    @RequestMapping(value = "/{userId}/{page}/{limit}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getMessageByUserId(@PathVariable("userId") Integer userId,@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        return messageService.messageQueryByUserId(userId,page,limit);
    }
}
