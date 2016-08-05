package com.cnc.xmhouse.controller;

import com.baidu.ueditor.ActionEnter;
import com.cnc.xmhouse.util.DateUtil;
import com.cnc.xmhouse.util.FileUtil;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by zhuangjy on 2016/7/23.
 */
@Controller
@RequestMapping("/ueditor")
public class EditorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditorController.class);

    @RequestMapping(value = "/config", method = RequestMethod.GET, params = "action=config")
    @ResponseBody
    public String config(HttpServletRequest request) throws JSONException {
        String rootPath = request.getSession()
                .getServletContext().getRealPath("/");
        return new ActionEnter(request, rootPath).exec();
    }

    @RequestMapping(value = "/config", method = RequestMethod.POST, params = "action=upload-image")
    @ResponseBody
    public String uploadImg(@RequestParam(value = "upfile") MultipartFile upFile,
                            HttpServletRequest request) throws UnsupportedEncodingException {
        String rootPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        //生成图片存储路径
        Date date = new Date();
        //图片存储相对路径
        String imgRelativePath = "/img/question/" + DateUtil.format(date, "yyyyMMdd") + "/";
        //图片系统存储绝对路径
        String imgRealPath = rootPath + imgRelativePath;
        String result = "";
        FileOutputStream out = null;
        try {
            File file = FileUtil.uploadImg(upFile,imgRealPath);
            if(file == null)
                result = "{\"state\": \"FAIL\"}";
            String postfix = upFile.getOriginalFilename().substring(upFile.getOriginalFilename().indexOf("."));
            result = "{'name':'" + file.getName() + "', 'originalName': '" + upFile.getOriginalFilename() + "', 'size': '" +
                        upFile.getSize() + "', 'state': 'SUCCESS', 'type': '" + postfix + "', 'url': '" + imgRelativePath + file.getName() + "'}";
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return result;
    }
}
