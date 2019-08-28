package com.tianzhen.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.Seller;
import com.tianzhen.service.SellerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;


    @GetMapping("/findAll")
    public List<Seller> findAll(){
        return sellerService.findAll();
    }

    @GetMapping("/findById")
    public Seller findById(String sellerId){
        return sellerService.findById(sellerId);
    }

    @GetMapping("/updateStatus")
    public String updateStatus(Seller seller){
        try {
            sellerService.update(seller);
        }catch (Exception e){
            return "";
        }
        return "success";
    }

}
