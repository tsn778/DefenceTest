package com.test.demo.controller;


import com.test.demo.common.JsonResult;
import com.test.demo.entity.Permission;
import com.test.demo.entity.Role;
import com.test.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Controller
@RequestMapping("/demo/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    @PostMapping("/addPermission")
    public JsonResult AddRole(@RequestParam String permissionName){
        try {
            Permission permission = new Permission();
            permission.setPermission(permissionName);
            permissionService.save(permission);

            return JsonResult.buildSuccess("添加成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.buildFailure("添加成功",null);
        }

    }
}

