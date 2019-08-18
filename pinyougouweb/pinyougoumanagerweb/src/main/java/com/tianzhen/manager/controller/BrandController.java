package com.tianzhen.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.Brand;
import com.tianzhen.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandController {
    @Reference(timeout = 10000)
    private BrandService brandService;

    @GetMapping("/brand/findAll")
    public List<Brand> findAll(){
        List<Brand> all = brandService.findAll();
        return all;
    }

}


