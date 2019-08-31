package com.tianzhen.search.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.service.ItemSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ItemSearchController {

    @Reference(timeout = 30000)
    private ItemSearchService itemSearchService;

    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody Map<String,Object> params){

        Map<String, Object> map = itemSearchService.searchHighlight(params);
        return map;
    }
}
