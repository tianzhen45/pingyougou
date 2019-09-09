package com.tianzhen.sms.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.service.SmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Reference(timeout = 30000)
    private SmsService smsService;

    @PostMapping("/sendSms")
    public Map<String, Object> sendSms(String phone, String signName, String templateCode, String templateParam){
        boolean success = smsService.sendSms(phone, signName, templateCode, templateParam);
        Map<String,Object> map = new HashMap<>();
        map.put("success",success);
        return map;
    }


    @GetMapping("/test")
    public String test(){
        return "success";
    }

}
