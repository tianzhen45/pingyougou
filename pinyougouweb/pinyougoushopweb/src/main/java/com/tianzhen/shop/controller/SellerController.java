package com.tianzhen.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.Seller;
import com.tianzhen.service.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;


    @RequestMapping("/save")
    public String register(@RequestBody Seller seller){
        try{
            sellerService.save(seller);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "success";
    }

}
