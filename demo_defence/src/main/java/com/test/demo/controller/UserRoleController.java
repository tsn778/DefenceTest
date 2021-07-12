package com.test.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.UserRole;
import com.test.demo.service.RoleService;
import com.test.demo.service.UserRoleService;
import com.test.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/demo/userRole")
public class UserRoleController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    @Autowired
    public UserRoleController(RoleService roleService,UserService userService,UserRoleService userRoleService){
        this.roleService = roleService;
        this.userService=userService;
        this.userRoleService=userRoleService;
    }

    @PutMapping("/adduserrole")
    public JsonResult AddUserRole(@RequestParam Integer roleId, @RequestParam Integer userId ){
        UserRole userRole=new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        UserRole one = userRoleService.getOne(wrapper);
        if(one==null)
        {
            userRoleService.save(userRole);

        }
        else
            userRoleService.update(userRole,wrapper);
        return JsonResult.buildSuccess("成功","1");


    }
}

