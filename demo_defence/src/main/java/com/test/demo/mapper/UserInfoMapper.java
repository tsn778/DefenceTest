package com.test.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.entity.User;
import com.test.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-28
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    IPage<UserInfo> selectUserInfo(IPage<UserInfo> page,@Param("instituteId")Integer instituteId,@Param("gradeYear")String gradeYear);
    UserInfo selectOneInfo(Integer userId);
    List<UserInfo> selectStuInGroup(Integer groupId);
    List<UserInfo> selectStuSort(Integer groupId,Integer timePeriod);


}
