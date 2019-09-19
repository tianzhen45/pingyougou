package com.tianzhen.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Brand;
import com.tianzhen.service.BrandService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference(timeout = 10000)
    private BrandService brandService;

    @GetMapping("/findAll")
    public List<Brand> findAll(){
        List<Brand> all = brandService.findAll();
        return all;
    }

    @PostMapping("/save")
    public String save(@RequestBody Brand brand){
        if(StringUtils.isEmpty(brand.getId())){

            brandService.save(brand);
        }else {
            brandService.update(brand);
        }
        return "ok";
    }

    @GetMapping("/findByPage")
    public PageInfo<Brand> findByPage(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "5") int pageSize){

        return brandService.findByPage(pageNum,pageSize);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody String[] ids){
        for (String id : ids) {
            brandService.delete(Integer.valueOf(id));
        }
        return "ok";
    }

    @GetMapping("/findAllByIdAndName")
    public List<Map<String, Object>> findAllByIdAndName(){
        List<Map<String, Object>> all = brandService.findAllByIdAndName();
        return all;

    }

}


