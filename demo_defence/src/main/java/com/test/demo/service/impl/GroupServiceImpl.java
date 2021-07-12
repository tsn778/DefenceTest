package com.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.*;
import com.test.demo.mapper.*;
import com.test.demo.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分组 服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {
    private final UserMapper userMapper;
    private final GroupStudentMapper gsMapper;
    private final GroupSortMapper groupSortMapper;
    private final GroupMapper groupMapper;
    @Autowired(required = false)
    public GroupServiceImpl(UserMapper userMapper,
                            GroupStudentMapper gsMapper,
                            GroupSortMapper groupSortMapper,
                            GroupMapper groupMapper){
        this.userMapper = userMapper;
        this.gsMapper=gsMapper;
        this.groupSortMapper=groupSortMapper;
        this.groupMapper=groupMapper;
    }
    @Override
    public IPage<Group> selectByPage(Integer type, int currentPage, int pageSize) {
        Page<Group> page=new Page<>(currentPage,pageSize);
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        //1为教师，2为学生
        if(type==null) {
            IPage<Group> groupIPage = groupMapper.selectPage(page, null);
            return groupIPage;
        }
            return null;
    }

    @Override
    public JsonResult RandomSort(Integer groupId) {
        QueryWrapper<GroupStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId).and(i->i.eq("status",1));
        List<GroupStudent> groupStudents = gsMapper.selectList(queryWrapper);
        for(GroupStudent groupStudent:groupStudents){
            groupStudent.setTest((int) (Math.random()*(1000)));
            gsMapper.updateById(groupStudent);
        }
        QueryWrapper<GroupStudent> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("group_id",groupId).and(i->i.eq("status",1)).orderByAsc("test");
        List<GroupStudent> groupStudent1s = gsMapper.selectList(queryWrapper1);
        groupStudent1s.forEach(System.out::println);
        return null;
    }

    @Override
    public IPage<Group> selectGroupList(Integer id, int currentPage, int pageSize) {
        Page<Group> page = new Page<>(currentPage,pageSize);
        IPage<Group> result = groupMapper.selectGroupInfo(page,id);

        return result;
    }

    @Override
    public Group selectGroupOne(Integer id) {
        return groupMapper.selectGroupOne(id);
    }

    @Override
    public String selectGroupGrade(Integer userId) {
        QueryWrapper<GroupTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        return null;
    }

    @Override
    public List<Group> selectMyGroup(Integer userId) {
        List<Group> myGroups = groupMapper.selectMyGroup(userId);
        return myGroups;
    }

    @Override
    public List<Group> selectStudentGroup(Integer userId) {
        List<Group> myGroups = groupMapper.selectStudentGroup(userId);
        return myGroups;
    }
}
