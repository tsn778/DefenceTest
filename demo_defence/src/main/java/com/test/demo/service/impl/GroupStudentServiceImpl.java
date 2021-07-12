package com.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.GroupSort;
import com.test.demo.entity.GroupStudent;
import com.test.demo.entity.User;
import com.test.demo.entity.UserInfo;
import com.test.demo.mapper.GroupSortMapper;
import com.test.demo.mapper.GroupStudentMapper;
import com.test.demo.mapper.UserInfoMapper;
import com.test.demo.mapper.UserMapper;
import com.test.demo.service.GroupStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生具体分组情况 服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class GroupStudentServiceImpl extends ServiceImpl<GroupStudentMapper, GroupStudent> implements GroupStudentService {
    @Autowired(required = false)
    private GroupStudentMapper groupStudentMapper;
    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    private GroupSortMapper groupSortMapper;
    @Override
    public IPage<User> selectStuInG(Integer groupId, int currentPage, int pageSize) {
        Page<User> page = new Page<>(currentPage,pageSize);
        IPage<User> result = groupStudentMapper.selectStuInG(page,groupId);
        return result;
    }

    @Override
    public IPage<User> selectNoChoose(int currentPage, int pageSize) {
        Page<User> page = new Page<>(currentPage,pageSize);
        IPage<User> result = groupStudentMapper.selectByNoGroup(page);
        return result;
    }

    @Override
    public List<UserInfo> selectStuInGroup(Integer groupId) {
        return userInfoMapper.selectStuInGroup(groupId);
    }

    @Override
    public JsonResult addStuToGroup(Integer groupId,List<UserInfo> users) {
        String msg="";
        int s=0;

        GroupStudent groupStudent = new GroupStudent();

        for (UserInfo userInfo:users){
            QueryWrapper<GroupStudent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("group_id",groupId).and(i->i.eq("user_id",userInfo.getUserId()));
            if(groupStudentMapper.selectCount(queryWrapper)==0){
                groupStudent.setGroupId(groupId);
                groupStudent.setUserId(userInfo.getUserId());
                groupStudentMapper.insert(groupStudent);
                UserInfo userInfo1 = userInfoMapper.selectById(userInfo.getId());
                userInfo1.setState(1);
                userInfoMapper.updateById(userInfo1);
                s++;
            }
            else{

                msg=msg+userInfo.getAccount()+"("+userInfo.getUserName()+")、";
            }
        }
        if(msg.equals("")&&s!=0)
            return JsonResult.buildSuccess("添加成功",null);
        if(s==0)
            return JsonResult.buildFailure(msg+"已存在组中，无需添加",null);
        else
            return JsonResult.buildSuccess(msg+"已存在组中，其余添加成功",null);
    }

    @Override
    public JsonResult deleteStuToGroup(Integer groupId, List<UserInfo> users) {
        GroupStudent groupStudent = new GroupStudent();
        for (UserInfo userInfo:users){
            QueryWrapper<GroupStudent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("group_id",groupId).and(i->i.eq("user_id",userInfo.getUserId()));
            groupStudentMapper.delete(queryWrapper);
            QueryWrapper<GroupStudent> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_id",userInfo.getUserId());
            if(groupStudentMapper.selectCount(queryWrapper1)==0)
            {
                UserInfo userInfo1 = userInfoMapper.selectById(userInfo.getId());
                userInfo1.setState(0);
                userInfoMapper.updateById(userInfo1);
            }
        }

            return JsonResult.buildSuccess("删除成功",null);
    }

    @Override
    public JsonResult distributeTeacher(Integer teacherId, List<UserInfo> users) {

        for (UserInfo userInfo:users){

            GroupStudent groupStudent = groupStudentMapper.selectById(userInfo.getGroupStudentId());
            groupStudent.setTeacherId(teacherId);
            groupStudentMapper.updateById(groupStudent);
        }
        return JsonResult.buildSuccess("分配成功！",null);
    }

    @Override
    public JsonResult distributeTime(Integer time, List<UserInfo> users) {

        for (UserInfo userInfo:users){
            QueryWrapper<GroupSort> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("group_student_id",userInfo.getGroupStudentId());
            GroupSort groupSort = groupSortMapper.selectOne(queryWrapper);
            if(groupSort==null){
                GroupSort groupSort1 = new GroupSort();
                groupSort1.setGroupStudentId(userInfo.getGroupStudentId());
                groupSort1.setUserId(userInfo.getUserId());
                groupSort1.setTimePeriod(time);
                groupSortMapper.insert(groupSort1);
            }
            else {
                groupSort.setTimePeriod(time);
                groupSortMapper.updateById(groupSort);
            }

        }
        return JsonResult.buildSuccess("分配成功！",null);
    }
}
