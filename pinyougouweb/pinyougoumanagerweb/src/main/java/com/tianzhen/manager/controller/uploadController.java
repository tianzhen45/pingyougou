package com.tianzhen.manager.controller;

import com.tianzhen.manager.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class uploadController {
    @Autowired
    private UploadUtils uploadUtils;

    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file")MultipartFile file){
        Map<String,Object> data = new HashMap<>();
        data.put("status",500);
        try{
            String url = uploadUtils.upload(file);
            data.put("url","http://"+url);
            data.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
