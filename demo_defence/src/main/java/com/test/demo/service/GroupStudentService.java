package com.test.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Group;
import com.test.demo.entity.GroupStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demo.entity.User;
import com.test.demo.entity.UserInfo;

import java.util.List;

/**
 * <p>
 * 学生具体分组情况 服务类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupStudentService extends IService<GroupStudent> {
    IPage<User> selectStuInG(Integer groupId, int currentPage, int pageSize);
    IPage<User> selectNoChoose(int currentPage, int pageSize);
    List<UserInfo> selectStuInGroup(Integer groupId);
    JsonResult addStuToGroup(Integer groupId,List<UserInfo> users);
    JsonResult deleteStuToGroup(Integer groupId,List<UserInfo> users);
    JsonResult distributeTeacher(Integer teacherId,List<UserInfo> users);
    JsonResult distributeTime(Integer time,List<UserInfo> users);
}
