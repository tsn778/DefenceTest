package com.test.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.demo.common.Constance;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.*;
import com.test.demo.mapper.SysTokenMapper;
import com.test.demo.mapper.UserInfoMapper;
import com.test.demo.mapper.UserMapper;
import com.test.demo.mapper.UserRoleMapper;
import com.test.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.demo.shiroAuth.TokenGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final SysTokenMapper sysTokenMapper;
    private final UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    public UserServiceImpl(UserMapper userMapper,SysTokenMapper sysTokenMapper,UserRoleMapper userRoleMapper,UserInfoMapper userInfoMapper){
        this.userMapper=userMapper;
        this.sysTokenMapper=sysTokenMapper;
        this.userRoleMapper=userRoleMapper;
        this.userInfoMapper=userInfoMapper;
    }
    @Override
    public JsonResult userList(String token) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if(userRoleMapper.selectById(user.getUserId()).getRoleId()==1)
        {


        }
//        try{
//            QueryWrapper<SysToken> tokenQueryWrapper = new QueryWrapper<>();
//            tokenQueryWrapper.eq("token",token);
//            SysToken byToken=  sysTokenMapper.selectOne(tokenQueryWrapper);
//            userMapper.selectById(byToken.getUserId());
//            if(byToken == null){
//                return JsonResult.build(Constance.TOKEN_NULL,"没有登录凭证请重新登录", null);
//            }
//            //生成一个token覆盖掉原来的（前端的也会清空）
//            token = TokenGenerator.generateValue();
//            //修改token
//            byToken.setToken(token);
//            //使前端获取到的token失效 8888888
//            sysTokenMapper.updateById(byToken);
//
//            return JsonResult.buildSuccess("退出成功",null);
//        }catch (Exception e){
//            e.printStackTrace();
            return JsonResult.buildFailure("退出失败",null);


    }
    public IPage<User> selectByPage(Integer type,int currentPage, int pageSize) {
        //查询对象
        //QueryWrapper<CartEntity> wrapper=new QueryWrapper<>();
        //分页，第一个参数是第几页，第二个是每页多少条数据
        Page<User> page=new Page<>(currentPage,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
       //1为教师，2为学生
        if(type==null){
            IPage<User> userIpage3 =userMapper.selectPage(page,null);

            return userIpage3;
        }
        if(type==1){

            queryWrapper.eq("state",1);
            IPage<User> userIpage1 =userMapper.selectPage(page,queryWrapper);
            return userIpage1;
        //
        }
        if(type==2) {
            queryWrapper.eq("state", 2);
            IPage<User> userIpage2 =userMapper.selectPage(page,queryWrapper);
            return userIpage2;
        }
        return null;
    }
    //搜寻关键字人员
    @Override
    public List<User> selectSomeone(Integer type,String info) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //如果type为空，全查；1，查找教师；2查找学生
        if(type==null)
            queryWrapper.like("account", info).or().like("user_name", info);
        else if(type==1) {
            queryWrapper.eq("state",1).and(i->i.like("account", info).or().like("user_name", info));

        }else{
            queryWrapper.eq("state",2).and(i->i.like("account", info).or().like("user_name", info));
        }
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    @Override
    public List<User> selectTeacher() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<User> selectTchList(Integer instituteId,int currentPage, int pageSize) {
        Page<User> page=new Page<>(currentPage,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(instituteId==0)
            queryWrapper.eq("state",1);
        else
        queryWrapper.eq("state", 1).and(i->i.eq("institute", instituteId));

        IPage<User> teacherList =userMapper.selectPage(page,queryWrapper);
        return teacherList;
    }

    @Override
    public JsonResult addStudent(JSONObject object) {
        User user = new User();

        UserInfo userInfo = JSON.toJavaObject(object, UserInfo.class);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("account",userInfo.getAccount());
        if(userMapper.selectOne(userQueryWrapper)==null) {
            user.setAccount(userInfo.getAccount());
            user.setPassword("111111");
            user.setUserName(userInfo.getUserName());
            user.setInstitute(userInfo.getInstituteId());
            user.setState(2);
            userMapper.insert(user);
            User user1 = userMapper.selectOne(userQueryWrapper);
            //添加角色
            UserRole userRole = new UserRole();
            userRole.setUserId(user1.getUserId());
            userRole.setRoleId(5);
            userRoleMapper.insert(userRole);
            userInfo.setUserId(user1.getUserId());
            userInfo.setState(0);//设置为未选择状态
            userInfo.setGradeYear(userInfo.getGradeYear().substring(0,4));
            userInfoMapper.insert(userInfo);
            return JsonResult.buildSuccess("添加成功",null);

        }
        else{
            return JsonResult.buildFailure("学号已存在",null);
        }


//        UserInfo userInfo1 = userInfoMapper.selectById(userInfo.getId());
//        userInfo.setState(userInfo1.getState());

    }

    @Override
    public JsonResult updateStudent(JSONObject object) {


        UserInfo userInfo = JSON.toJavaObject(object, UserInfo.class);
        UserInfo userInfo1 = userInfoMapper.selectById(userInfo.getId());
        userInfo.setState(userInfo1.getState());//设置为未选择状态
        userInfo.setGradeYear(userInfo.getGradeYear().substring(0,4));
        userInfoMapper.updateById(userInfo);
        User user1 = userMapper.selectById(userInfo.getUserId());
        user1.setAccount(userInfo.getAccount());

            user1.setUserName(userInfo.getUserName());
            user1.setInstitute(userInfo.getInstituteId());

            userMapper.updateById(user1);


            return JsonResult.buildSuccess("修改成功",null);


    }

    @Override
    public JsonResult addTeacher(JSONObject object) {
        User user = JSON.toJavaObject(object, User.class);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("account",user.getAccount());
        if(userMapper.selectOne(userQueryWrapper)==null) {
        user.setPassword("123456");
        user.setState(1);
        userMapper.insert(user);
            User user1 = userMapper.selectOne(userQueryWrapper);
            UserRole userRole = new UserRole();
            userRole.setUserId(user1.getUserId());
            userRole.setRoleId(4);
            userRoleMapper.insert(userRole);
            return JsonResult.buildSuccess("添加成功",null);}
        else {
            return JsonResult.buildFailure("教工号已存在",null);
        }
    }

    @Override
    public JsonResult updateTeacher(JSONObject object) {
        User user = JSON.toJavaObject(object, User.class);
        User user1 = userMapper.selectById(user.getUserId());
        user.setPassword(user1.getPassword());
        user.setState(user1.getState());
        return JsonResult.buildSuccess("修改成功",null);
    }

    @Override
    public JsonResult deleteStudent(JSONObject object) {
        List<User> groupTeachers=JSONObject.parseArray(object.toJSONString(), User.class);
        return null;
    }

    @Override
    public JsonResult deleteTeacher(JSONObject object) {
        return null;
    }
}
