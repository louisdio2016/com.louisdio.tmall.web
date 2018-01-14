package com.how2java.tmall.shiro;

import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.service.AdminUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class AdminUserRealm extends AuthorizingRealm {
    @Autowired
    private AdminUserService adminUserService;
    //自动注入父类的credentialsMatcher属性，applicationContext.xml中增加<context:component-scan base-package="com.how2java.tmall.shiro"/>
    @Autowired
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher){
        super.setCredentialsMatcher(credentialsMatcher);
    }

    /**
     * 用于权限验证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        String username = principalCollection.getPrimaryPrincipal().toString();
        AdminUser user = (AdminUser)principalCollection.getPrimaryPrincipal();
        Set<String> roles = adminUserService.findRoles(user.getName());
        Set<String> permissions = adminUserService.findPermissions(user.getName());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 用于登录验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        AdminUser adminUser = adminUserService.findByName(username);
//        AdminUser adminUser = adminUserService.get(11);
        if (adminUser != null){
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(adminUser, adminUser.getPassword(),getName());
            return authenticationInfo;
        }else {
            return null;
        }
    }
}
