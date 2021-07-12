package com.test.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Group;
import com.test.demo.entity.GroupSort;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demo.entity.UserInfo;
import com.test.demo.vo.DefenceInfo;

import java.util.List;

/**
 * <p>
 * 学生排序 服务类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupSortService extends IService<GroupSort> {
    List<GroupSort> selectSort(Integer groupId);
    List<GroupSort> ResetSortList(Integer groupId);
    List<GroupSort> RandomSort(Integer groupId);
    JsonResult Order(Integer time,Integer groupId,List<UserInfo> objects);
    List<UserInfo> selectSortByTime(Integer groupId,Integer timePeriod);
    DefenceInfo getDefenceNow(Integer groupId);
    DefenceInfo getMyDefence(Integer groupId,Integer userId,DefenceInfo defenceInfo);
    GroupSort getMySort(Integer groupId,Integer userId);

}
