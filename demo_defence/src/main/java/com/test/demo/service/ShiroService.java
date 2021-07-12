package com.test.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.SysToken;
import com.test.demo.entity.User;


import java.util.Map;

/**
 * @Author tsn77
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
public interface ShiroService {
    User findByAccount(String account);
    Map<String,Object> createToken(Integer userId);
    User userLogin(JSONObject jsonParam);
    User userLogins(String account,String password);
    JsonResult logout(String token);
    SysToken findByToken(String accessToken);
    User findByUserId(Integer userId);

}
