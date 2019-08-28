package com.tianzhen.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.TypeTemplate;
import com.tianzhen.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    @Reference
    private TypeTemplateService typeTemplateService;


    @GetMapping("/findById")
    public TypeTemplate findById(String id){
        TypeTemplate typeTemplate = typeTemplateService.findById(id);
        return typeTemplate;
    }

    @GetMapping("/findSpecByTemplateId")
    public List<Map> findSpecByTemplateId(Long id){
        return typeTemplateService.findSpecByTemplateId(id);
    }
}
