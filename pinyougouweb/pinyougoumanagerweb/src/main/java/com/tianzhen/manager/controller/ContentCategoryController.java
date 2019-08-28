package com.tianzhen.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.ContentCategory;
import com.tianzhen.service.ContentCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {
    @Reference(timeout = 10000)
    private ContentCategoryService contentCategoryService;
    
    
    @GetMapping("/findByPage")
    public PageInfo<ContentCategory> findByPage(ContentCategory contentCategory, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize){
        return contentCategoryService.findByPage(contentCategory,page,pageSize);
    }

    @GetMapping("/findAll")
    public List<ContentCategory> findAll(){
        return contentCategoryService.findAll();
    }
    
}
