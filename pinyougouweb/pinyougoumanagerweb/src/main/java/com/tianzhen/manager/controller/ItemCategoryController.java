package com.tianzhen.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.ItemCategory;
import com.tianzhen.service.ItemCategoryService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/itemCategory")
@RestController
public class ItemCategoryController {

    @Reference(timeout = 10000)
    private ItemCategoryService itemCategoryService;

    @GetMapping("/findByParentId")
    public List<ItemCategory> findByParentId(Integer parentId){
        return itemCategoryService.findByParentId(parentId);
    }

    @PostMapping("/save")
    public String save(@RequestBody ItemCategory itemCategory){
       if(StringUtils.isEmpty(itemCategory.getId())) {
           itemCategoryService.save(itemCategory);
       }else {
           itemCategoryService.update(itemCategory);
       }

       return "ok";
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Integer[] ids){
        if(ids != null && ids.length >0) {
            return itemCategoryService.deleteByArr(ids);
        }
        return false;
    }
}
