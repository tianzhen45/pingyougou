package com.tianzhen.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Specification;
import com.tianzhen.pojo.SpecificationOption;
import com.tianzhen.service.SpecificationService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecController {
    @Reference(timeout = 10000)
    private SpecificationService specificationService;

    @GetMapping("/findAll")
    public List<Specification> findAll(){
        List<Specification> all = specificationService.findAll();
        return all;
    }

    @PostMapping("/save")
    public String save(@RequestBody Specification specification){
        if(StringUtils.isEmpty(specification.getId())){

            specificationService.save(specification);
        }else {
            specificationService.update(specification);
        }
        return "ok";
    }

    @GetMapping("/findByPage")
    public PageInfo<Specification> findByPage(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "5") int pageSize){

        return specificationService.findByPage(pageNum,pageSize);
    }

    @PostMapping("/delete")
    public String delete(@RequestBody String[] ids){
        for (String id : ids) {
            specificationService.delete(Integer.valueOf(id));
        }
        return "ok";
    }

    @GetMapping("/findSpecificationOptions")
    public List<SpecificationOption> findSpecificationOptions(Integer id){
        return specificationService.findSpecificationOptions(id);
    }

    @GetMapping("/findAllByIdAndName")
    public List<Map<String, Object>> findAllByIdAndName(){
        List<Map<String, Object>> all = specificationService.findAllByIdAndName();
        return all;
    }

}


