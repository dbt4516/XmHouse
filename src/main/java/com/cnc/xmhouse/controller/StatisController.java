package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.entity.Highchart;
import com.cnc.xmhouse.service.StaticService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * Created by hongzhan
 */
@Controller
@RequestMapping("/statis")
public class StatisController {

    @Autowired
    private StaticService staticService;
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StatisController.class);


    @RequestMapping("")
    public String list(){
        return "statis";
    }

    @RequestMapping("/ajax/getText")
    @ResponseBody
    public Map get(long start,long end){
        Map<String,Object>ret=new HashMap<>();
        ret.put("data",staticService.getText(start,end));
        return ret;
    }

    @RequestMapping("/ajax/getDiagrams")
    @ResponseBody
    public Map getDiagram(long start,long end){
        Map<String,Object>ret=new HashMap<>();
        Map<String,Highchart>charts=new HashMap<>();
        charts.put("trendLine",staticService.getTrendLine(start,end));
        charts.put("areaDis",staticService.getAreaDis(start,end));
        charts.put("salePie",staticService.getSalePie(start,end));
        ret.put("data",charts);
        return ret;
    }



}
