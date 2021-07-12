package com.test.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Institute;
import com.test.demo.entity.SysToken;
import com.test.demo.entity.User;
import com.test.demo.entity.UserInfo;
import com.test.demo.service.InstituteService;
import com.test.demo.service.ShiroService;
import com.test.demo.service.UserInfoService;
import com.test.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/demo/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShiroService shiroService;
    @GetMapping("/hello")
    public String HelloWorld(){
        return "hello";
    }
    @GetMapping("/user")
    public User MyUser(){

        return userService.getById(1);
    }

    @RequiresPermissions({"save"}) //没有的话 AuthorizationException from MyExceptionHandler
    @PostMapping("/save")
    public JsonResult save(@RequestHeader("token")String token) {
        System.out.println("save" + token);
        return JsonResult.buildSuccess("当前用户有2313save权限",null);
    }

    @RequiresPermissions({"delete"}) //没有的话 AuthorizationException
    @DeleteMapping("/delete")
    public JsonResult delete(@RequestHeader("token")String token) {
        System.out.println("delete" + token);
        return JsonResult.buildSuccess("当前用户有delete的权力", null);
    }

    @RequiresPermissions({"update"}) //没有的话 AuthorizationException
    @PutMapping("update")
    public JsonResult update(@RequestHeader("token")String token) {
        System.out.println("update" + token);
        return JsonResult.buildSuccess("当前用户有update的权力", null);
    }

    @RequiresPermissions({"select"}) //没有的话 AuthorizationException
    @GetMapping("select")
    public JsonResult select(@RequestHeader("token")String token) {
        System.out.println("select" + token);
        return JsonResult.buildSuccess("当前用户有select的权力", null);
    }

    @RequiresRoles({"VIP"}) //没有的话 AuthorizationException
    @PostMapping("/vip")
    public JsonResult vip(@RequestHeader("token")String token) {
        System.out.println("vip" + token);
        return JsonResult.buildSuccess("你有vip角色", null);
    }
    @RequiresRoles({"SVIP"}) //没有的话 AuthorizationException
    @PostMapping("/svip")
    public JsonResult svip(@RequestHeader("token")String token) {
        System.out.println("svip" + token);
        return JsonResult.buildSuccess("你有svip角色", null);
    }
    @RequiresRoles({"P"}) //没有的话 AuthorizationException
    @GetMapping("/p")
    public JsonResult p(@RequestHeader("token")String token) {
        System.out.println("IsPRole" + token);
        return JsonResult.buildSuccess("你有p角色", null);
    }
    @PostMapping("/userLists")
    public JsonResult userList(@RequestHeader("token")String token){
        userService.list();
        return userService.userList("1");
    }
    @ApiOperation(value = "学生列表", notes = "参数:无")
    @RequestMapping("/userList/{page}/{size}")
    public IPage<UserInfo> getPage(@RequestParam(value ="gradeYear",required = false)String gradeYear,@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {
        System.out.println(gradeYear);
        SysToken myToken = shiroService.findByToken(token);
        User myInfo = userService.getById(myToken.getUserId());
        if(SecurityUtils.getSubject().hasRole("sysadmin"))
        {
                    IPage<UserInfo> userInfoIPage=userInfoService.selectInInstitute(null,null,page,size);
                    return userInfoIPage;
        }
        else
        {
            IPage<UserInfo> userInfoIPage=userInfoService.selectInInstitute(myInfo.getInstitute(),gradeYear,page,size);
            return userInfoIPage;
        }

    }
    @ApiOperation(value = "教师列表", notes = "参数:无")
    @RequestMapping("/tchList/{page}/{size}")
    public IPage<User> getTeacherList(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {

        SysToken myToken = shiroService.findByToken(token);
        User myInfo = userService.getById(myToken.getUserId());
        if(SecurityUtils.getSubject().hasRole("sysadmin"))
        {
            IPage<User> userIPage = userService.selectTchList(0, page, size);

            return userIPage;
        }
        else
        {
            IPage<User> userIPage = userService.selectTchList(myInfo.getInstitute(), page, size);
            return userIPage;
        }

    }
    @GetMapping("/teacherList/{page}/{size}")
    public IPage<User> getTeacherPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {
        IPage<User> userPage = userService.selectByPage(1,page, size);

        return userPage;
    }
    @GetMapping("/studentList/{page}/{size}")
    public IPage<User> getStudentPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {
        IPage<User> userPage = userService.selectByPage(2,page, size);

        return userPage;
    }
    @ApiOperation(value = "查找", notes = "参数:类型，关键字")
    @GetMapping("/selectSome")
    public JsonResult selectSomeone(@RequestParam(value ="info") String info,@RequestHeader("token")String token){
        System.out.println(info);
         List<User> users=userService.selectSomeone(1,info);
         return JsonResult.buildSuccess("查找成功",users);
    }
    @ApiOperation(value = "查找老师", notes = "参数:无")
    @GetMapping("/selectTeacher")
    public JsonResult selectTeacher(@RequestHeader("token")String token){

        List<User> users=userService.selectTeacher();
        return JsonResult.buildSuccess("查找成功",users);
    }
    @Autowired
    private UserInfoService userInfoService;
    @GetMapping("/InstituteStudentList/{page}/{size}")
    public IPage<UserInfo> getInInstitute(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {
        IPage<UserInfo> userPage = userInfoService.selectInInstitute(0,null,page,size);

        return userPage;
    }
    @Autowired
    private InstituteService instituteService;
    @GetMapping("/instituteList")
    public List<Institute> getInstituteList(){
        return instituteService.list();
    }

    @PostMapping("/addStu")
    public JsonResult addStu(@RequestBody JSONObject object, @RequestHeader("token")String token){


        return userService.addStudent(object);
    }
    @PutMapping("/updateStu")
    public JsonResult updateStu(@RequestBody JSONObject object, @RequestHeader("token")String token){


        return userService.updateStudent(object);
    }
    @PostMapping("/addTchInfo")
    public JsonResult addTch(@RequestBody JSONObject object, @RequestHeader("token")String token){


        return userService.addTeacher(object);
    }
    @PutMapping("/updateTch")
    public JsonResult updateTch(@RequestBody JSONObject object, @RequestHeader("token")String token){


        return userService.updateTeacher(object);
    }

}

