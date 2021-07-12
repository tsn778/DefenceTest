package com.test.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface UserService extends IService<User> {
    JsonResult userList(String token);
    IPage<User> selectByPage(Integer type,int currentPage, int pageSize);
    List<User> selectSomeone(Integer type,String info);
    List<User> selectTeacher();
    IPage<User> selectTchList(Integer instituteId,int currentPage, int pageSize);
    JsonResult addStudent(JSONObject object);
    JsonResult updateStudent(JSONObject object);
    JsonResult addTeacher(JSONObject object);
    JsonResult updateTeacher(JSONObject object);
    JsonResult deleteStudent(JSONObject object);
    JsonResult deleteTeacher(JSONObject object);
}
