package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.lagacy_entity.Log;
import com.cnc.xmhouse.enums.LogType;
import com.cnc.xmhouse.service.LogService;
import com.cnc.xmhouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by huangmh1 on 2016/7/27.
 */
@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String gotoLog(Model model) {
        List<String> mails = userService.loadAllUserMails();
        model.addAttribute("mails", mails);
        model.addAttribute("type", LogType.values());
        model.addAttribute("current","sadmin");
        return "/log/list";
    }

    @RequestMapping(value = "/{page}/{limit}", method = RequestMethod.GET)
    @ResponseBody
    public List<Log> getLogs(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit,
                                        Log log, String startTime, String endTime) {
        return logService.find(log, startTime, endTime, page, limit);
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public long getLogsNum(Log log, String startTime, String endTime){
        return logService.findCount(log,startTime,endTime);
    }
}
