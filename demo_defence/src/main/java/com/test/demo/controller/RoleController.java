package com.test.demo.controller;


import com.test.demo.common.JsonResult;
import com.test.demo.entity.Role;
import com.test.demo.entity.UserRole;
import com.test.demo.service.RoleService;
import com.test.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/demo/role")
public class RoleController {
     @Autowired
     private RoleService roleService;
     @PostMapping("/addRole")
     public JsonResult AddRole(@RequestParam String roleName){
         try {
             Role role = new Role();
             role.setRoleName(roleName);
             roleService.save(role);
             return JsonResult.buildSuccess("添加成功",null);
         }catch (Exception e){
             e.printStackTrace();
             return JsonResult.buildFailure("添加成功",null);
         }

     }



}

