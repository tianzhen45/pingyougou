package com.tianzhen.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Goods;
import com.tianzhen.service.GoodsService;
import com.tianzhen.shop.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    @Autowired
    private UploadUtils uploadUtils;

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

    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file){
        Map<String,Object> data = new HashMap<>();
        data.put("status","500");
        try{
            String url = uploadUtils.upload(file);
            data.put("url","http://"+url);
        }catch (Exception e){
            e.printStackTrace();
        }
        data.put("status","200");
        return data;
    }

    @GetMapping("/findByPage")
    public PageInfo<Goods> findByPage(Goods goods,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize){
        PageInfo<Goods> pageInfo = goodsService.findByPage(goods,page, pageSize);
        return pageInfo;
    }

    @GetMapping("/updateMarketable")
    public boolean updateMarketable(String[] ids,String marketable){
        try{
            if(ids != null && ids.length  > 0){
                for (String id : ids) {
                    Goods goods = new Goods();
                    goods.setId(Long.valueOf(id));
                    goods.setIsMarketable(marketable);
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
