package com.tianzhen.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Content;
import com.tianzhen.service.ContentService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Reference(timeout = 10000)
    private ContentService contentService;


    @GetMapping("/findByPage")
    public PageInfo<Content> findByPage(Content content, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize){
        return contentService.findByPage(content,page,pageSize);
    }


    @PostMapping("/save")
    public boolean save(@RequestBody Content content){
        try {
            contentService.save(content);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Content content){
        try {
            contentService.update(content);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/delete")
    public boolean delete(String[] ids){
        try {
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    contentService.delete(id);
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

}
