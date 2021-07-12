package com.test.demo.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.servlet.http.HttpServletRequest;

/*
 * ndky_LiJK
 *
 * 2021/3/31 20:25
 * token工具类
 */
public class TokenUtil {
    public static String getRequestToken(HttpServletRequest httpRequest) {

        //从header中获取token
        String token = httpRequest.getHeader("token");
        System.out.println("--token--is---"+token);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }


}
