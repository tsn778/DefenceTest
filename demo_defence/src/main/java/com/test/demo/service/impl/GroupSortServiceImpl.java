package com.test.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.*;
import com.test.demo.mapper.GroupMapper;
import com.test.demo.mapper.GroupSortMapper;
import com.test.demo.mapper.UserInfoMapper;
import com.test.demo.service.GroupSortService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.demo.vo.DefenceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 学生排序 服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class GroupSortServiceImpl extends ServiceImpl<GroupSortMapper, GroupSort> implements GroupSortService {
    @Autowired(required = false)
    private GroupSortMapper groupSortMapper;
    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    private GroupMapper groupMapper;

    @Override
    public List<GroupSort> selectSort(Integer groupId) {

        return groupSortMapper.selectSort(groupId);
    }

    @Override
    public List<GroupSort> ResetSortList(Integer groupId) {

        return groupSortMapper.resetSort(groupId);
    }

    @Override
    public List<GroupSort> RandomSort(Integer groupId) {
        List<GroupSort> groupSorts = groupSortMapper.resetSort(groupId);
        for(GroupSort groupSort:groupSorts){

            groupSort.setTest((int) (Math.random()*(1000)));

        }
        Collections.sort(groupSorts, new Comparator<GroupSort>() {
            @Override
            public int compare(GroupSort o1, GroupSort o2) {
                return o1.getTest()- o2.getTest();
            }
        });
        return groupSorts;
    }

    @Override
    public JsonResult Order(Integer time,Integer groupId,List<UserInfo> objects){
        int a=1;
        Group group = groupMapper.selectById(groupId);
        String am=group.getGroupDefenceTime()+" 09:00:00";
        String pm=group.getGroupDefenceTime()+" 13:30:00";
        Calendar calendar= Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date date1= null;
        Date date2 =null;
        try {
            date1 = sdf.parse(am);
            date2=sdf.parse(pm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(time==1) {
            calendar.setTime(date1);
        }
        else
            calendar.setTime(date2);

        System.out.println(calendar.getTime());
        for(UserInfo userInfo:objects){

            QueryWrapper<GroupSort> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("group_student_id",userInfo.getGroupStudentId());
            GroupSort groupSort = groupSortMapper.selectOne(queryWrapper);
            groupSort.setSort(a);
            groupSort.setDefenceTime(sdf.format(calendar.getTime()));
            groupSort.setDefencePlace(group.getGroupDefencePlace());
            groupSortMapper.updateById(groupSort);
            a++;
            calendar.add(Calendar.MINUTE, 30);
        }
        return JsonResult.buildSuccess("成功",null);
    }

    @Override
    public List<UserInfo> selectSortByTime(Integer groupId, Integer timePeriod) {
        return userInfoMapper.selectStuSort(groupId,timePeriod);
    }

    @Override
    public DefenceInfo getDefenceNow(Integer groupId) {
        List<GroupSort> groupSorts = groupSortMapper.selectSortByGroup(groupId);
        int am=0;
        int pm=0;
        GroupSort myGroupSort = new GroupSort();
        for(GroupSort groupSort:groupSorts){
            if(groupSort.getState()==null||groupSort.getState()==0) {
                if (groupSort.getTimePeriod() == 1) {
                    am++;
                }
                if (groupSort.getTimePeriod() == 2) {
                    pm++;
                }
            }



        }
        myGroupSort=groupSorts.get(0);
        DefenceInfo defenceInfo = new DefenceInfo();

        defenceInfo.setAm(am);
        defenceInfo.setPm(pm);
        defenceInfo.setObject(myGroupSort);
        return defenceInfo;
    }

    @Override
    public DefenceInfo getMyDefence(Integer groupId, Integer userId,DefenceInfo defenceInfo) {
        GroupSort groupSort = groupSortMapper.selectSortById(groupId, userId);
        GroupSort now = (GroupSort) defenceInfo.getObject();

        System.out.println(groupSort);
        if(groupSort.getState()==null) {

                if (groupSort.getTimePeriod() == 2&&defenceInfo.getAm()!=0) {

                    defenceInfo.setFront(groupSort.getSort()+defenceInfo.getAm()-1);


                }
                else
                    defenceInfo.setFront(groupSort.getSort() - now.getSort());

        }
        else if(groupSort.getState()==2)
            defenceInfo.setNow(2);
        else
            defenceInfo.setNow(1);

        return defenceInfo;
    }

    @Override
    public GroupSort getMySort(Integer groupId, Integer userId) {
        return groupSortMapper.selectSortById(groupId,userId);
    }

}
