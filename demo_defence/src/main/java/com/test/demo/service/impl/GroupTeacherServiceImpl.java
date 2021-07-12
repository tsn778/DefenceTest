package com.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.demo.entity.Group;
import com.test.demo.entity.GroupTeacher;
import com.test.demo.entity.User;
import com.test.demo.mapper.GroupTeacherMapper;
import com.test.demo.mapper.UserMapper;
import com.test.demo.service.GroupTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.demo.util.PageList;
import com.test.demo.vo.TeacherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 老师分组情况 服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class GroupTeacherServiceImpl extends ServiceImpl<GroupTeacherMapper, GroupTeacher> implements GroupTeacherService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private GroupTeacherMapper gtMapper;
    @Override
    public IPage<User> selectByPage(Integer groupId, int currentPage, int pageSize) {

        Page<User> page = new Page<>(currentPage,pageSize);
        IPage<User> result = gtMapper.selectTchInG(page,groupId);

        return result;
    }

    @Override
    public IPage<User> selectAllTeacher(Integer groupId, int currentPage, int pageSize) {

        Page<User> page = new Page<>(currentPage,pageSize);
        IPage<User> result = gtMapper.selectByNoGroup(page,groupId);

        return result;
    }

    @Override
    public List<TeacherInfo> TchInGroup(Integer groupId) {
        return gtMapper.TchInGroup(groupId);
    }
}
