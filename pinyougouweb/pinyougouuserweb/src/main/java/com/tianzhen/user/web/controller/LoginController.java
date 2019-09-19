package com.tianzhen.user.web.controller;

import org.apache.shiro.SecurityUtils;
import org.pac4j.core.profile.Pac4JPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @GetMapping("/user/showName")
    public Map<String,String> showName(){
        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        Map<String,String> data = new HashMap<>();
        data.put("loginName",principal.getName());
        return data;
    }

}
