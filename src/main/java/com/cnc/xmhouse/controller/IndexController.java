package com.cnc.xmhouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhuangjy on 2016/7/21.
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @RequestMapping("")
    public String index(Model model){

        return "index";
    }
}
