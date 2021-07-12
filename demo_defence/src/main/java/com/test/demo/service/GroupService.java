package com.test.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demo.entity.UserInfo;

import java.util.List;


/**
 * <p>
 * 分组 服务类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupService extends IService<Group> {
    IPage<Group> selectByPage(Integer type, int currentPage, int pageSize);
    JsonResult RandomSort(Integer groupId);
    IPage<Group> selectGroupList(Integer id, int currentPage, int pageSize);
    Group selectGroupOne(Integer id);
    String selectGroupGrade(Integer userId);
    List<Group> selectMyGroup(Integer userId);
    List<Group> selectStudentGroup(Integer userId);
}
