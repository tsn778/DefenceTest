package com.test.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.test.demo.common.Constance;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Permission;
import com.test.demo.entity.Role;
import com.test.demo.entity.SysToken;
import com.test.demo.entity.User;
import com.test.demo.mapper.PermissionMapper;
import com.test.demo.mapper.RoleMapper;
import com.test.demo.mapper.SysTokenMapper;
import com.test.demo.mapper.UserMapper;
import com.test.demo.service.ShiroService;
import com.test.demo.shiroAuth.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author tsn77
 * @Date 2021/4/14 17:21
 * @Version 1.0
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    private final UserMapper userMapper;
    private final SysTokenMapper sysTokenMapper;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    @Autowired(required = false)
    public ShiroServiceImpl(UserMapper userMapper,
                            SysTokenMapper sysTokenMapper,
                            RoleMapper roleMapper,
                            PermissionMapper permissionMapper){
        this.userMapper = userMapper;
        this.sysTokenMapper = sysTokenMapper;
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public User userLogins(String account, String password) {
        System.out.println(account+"  ,"+password);
        //这里的userId表示登录凭证这是 email
        String userId=account;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("account",userId);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            return null;
        }
        System.out.println(userId+"通过数据验证");
        return user;
    }

    @Override
    public User userLogin(JSONObject jsonParam) {
        System.out.println(jsonParam);
        //这里的userId表示登录凭证这是 email
        String userId = jsonParam.get("account").toString();
        String password = jsonParam.get("password").toString();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("account",userId);
        userQueryWrapper.eq("password",password);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            return null;
        }

        System.out.println(userId+"通过数据验证");
        return user;
    }

    @Override
    public User findByAccount(String account) {
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", account);

            return userMapper.selectOne(queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> createToken(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(Constance.TOKEN_EXPIRE);
        //判断是否生成过token
        SysToken tokenEntity = sysTokenMapper.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            sysTokenMapper.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            sysTokenMapper.updateById(tokenEntity);
        }
        result.put("token", token);
        result.put("expire", expireTime);
        User user = findByUserId(userId);
        result.put("user",user);

        return result;


    }

    @Override
    public JsonResult logout(String token) {
        try{
            SysToken byToken = findByToken(token);
            if(byToken == null){
                return JsonResult.build(Constance.TOKEN_NULL,"没有登录凭证请重新登录", null);
            }
            //生成一个token覆盖掉原来的（前端的也会清空）
            token = TokenGenerator.generateValue();
            //修改token
            byToken.setToken(token);
            //使前端获取到的token失效 8888888
            sysTokenMapper.updateById(byToken);

            return JsonResult.buildSuccess("退出成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.buildFailure("退出失败",null);
        }


    }

    @Override
    public SysToken findByToken(String accessToken) {
        QueryWrapper<SysToken> tokenQueryWrapper = new QueryWrapper<>();
        tokenQueryWrapper.eq("token",accessToken);
        return sysTokenMapper.selectOne(tokenQueryWrapper);
    }



    @Override
    public User findByUserId(Integer userId) {
        User user = userMapper.selectById(userId);
        Set<Role> roleSet = roleMapper.findRolesByUserId(userId);
        roleSet.forEach(item ->{
            Set<Permission> permissionSet = permissionMapper.findPersByRoleId(item.getRoleId());
            item.setPermissions(permissionSet);
        });
        user.setRoles(roleSet);
        //返回及用户权限一身的用户
        return user;
    }
}
