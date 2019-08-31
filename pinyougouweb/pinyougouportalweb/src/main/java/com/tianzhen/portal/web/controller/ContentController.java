package com.tianzhen.portal.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.Content;
import com.tianzhen.service.ContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @GetMapping("/findContentByCategoryId")
    public List<Content> findContentByCategoryId(String categoryId){
        List<Content> contentList = contentService.findContentByCategoryId(categoryId);
        return contentList;
    }
}
