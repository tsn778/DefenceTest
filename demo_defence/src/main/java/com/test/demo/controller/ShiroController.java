package com.test.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.User;
import com.test.demo.service.RoleService;
import com.test.demo.service.ShiroService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author tsn77
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
@RestController
public class ShiroController {
    private final ShiroService shiroService;
    private final RoleService roleService;
    public ShiroController(ShiroService shiroService,RoleService roleService) {
        this.shiroService = shiroService;
        this.roleService = roleService;
    }


    /**
     * 登录
     */
    @ApiOperation(value = "登陆", notes = "参数:用户名 密码")
    @PostMapping("/sys/login")
    public JsonResult login(@RequestBody JSONObject jsonParam) {
        User user = shiroService.userLogin(jsonParam);
        if(user == null){
            return JsonResult.buildFailure("登录失败", null);
        }
        Integer userId = user.getUserId();
        //生成token，并保存到数据库
        Map<String, Object> result = shiroService.createToken(userId);
        if(result != null){
            return JsonResult.buildSuccess("登录成功Success",result);
        }

        return JsonResult.buildFailure("登录失败", null);
    }

    /**
     * 退出
     */
    @ApiOperation(value = "登出", notes = "参数:token")
    @PostMapping("/sys/logout")
    public JsonResult ShiroLoginOut(@RequestHeader("token")String token){
        return shiroService.logout(token);
    }
    @ApiOperation(value = "查找", notes = "参数:account")
    @PostMapping("/sys/find")
    public JsonResult FindAccount(@RequestParam String account) {

        return JsonResult.buildSuccess("查找成功",shiroService.findByAccount(account));
    }


}
