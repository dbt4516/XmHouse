package com.cnc.xmhouse.controller;

import com.cnc.xmhouse.dto.AjaxResultInfo;
import com.cnc.xmhouse.lagacy_entity.Resource;
import com.cnc.xmhouse.service.ResourceService;
import com.cnc.xmhouse.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by linsy1 on 2016/7/25.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    /**
     * 下载资源
     * */
    @RequestMapping("download/{resourceId}")
    public ResponseEntity<byte[]> download(@PathVariable("resourceId")long resourceId,
                                                       HttpServletRequest request
                                                       ) throws IOException {
        Resource resource = resourceService.getResourceById(resourceId);
        String rootPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        String filePath = rootPath + "file" + "/course/";
        File file = new File(filePath+resource.getSource());

        HttpHeaders headers = FileUtil.downloadHeader(file);
        headers.setContentDispositionFormData("attachment", file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
    /**
     * 删除资源
     * */
    @RequestMapping("remove/{resourceId}")
    @ResponseBody
    public AjaxResultInfo<Integer> remove(@PathVariable("resourceId")
                                                      HttpServletRequest request,
                                                      long resourceId) {
        String rootPath = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        String filePath = rootPath + "file" + "/course/";
        resourceService.remove(resourceId,filePath);

        return new AjaxResultInfo(1,true,"");

    }


}
