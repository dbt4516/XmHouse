package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.service.StaticService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * Created by linsy1 on 2016/7/24.
 */
@Controller
@RequestMapping("/statis")
public class StatisController {

    @Autowired
    private StaticService staticService;
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StatisController.class);


    @RequestMapping("")
    public String list(Model model){
        return "statis";
    }

    @RequestMapping("/ajax/get")
    @ResponseBody
    public Map get(long start,long end){
        Map<String,Object>ret=new HashMap<>();
        ret.put("data",staticService.get(start,end));
        return ret;
    }

}
