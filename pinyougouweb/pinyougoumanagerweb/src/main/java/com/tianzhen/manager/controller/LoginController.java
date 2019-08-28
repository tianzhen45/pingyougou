package com.tianzhen.manager.controller;


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
@RequestMapping("/user")
public class LoginController {


    @PostMapping("/login")
    public String login(String username,String password){

        try {
            //获取认证的主题
            Subject subject = SecurityUtils.getSubject();
            //提交的登录信息创建token
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //身份认证
            subject.login(token);
            //判断是否通过认证
            if(subject.isAuthenticated()){
                return "redirect:/admin/index.html";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/login.html";
    }

    @GetMapping("/findLoginName")
    @ResponseBody
    public Map<String,String> findLoginName(){
        //获取登录用户名
        //获取认证的主题
        Subject subject = SecurityUtils.getSubject();
        String loginName = subject.getPrincipal().toString();
        Map<String,String> data = new HashMap<>();
        data.put("loginName",loginName);
        return data;
    }
}
