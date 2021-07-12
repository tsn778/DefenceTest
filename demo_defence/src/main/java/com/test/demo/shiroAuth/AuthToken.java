package com.test.demo.shiroAuth;

import org.apache.shiro.authc.UsernamePasswordToken;

/*
 * ndky_LiJK
 *
 * 2021/3/31 20:18
 * shiro 自定义token类
 */
public class AuthToken extends UsernamePasswordToken {
    private final String token;

    public AuthToken(String token){
        this.token = token;
    }


    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
