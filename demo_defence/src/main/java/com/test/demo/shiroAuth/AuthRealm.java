package com.test.demo.shiroAuth;



import com.test.demo.entity.Permission;
import com.test.demo.entity.Role;
import com.test.demo.entity.SysToken;
import com.test.demo.entity.User;
import com.test.demo.service.ShiroService;
import com.test.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
 * ndky_LiJK
 *
 * 2021/3/31 20:47
 * Shiro自定义Realm
 */
@Component
public class AuthRealm extends AuthorizingRealm {

    private final UserService userService;
    private final ShiroService shiroService;
    @Autowired
    public AuthRealm(UserService userService,ShiroService shiroService){
        this.userService = userService;
        this.shiroService=shiroService;
    }

    /**
     * 授权 获取用户的角色和权限
     *@param  principalCollection
     *@return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("------开始添加权限");
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        //2.添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //2.1添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                //2.1.1添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        System.out.println("------结束添加权限");
        return simpleAuthorizationInfo;
    }

    /**
     * 认证 判断token的有效性
     *@param  token
     *@return org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("------判断token的有效性开始");
        //获取token，既前端传入的token
        String accessToken = (String) token.getPrincipal();
        //1. 根据accessToken，查询用户信息
        SysToken tokenEntity = shiroService.findByToken(accessToken);
        //2. token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new IncorrectCredentialsException("token失效(不存在)，请重新登录");
        }
        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        User user = shiroService.findByUserId(tokenEntity.getUserId());
        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在!");
        }
        //5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        System.out.println("------判断token的有效性结束");
        System.out.println(user);
        return new SimpleAuthenticationInfo(user, accessToken, this.getName());
    }
}
