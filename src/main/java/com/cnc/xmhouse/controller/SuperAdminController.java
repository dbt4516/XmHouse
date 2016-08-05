package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.lagacy_entity.*;
import com.cnc.xmhouse.enums.Role;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.CategoryService;
import com.cnc.xmhouse.service.SuperAdminService;
import com.cnc.xmhouse.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhuangjy on 2016/7/27.
 */
@Controller
@RequestMapping("/super")
public class SuperAdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuperAdminController.class);
    @Autowired
    private SuperAdminService superAdminService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public String gotoSuper(Model model) {
        List<AdminGroup> adminGroups = superAdminService.listAdminGroup();
        model.addAttribute("groups", adminGroups);
        model.addAttribute("current","admin");
        return "/super/admin_group_list";
    }

    @RequestMapping("/manage/{groupId}")
    public String gotoManage(@PathVariable("groupId") Integer groupId, Model model) {
        //获取当前组信息
        AdminGroup adminGroup = superAdminService.getAdminGroupById(groupId);
        //获取组员用户信息
        List<AdminGroupUser> adminGroupUser = superAdminService.getAdminGroupUsersByGroupId(groupId);
        List<Integer> ids = adminGroupUser.stream().map(AdminGroupUser::getUserId).collect(Collectors.toList());
        List<User> users = userService.getUsers(ids);
        //获取所有邮箱用于自动提示
        List<String> mails = userService.loadAllUserMails(ids);

        //获取小组权限信息
        List<AdminGroupCategory> adminGroupCategory = superAdminService.getAdminGroupCategorysByGroupId(groupId);
        //已添加的分类id
        ids = adminGroupCategory.stream().map(AdminGroupCategory::getCategoryId).collect(Collectors.toList());
        //获取未添加的分类
        List<Category> category = categoryService.list();
        List<Category> unOccupiedCategory = new ArrayList<>();
        for(Integer id :ids){
            unOccupiedCategory.addAll(category.stream().filter(c -> c.getId() == id).collect(Collectors.toList()));
        }
        //获得已拥有权限
        List<Category> occupiedCategory = categoryService.list(ids);
        model.addAttribute("mails", mails);
        model.addAttribute("group", adminGroup);
        model.addAttribute("users", users);
        //所有分类
        model.addAttribute("categories", unOccupiedCategory);
        //已添加的分类
        model.addAttribute("category",occupiedCategory);
        model.addAttribute("current","admin");
        return "/super/admin_group_manage";
    }

    @RequestMapping(value = "/add/group", method = RequestMethod.POST)
    @ResponseBody
    public boolean createAdminGroup(AdminGroup adminGroup) {
        superAdminService.createAdminGroup(adminGroup);
        LOGGER.info("{} 添加管理组",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return true;
    }

    //TODO 防止超级管理员被添加
    @RequestMapping(value = "/add/user", method = RequestMethod.POST)
    @ResponseBody
    public boolean addAdminGroupMember(Integer adminGroupId, String mail) {
        User user = userService.getUserByMail(mail);
        if(user.getAuth().equals("SADMIN"))
            return false;
        AdminGroupUser adminGroupUser = new AdminGroupUser(adminGroupId, user.getId());
        userService.updateUserAuth(user.getId(), Role.ADMIN.getName());
        superAdminService.addAdminGroupMember(adminGroupUser);
        LOGGER.info("{} 添加管理组成员",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return true;
    }

    @RequestMapping(value = "/add/auth", method = RequestMethod.POST)
    @ResponseBody
    public boolean addAuth(AdminGroupCategory adminGroupCategory) {
        superAdminService.addAuth(adminGroupCategory);
        LOGGER.info("{} 添加管理组权限",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return true;
    }

    @RequestMapping(value = "/dismissal/group", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteAdminGroup(Integer groupId) {
        //获取该组所有成员id
        List<Integer> ids = superAdminService.getUsersIdsInGroup(groupId);
        superAdminService.deleteAdminGroup(groupId);
        //设置ROLE
        ids = superAdminService.getUnExistUsers(ids);
        userService.updateUserAuth(ids, Role.USER.getName());
        LOGGER.info("{} 删除管理组权限",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return true;
    }

    @RequestMapping(value = "/dismissal/user", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteAdminUser(AdminGroupUser adminGroupUser) {
        superAdminService.deleteAdminUser(adminGroupUser);
        if(!superAdminService.isUserInOtherGroup(adminGroupUser.getUserId(),adminGroupUser.getAdminGroupId()))
            userService.updateUserAuth(adminGroupUser.getUserId(), Role.USER.getName());
        LOGGER.info("{} 删除管理成员权限",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return true;
    }

    @RequestMapping(value = "/dismissal/auth", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteAdminUser(AdminGroupCategory adminGroupCategory) {
        superAdminService.deleteAuth(adminGroupCategory);
        LOGGER.info("{} 删除管理组权限",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return true;
    }




}
