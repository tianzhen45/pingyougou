package com.tianzhen.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Goods;
import com.tianzhen.service.GoodsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;



    @PostMapping("/save")
    public Boolean save(@RequestBody Goods goods){
        try{
            goodsService.save(goods);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @GetMapping("/findByPage")
    public PageInfo<Goods> findByPage(Goods goods,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize){
        PageInfo<Goods> pageInfo = goodsService.findByPage(goods,page, pageSize);
        return pageInfo;
    }

    @GetMapping("/updateStatus")
    public boolean upodateStatus(String[] ids,String status){
        try {
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    Goods goods = new Goods();
                    goods.setId(Long.valueOf(id));
                    goods.setAuditStatus(status);
                    goodsService.update(goods);
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/delete")
    public boolean delete(String[] ids){
        try{
            if(ids != null && ids.length > 0){
                for (String id : ids) {
                    goodsService.deleteLogic(id);
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
