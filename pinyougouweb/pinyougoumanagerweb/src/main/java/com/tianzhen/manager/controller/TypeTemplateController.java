package com.tianzhen.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.TypeTemplate;
import com.tianzhen.service.TypeTemplateService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    @GetMapping("/findAll")
    public List<TypeTemplate> findAll(){
        List<TypeTemplate> all = typeTemplateService.findAll();
        return all;
    }

    @PostMapping("/save")
    public String save(@RequestBody TypeTemplate typeTemplate){
        if(StringUtils.isEmpty(typeTemplate.getId())){

            typeTemplateService.save(typeTemplate);
        }else {
            typeTemplateService.update(typeTemplate);
        }
        return "ok";
    }

    @GetMapping("/findByPage")
    public PageInfo<TypeTemplate> findByPage(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "5") int pageSize){

        return typeTemplateService.findByPage(pageNum,pageSize);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody String[] ids){
        for (String id : ids) {
            typeTemplateService.delete(Integer.valueOf(id));
        }
        return "ok";
    }

    @GetMapping("/findByIdAndName")
    public List<Map<String,Object>> findByIdAndName(){
        return typeTemplateService.findByIdAndName();
    }


}
