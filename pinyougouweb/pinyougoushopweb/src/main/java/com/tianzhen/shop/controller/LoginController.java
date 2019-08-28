package com.tianzhen.shop.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {



    @PostMapping("/login")
    public String login(String username,String password){
        try {
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

            subject.login(usernamePasswordToken);

            //是否通过验证
            if (subject.isAuthenticated()) {
                return "redirect:/admin/index.html";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/shoplogin.html";
    }

    @GetMapping("/findLoginName")
    @ResponseBody
    public Map<String,Object> findLoginName(){
        String loginName = SecurityUtils.getSubject().getPrincipal().toString();
        Map<String,Object> data = new HashMap<>();
        data.put("loginName",loginName);
        return data;
    }
}
