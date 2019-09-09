package com.tianzhen.item.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.Goods;
import com.tianzhen.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;


@Controller
public class ItemContrller {

    @Reference(timeout = 10000)
    private GoodsService goodsService;


    @GetMapping("/{goodsId}")
    public String getGoods(@PathVariable("goodsId")Long goodsId,Model model){
        Map<String,Object> data = goodsService.getGoods(goodsId);
        model.addAllAttributes(data);
        return "item";
    }
}
