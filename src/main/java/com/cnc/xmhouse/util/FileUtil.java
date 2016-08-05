package com.cnc.xmhouse.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhuangjy on 2016/7/26.
 */
public class FileUtil {
    public static File createFile(String savePath, String name) throws IOException {
        File savePathFile = new File(savePath);
        if (!savePathFile.exists())
            savePathFile.mkdirs();
        File file = new File(savePathFile, name);
        file.createNewFile();
        return file;
    }

    public static File transferToFile(String savePath, String name, MultipartFile origin) throws IOException {
        File file = createFile(savePath, name);
        origin.transferTo(file);
        return file;
    }

    //设置下载文件Header
    public static HttpHeaders downloadHeader(File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());
        return headers;
    }

    //图片上传使用默认命名方式
    public static File uploadImg(MultipartFile upFile, String imgPath) throws IOException {
        FileOutputStream out = null;
        String postfix = upFile.getOriginalFilename().substring(upFile.getOriginalFilename().indexOf("."));
        String fName = Math.round(Math.random() * 1000000) + "" + new Date().getTime() + postfix;
        File file = createFile(imgPath, fName);
        out = new FileOutputStream(imgPath + fName);
        out.write(upFile.getBytes());
        out.close();
        return file;
    }

    //获取指定路径下的最新的一份log文件： 文件名以日期排序
    public static File getYesterdayLog(String path) {
        File files = new File(path);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        for (String s : files.list())
            if (s.indexOf(yesterday) != -1)
                return new File(path + s);
        return null;
    }
}
