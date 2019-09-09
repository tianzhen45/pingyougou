package com.tianzhen.user.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.User;
import com.tianzhen.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {



    @Reference(timeout = 30000)
    private UserService userService;


    @PostMapping("/save")
    public boolean save(@RequestBody User user, String smsCode){
        try{
            //检查用户输入的验证码
            boolean ok = userService.checkSmsCode(user.getPhone(),smsCode);

            if(ok) {
                userService.save(user);
                return true;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @GetMapping("/sendCode")
    public boolean sendCode(String phone){
        try{
            userService.sendCode(phone);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}

