package com.how2java.tmall.shiro;

import com.how2java.tmall.util.SHAUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * 自定义matcher，采用SHA256加密
 */
@Component("credentialsMatcher")
public class SHA256CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String pwd = SHAUtil.sha(new String(upToken.getPassword()), upToken.getUsername());
        Object dbpwd = info.getCredentials();
        return super.equals(pwd,dbpwd);
    }
}
