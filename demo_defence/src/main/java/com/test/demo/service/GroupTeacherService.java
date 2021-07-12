package com.test.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.entity.Group;
import com.test.demo.entity.GroupTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demo.entity.User;
import com.test.demo.vo.TeacherInfo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 老师分组情况 服务类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupTeacherService extends IService<GroupTeacher> {
    IPage<User> selectByPage(Integer type, int currentPage, int pageSize);
    IPage<User> selectAllTeacher(Integer groupId,int currentPage, int pageSize);
    List<TeacherInfo> TchInGroup(Integer groupId);
}
