package com.cnc.xmhouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhuangjy on 2016/7/21.
 */
//@Controller
public class AuthController {
    @RequestMapping("/login")
    public String login(String err, Model model) {
        if (!StringUtils.isEmpty(err))
            model.addAttribute("authError", "身份验证失败,请重新输入!");
        return "login";
    }

    @RequestMapping("/deny")
    public String deny() {
        return "deny";
    }
}
