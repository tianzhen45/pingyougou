package com.tianzhen.manager.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.User;
import com.tianzhen.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;



public class AuthRealm extends AuthorizingRealm{
    @Reference
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
        //得到用户名和密码
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        //根据用户名得到数据库用户对象
        User user = userService.findByUsername(username);
        if(user != null){
            //第一个参数：安全数据（user对象）
            //第二个参数：密码（数据库密码）
            //第三个参数：当前调用realm域的名称（类名即可）
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return info;
        }

        return null;
    }
}
