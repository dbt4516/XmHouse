package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.Dept;
import com.cnc.xmhouse.lagacy_entity.User;
import com.cnc.xmhouse.framework.security.UserInfo;
import com.cnc.xmhouse.service.UserExamService;
import com.cnc.xmhouse.service.UserService;
import com.cnc.xmhouse.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hongzhan on 2016/7/28.
 */

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private BaseDao<User>userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserExamService ueService;

  //  private Logger logger= LoggerFactory.getLogger(UserController.class);
    /**
     * 添加用户显示页面
     * */
    @RequestMapping("/add")
    public String add(){
        return "/user/add";
    }
    /**
     * 添加用户
     * */
    @RequestMapping(value = "ajax/addUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer> addUser(User user){
        return new AjaxResultInfo<>(0,(null != userService.save(user)),"");
    }
    /**
     * 进入用户列表页面
     * */
    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("count",userService.getUsersCount());
        return "/exam/list";
    }
    /**
     * 用户列表
     * */
    @RequestMapping("/{page}/{limit}")
    public List<User> getUsers(@PathVariable("page") Integer page,
                                 @PathVariable("limit") Integer limit) {

        return userService.getUsers(page,limit);
    }
    /**
     *查看用户详情
     */
    @RequestMapping(value = "ajax/getUser", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultInfo<User>getUser(Integer userId){
        return new AjaxResultInfo<>(userService.getUserById(userId),true,"");
    }
    /**
     * 修改用户显示页面
     * */
    @RequestMapping(value = "/update/{userId}")
    public String showUpdate(@PathVariable("userId")
                                     Integer userId, Model model) {
        model.addAttribute("user",userService.getUserById(userId));
        return "user/update";
    }
    /**
     * 修改用户信息
     * */
    @RequestMapping(value = "ajax/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultInfo<Integer>updateUser(User user){
        userService.updateUser(user);
        LOGGER.info("{} 更新信息",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return new AjaxResultInfo<>(0,true,"");
    }

    /**
     * 删除用户
     * */
    @RequestMapping(value = "ajax/removeUser")
    @ResponseBody
    public AjaxResultInfo<Integer>removeUser(Integer userId){
        userService.removeUser(userId);
        return new AjaxResultInfo<>(0,true,"");
    }

    @RequestMapping(value = "ajax/getDeptUser", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultInfo<List<User>> getDeptUser(int deptId){
        return new AjaxResultInfo<>(userService.getUsersByDept(deptId),true,"");
    }

  @RequestMapping(value = "ajax/getsUserByName", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultInfo<List<User>> getsUserByName(String name){
        return new AjaxResultInfo<>(userService.getsUserByName(name),true,"");
    }

    @RequestMapping(value = "ajax/getDepts", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultInfo<List<Dept>> getDeptUser(){
        return new AjaxResultInfo<>(userService.getDepts(),true,"");
    }


    @RequestMapping(value = "ajax/uploadXls", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadXls(@RequestParam(value = "resources") MultipartFile origin,long examId)  {
        Map<String,Object>ret=new HashMap<>();
        List<User>success=new LinkedList<>();
        List<String>failed=new LinkedList<>();
        List<String> userCodes =null;
        try {
            userCodes = ExcelUtil.readExcelContent(origin.getInputStream(), 0);
        }catch (Exception e){
            LOGGER.info("{} 错误信息 {} ",((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),e.getMessage());


        }
        if(userCodes!=null && userCodes.size()>0) {
            for (String uCode : userCodes) {
                User user = userService.getUserByNumber(uCode);
                if(user==null){
                    failed.add(uCode);
                }else{
                    success.add(user);
                }
            }
            ueService.setExamUsers(examId,success.stream().map(u->u.getId()).collect(Collectors.toList()));
            ret.put("imported",success);
            ret.put("failed",failed);
            ret.put("success",true);
            return ret;
        }else{
            ret.put("success",false);
            return ret;
        }
    }
}
