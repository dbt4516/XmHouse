package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.lagacy_entity.bean.Notification;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhuangjy on 2016/7/26.
 */

@Controller
@RequestMapping(value = "/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    @ResponseBody
    public Long userUnread() {
//        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return 1l;
    }

    @RequestMapping("/{page}/{limit}")
    @ResponseBody
    public List<Notification> Notification(@PathVariable("page") Integer page,
                                           @PathVariable("limit") Integer limit) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificationService.lastNotification(userInfo.getId(), page, limit);
    }

    @RequestMapping("/last")
    @ResponseBody
    public List<Notification> last() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        notificationService.allRead(userInfo.getId());
        return notificationService.lastNotification(userInfo.getId(), 0, 5);
    }

    @RequestMapping("/list")
    public String list(Model model){
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Notification> notifications = notificationService.list(userInfo.getId());
        model.addAttribute("notifications",notifications);
        return "/notification/list";
    }
}
