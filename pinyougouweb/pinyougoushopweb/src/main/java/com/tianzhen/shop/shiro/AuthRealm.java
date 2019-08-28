package com.tianzhen.shop.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.pojo.Seller;
import com.tianzhen.pojo.User;
import com.tianzhen.service.SellerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class AuthRealm extends AuthorizingRealm{
    @Reference(timeout = 10000)
    private SellerService sellerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //身份认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
            //获取登录用户名
            String username = (String) authenticationToken.getPrincipal();

            //根据用户名查询商家对象
            Seller seller = sellerService.findByUsername(username);

            //如果存在商家，并且已审核，继续验证密码
            if (seller != null && "1".equals(seller.getStatus())) {
                return new SimpleAuthenticationInfo(username, seller.getPassword(), this.getName());
            }
        }catch (Exception e){
            throw  new AuthenticationException(e);
        }
        return null;
    }
}
