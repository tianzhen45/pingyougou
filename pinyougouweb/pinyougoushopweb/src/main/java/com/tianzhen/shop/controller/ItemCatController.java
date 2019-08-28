package com.tianzhen.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.ItemCategory;
import com.tianzhen.service.ItemCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController{

    @Reference(timeout = 10000)
    private ItemCategoryService itemCategoryService;

    @GetMapping("/findItemCatByParentId")
    public List<ItemCategory> findItemCatByParentId(@RequestParam(defaultValue = "0") Integer parentId){
        return itemCategoryService.findByParentId(parentId);
    }



}
