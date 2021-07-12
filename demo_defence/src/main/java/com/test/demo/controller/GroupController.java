package com.test.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.*;
import com.test.demo.service.*;
import com.test.demo.vo.DefenceInfo;
import com.test.demo.vo.TeacherInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分组 前端控制器
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/demo/group")
public class GroupController {
    private final ShiroService shiroService;
    private final RoleService roleService;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupTeacherService groupTeacherService;
    private final GroupSortService groupSortService;
    private final GroupStudentService groupStudentService;
    private final UserInfoService userInfoService;
    private final InstituteService instituteService;
    private final UserRoleService userRoleService;
    public GroupController(ShiroService shiroService,
                           RoleService roleService,
                           GroupService groupService,
                           GroupTeacherService groupTeacherService,
                           GroupSortService groupSortService,
                           GroupStudentService groupStudentService,
                           UserService userService,
                           UserInfoService userInfoService,
                           InstituteService instituteService,
                           UserRoleService userRoleService) {
        this.shiroService = shiroService;
        this.roleService = roleService;
        this.groupService=groupService;
        this.groupSortService=groupSortService;
        this.groupStudentService=groupStudentService;
        this.groupTeacherService=groupTeacherService;
        this.userService=userService;
        this.userInfoService=userInfoService;
        this.instituteService=instituteService;
        this.userRoleService=userRoleService;
    }
    @ApiOperation(value = "添加或修改组", notes = "参数:组 token")
    @PostMapping("/manageGroup")
    public JsonResult ManageGroup(@RequestBody JSONObject group,@RequestHeader("token")String token){
        System.out.println(group);
        SysToken myToken = shiroService.findByToken(token);
        User myInfo = userService.getById(myToken.getUserId());
        if(group==null){
            return JsonResult.buildFailure("操作失败",null);
        }
        else {
            Group group1= JSON.toJavaObject(group,Group.class);
            System.out.println(group1);
            group1.setInstituteId(myInfo.getInstitute());

            group1.setGradeYear(group1.getGradeYear().substring(0,4));
            group1.setGroupDefenceTime(group1.getGroupDefenceTime().substring(0,10));
            QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.eq("institute_id",myInfo.getInstitute());
            groupQueryWrapper.eq("grade_year",group1.getGradeYear());
            int count = groupService.count(groupQueryWrapper);
            int count1=count+1;
            groupQueryWrapper.eq("group_secretary_id",group1.getGroupSecretaryId());
            if(groupService.count(groupQueryWrapper)==0) {
                group1.setGroupName(group1.getGradeYear() + instituteService.getById(group1.getInstituteId()).getName() + "组" + count1);
                groupService.save(group1);
                QueryWrapper<UserRole> leaderQueryWrapper = new QueryWrapper<>();
                QueryWrapper<UserRole> secretaryQueryWrapper = new QueryWrapper<>();
                leaderQueryWrapper.eq("user_id", group1.getGroupLeaderId());
                leaderQueryWrapper.eq("role_id", 2);
                secretaryQueryWrapper.eq("user_id", group1.getGroupSecretaryId());
                secretaryQueryWrapper.eq("role_id", 3);
                if (userRoleService.count(leaderQueryWrapper) == 0) {
                    UserRole leader = new UserRole();

                    leader.setUserId(group1.getGroupLeaderId());
                    leader.setRoleId(2);
                    userRoleService.save(leader);
                }
                if (userRoleService.count(secretaryQueryWrapper) == 0) {

                    UserRole secretary = new UserRole();
                    secretary.setUserId(group1.getGroupSecretaryId());
                    secretary.setRoleId(3);
                    userRoleService.save(secretary);
                }

                Group one = groupService.getOne(groupQueryWrapper);
                GroupTeacher leaderAdd = new GroupTeacher();
                GroupTeacher secretaryAdd = new GroupTeacher();

                leaderAdd.setGroupId(one.getGroupId());
                leaderAdd.setGroupName(one.getGroupName());
                leaderAdd.setUserId(one.getGroupLeaderId());
                leaderAdd.setStatus(1);
                groupTeacherService.save(leaderAdd);
                secretaryAdd.setGroupId(one.getGroupId());
                secretaryAdd.setGroupName(one.getGroupName());
                secretaryAdd.setUserId(one.getGroupSecretaryId());
                secretaryAdd.setStatus(1);
                groupTeacherService.save(secretaryAdd);



            }
            else
            {
                return JsonResult.buildFailure("当前组已存在，请重新选择秘书",null);
            }



            return JsonResult.buildSuccess("操作成功",group1.getGroupName());

        }

    }
    @ApiOperation(value = "添加老师进组", notes = "参数:信息 token")
    @PostMapping("/addTeacher")
    public JsonResult AddTeacher(@RequestBody JSONObject info,@RequestHeader("token")String token){
        System.out.println(info);
        String groupName=info.getString("groupName");
        System.out.println(groupName);
        Integer groupId=info.getInteger("groupId");
        Integer status=info.getInteger("status");
        List<User> users=JSONObject.parseArray(info.getJSONArray("data").toJSONString(), User.class);
        if(users==null||
                groupId==null||
                groupName==null||status==null){
            return JsonResult.buildFailure("添加失败，数据为空!请重试",null);
        }
//        users.forEach(user-> System.out.println(user));
        int s=0;
        String msg="";
        for (User user : users) {
            System.out.println(user);
            GroupTeacher groupTeacher=new GroupTeacher();

            QueryWrapper<GroupTeacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("group_id",groupId).and(i->i.eq("user_id",user.getUserId()));
            if(groupTeacherService.count(queryWrapper)==0){
                groupTeacher.setGroupId(groupId);
                groupTeacher.setGroupName(groupName);
                groupTeacher.setUserId(user.getUserId());
                groupTeacher.setStatus(status);
                groupTeacherService.save(groupTeacher);

                s++;
            }
            else{

                msg=msg+user.getAccount()+"("+user.getUserName()+")、";
            }
        }
        if(msg.equals("")&&s!=0)
            return JsonResult.buildSuccess("添加成功",null);
        if(s==0)
            return JsonResult.buildFailure(msg+"已存在组中，无需添加",null);
        else
            return JsonResult.buildSuccess(msg+"已存在组中，其余添加成功",null);

    }
    @ApiOperation(value = "组内移除老师", notes = "参数:信息 token")
    @DeleteMapping ("/deleteTeacher")
    public JsonResult DeleteTeacher(@RequestBody JSONObject info,@RequestHeader("token")String token){
        List<TeacherInfo> groupTeachers=JSONObject.parseArray(info.getJSONArray("data").toJSONString(), TeacherInfo.class);
        System.out.println("删除");
        if(groupTeachers==null)
            return JsonResult.buildFailure("删除失败！",null);
        for(TeacherInfo teacherInfo:groupTeachers){
            System.out.println(teacherInfo);
            groupTeacherService.removeById(teacherInfo.getGroupTeacherId());
        }
        return JsonResult.buildSuccess("删除成功",null);
    }

    @GetMapping("/teacherList/{page}/{size}")
    public IPage<User> getPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestHeader("token")String token) {
        IPage<User> userPage = groupTeacherService.selectByPage(2,page, size);

        return userPage;
    }
    @GetMapping("/userList/{page}/{size}")
    public IPage<User> getAllTeacher(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {
        IPage<User> userPage = groupTeacherService.selectAllTeacher(2,page, size);

        return userPage;
    }
    @GetMapping("/stuInGList/{page}/{size}")
    public IPage<User> getStuInGList(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestHeader("token")String token) {
        IPage<User> userPage = groupStudentService.selectStuInG(2,page, size);

        return userPage;
    }
    @GetMapping("/studentList/{page}/{size}")
    public IPage<User> getAllStudent(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {
        IPage<User> userPage = groupStudentService.selectNoChoose(page, size);

        return userPage;
    }
    @ApiOperation(value = "得到排序", notes = "参数:信息 token")
    @PutMapping("/sortOrder")
    public JsonResult SortOrder(@RequestBody JSONObject info,@RequestHeader("token")String token){
        Integer groupId = info.getInteger("groupId");
        Integer time=info.getInteger("time");
        List<UserInfo> users=JSONObject.parseArray(info.getJSONArray("data").toJSONString(), UserInfo.class);

        return groupSortService.Order(time,groupId,users);


//        }
////        List<Object> objects=JSONObject.parseArray(info.toJSONString(), Object.class);
////        objects.forEach(System.out::println);
//
//
//        return JsonResult.buildSuccess("添加成功",null);
    }
    @GetMapping("/getSortList")
    public JsonResult GetSortList(@RequestParam(value ="groupId") Integer groupId ,@RequestHeader("token")String token){
        return JsonResult.buildSuccess("查找成功",groupSortService.selectSort(groupId));
    }
    @GetMapping("/resetSort")
    public JsonResult GetResetSort(@RequestParam(value ="groupId") Integer groupId ,@RequestHeader("token")String token){
        return JsonResult.buildSuccess("重置成功",groupSortService.ResetSortList(groupId));
    }
    @GetMapping("/randomSort")
    public JsonResult GetRandomSort(@RequestParam(value ="groupId") Integer groupId ,@RequestHeader("token")String token){
        return JsonResult.buildSuccess("查找成功",groupSortService.RandomSort(groupId));
    }
    @ApiOperation(value = "分组列表", notes = "参数:无")
    @GetMapping("/groupList/{page}/{size}")
    public IPage<Group> GetGroupList(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {

        SysToken myToken = shiroService.findByToken(token);
        User myInfo = userService.getById(myToken.getUserId());
        if(SecurityUtils.getSubject().hasRole("sysadmin"))
        {
            IPage<Group> groupIPage = groupService.selectGroupList(0, page, size);


            return groupIPage;
        }
        else
        {
            IPage<Group> groupIPage = groupService.selectGroupList(myInfo.getInstitute(), page, size);


            return groupIPage;
        }

    }
    @ApiOperation(value = "得到一个group", notes = "参数:groupId")
    @GetMapping("/getGroupOne")
    public JsonResult GetGroupOne(@RequestParam(value = "groupId",required=false)Integer groupId, @RequestHeader("token")String token) {

        SysToken myToken = shiroService.findByToken(token);
        User myInfo = userService.getById(myToken.getUserId());
        int a=0;
        if (groupId==null)
        {
            return JsonResult.buildFailure("查询失败",null);
        }
        else {
//            if(!SecurityUtils.getSubject().hasRole("sysadmin")&&!SecurityUtils.getSubject().hasRole("admin")) {
//                List<Group> myGroups = groupService.selectMyGroup(myInfo.getUserId());
//                for(Group group:myGroups){
//                    if(groupId==group.getGroupId())
//                        a=1;
//                }
//                if(a==0) {
//
//                    return JsonResult.buildFailure("查询失败", null);
//                }
//            }

                Group group = groupService.selectGroupOne(groupId);
                return JsonResult.buildSuccess("查询成功",group);



        }

    }
    @ApiOperation(value = "得到一个group", notes = "参数:groupId")
    @GetMapping("/tchInGroup")
    public JsonResult GetTchInGroup(@RequestParam(value = "groupId",required=false)Integer groupId, @RequestHeader("token")String token) {

        if (groupId==null)
        {
            return JsonResult.buildFailure("查询失败",null);
        }
        else {

                List<TeacherInfo> users = groupTeacherService.TchInGroup(groupId);




                return JsonResult.buildSuccess("查询成功",users);

        }
    }
    @ApiOperation(value = "得到自己组", notes = "参数:groupId")
    @GetMapping("/myGroup")
    public List<Group> GetMyGroup(@RequestParam(value = "teacherId",required=false)Integer teacherId, @RequestHeader("token")String token) {
        SysToken myToken = shiroService.findByToken(token);
        User myInfo = userService.getById(myToken.getUserId());
        if(teacherId==null)
        {
            List<Group> myGroups=groupService.selectMyGroup(myInfo.getUserId());
            return myGroups;

        }
        else{
            List<Group> myGroups=groupService.selectMyGroup(teacherId);
            return myGroups;
        }


    }
    @ApiOperation(value = "得到组内学生", notes = "参数:groupId")
    @GetMapping("/stuInGroup")
    public JsonResult GetStuInGroup(@RequestParam(value = "groupId")Integer groupId, @RequestHeader("token")String token) {
        if (groupId==null)
        {
            return JsonResult.buildFailure("查询失败",null);
        }
        else {

            List<UserInfo> users = groupStudentService.selectStuInGroup(groupId);




            return JsonResult.buildSuccess("查询成功",users);

        }

    }
    @ApiOperation(value = "得到全部学生", notes = "参数:groupId")
    @GetMapping("/getAllStu/{page}/{size}")
    public IPage<UserInfo> GetAllStudent(@RequestParam(value = "groupId")Integer groupId,@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {

        Group group = groupService.selectGroupOne(groupId);
        IPage<UserInfo> userInfoIPage=userInfoService.selectInInstitute(group.getInstituteId(),group.getGradeYear(),page,size);
        return userInfoIPage;

    }
    @ApiOperation(value = "添加全部学生", notes = "参数:groupId")
    @PostMapping ("/addStudent")
    public  JsonResult AddStudentToGroup( @RequestBody JSONObject info,@RequestHeader("token")String token){
        System.out.println(info);
        if(info==null)
            return JsonResult.buildFailure("数据异常，请重新添加",null);
        Integer groupId=info.getInteger("groupId");
        List<UserInfo> users=JSONObject.parseArray(info.getJSONArray("data").toJSONString(), UserInfo.class);
      return   groupStudentService.addStuToGroup(groupId,users);

    }
    @ApiOperation(value = "添加全部学生", notes = "参数:groupId")
    @DeleteMapping ("/deleteStudent/{groupId}")
    public  JsonResult deleteStudentToGroup(@PathVariable("groupId")Integer groupId, @RequestBody JSONObject info, @RequestHeader("token")String token){
        System.out.println(info);
        if(info==null)
            return JsonResult.buildFailure("数据异常，请重新删除",null);
//        Integer groupId=info.getInteger("groupId");
        List<UserInfo> users=JSONObject.parseArray(info.getJSONArray("data").toJSONString(), UserInfo.class);




        return   groupStudentService.deleteStuToGroup(groupId,users);

    }
    @ApiOperation(value = "得到全部老师", notes = "参数:groupId")
    @GetMapping("/getAllTch/{page}/{size}")
    public IPage<User> GetAllTeacher(@RequestParam(value = "groupId")Integer groupId,@PathVariable("page") Integer page, @PathVariable("size") Integer size,@RequestHeader("token")String token) {

        Group group = groupService.selectGroupOne(groupId);
//        IPage<UserInfo> userInfoIPage=userInfoService.selectInInstitute(group.getInstituteId(),group.getGradeYear(),page,size);
        IPage<User> userIPage = userService.selectTchList(group.getInstituteId(), page, size);
        return userIPage;

    }
    @ApiOperation(value = "分配老师", notes = "参数:教师id,学生列表")
    @PutMapping("/distribute")
    public JsonResult  DistributeTeacher(@RequestBody JSONObject info,@RequestHeader("token")String token){

        Integer teacherId = info.getInteger("teacherId");
        List<UserInfo> users=JSONObject.parseArray(info.getJSONArray("data").toJSONString(), UserInfo.class);
        return groupStudentService.distributeTeacher(teacherId,users);
    }
    @ApiOperation(value = "分配时间段", notes = "参数:时间,学生列表")
    @PostMapping("/distributeTime")
    public JsonResult  DistributeTime(@RequestBody JSONObject info,@RequestHeader("token")String token){

        Integer time = info.getInteger("time");
        List<UserInfo> users=JSONObject.parseArray(info.getJSONArray("data").toJSONString(), UserInfo.class);
        return groupStudentService.distributeTime(time,users);
    }
    @ApiOperation(value = "得到排序列表", notes = "参数:组id,时间段")
    @GetMapping("/stuSortByTime")
    public List<UserInfo> StuSortByTime(@RequestParam(value = "groupId")Integer groupId,@RequestParam(value = "timePeriod")Integer timePeriod, @RequestHeader("token")String token){

        return groupSortService.selectSortByTime(groupId,timePeriod);
    }
    @RequiresRoles("student")
    @ApiOperation(value = "得到排序列表", notes = "参数:组id,时间段")
    @GetMapping("/studentGroup")
    public JsonResult StudentGroup(@RequestHeader("token")String token){
        SysToken myToken = shiroService.findByToken(token);
        User myInfo = userService.getById(myToken.getUserId());
        List<Group> groups = groupService.selectStudentGroup(myInfo.getUserId());

        if(groups.isEmpty()){
            return JsonResult.buildFailure("未分配组，请联系你的指导老师进行确认",null);
        }
        return JsonResult.buildSuccess("成功",groups);
    }
    @RequiresPermissions("sort")
    @ApiOperation(value = "答辩状态改变", notes = "参数:组id,时间段")
    @PutMapping("/chanceDefence")
    public JsonResult ChanceDefence(@RequestBody JSONObject object,@RequestHeader("token")String token){
        Integer groupId = object.getInteger("groupId");
        Integer state = object.getInteger("state");
        Group byId = groupService.getById(groupId);
        if(byId.getAction()!=null&&byId.getAction().equals(state))
        {
            return JsonResult.buildFailure("您已是此状态，无需修改",null);
        }
        else {
            byId.setAction(state);
            groupService.updateById(byId);
        }
        return JsonResult.buildSuccess("修改成功",byId);
    }
    @RequiresPermissions("sort")
    @ApiOperation(value = "答辩状态改变", notes = "参数:组id,时间段")
    @PutMapping("/setStuState")
    public JsonResult SetStuState(@RequestBody JSONObject object,@RequestHeader("token")String token){
        Integer groupStudentId = object.getInteger("groupStudentId");
        Integer groupId = object.getInteger("groupId");
        Integer state = object.getInteger("state");
        GroupStudent byId = groupStudentService.getById(groupStudentId);
        byId.setState(state);
        groupStudentService.updateById(byId);
        DefenceInfo defenceNow = groupSortService.getDefenceNow(groupId);


        return JsonResult.buildSuccess("修改成功",defenceNow);
    }
    @ApiOperation(value = "刷新答辩状态", notes = "参数:组id,时间段")
    @GetMapping("/refreshDefence")
    public JsonResult RefreshDefence(@RequestParam(value = "userId",required = false)Integer userId,@RequestParam(value = "groupId")Integer groupId,@RequestHeader("token")String token){
        DefenceInfo defenceNow = groupSortService.getDefenceNow(groupId);
        if(userId!=null)
        {
            DefenceInfo myDefence = groupSortService.getMyDefence(groupId, userId, defenceNow);
            return JsonResult.buildSuccess("查询成功",myDefence);
        }

        return JsonResult.buildSuccess("查询成功",defenceNow);
    }
    @GetMapping("/getMySortInfo")
    public JsonResult MySortInfo(@RequestParam(value = "userId",required = false)Integer userId,@RequestParam(value = "groupId")Integer groupId,@RequestHeader("token")String token){
        GroupSort mySort = groupSortService.getMySort(groupId, userId);
        return JsonResult.buildSuccess("我的",mySort);
    }
}

