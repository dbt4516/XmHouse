package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongzhan on 2016/8/9.
 */


@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @RequestMapping("")
    public String list(){
        return "history";
    }


    @RequestMapping("/ajax/get")
    @ResponseBody
    public Map get(long day){
        Map<String,Object>ret=new HashMap<>();
        ret.put("data",historyService.getDay(day));
        return ret;
    }

}
