package com.test.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.entity.GroupTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.demo.entity.User;
import com.test.demo.entity.UserInfo;
import com.test.demo.vo.GroupInfo;
import com.test.demo.vo.TeacherInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 老师分组情况 Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupTeacherMapper extends BaseMapper<GroupTeacher> {
        GroupInfo selectTeacherInfo(Integer id);
        List<UserInfo> selectTeacher(Integer groupId);
        IPage<User> selectTchInG(IPage<User> page,Integer groupId);
        IPage<User> selectByNoGroup(IPage<User> page,Integer groupId);
        List<TeacherInfo> TchInGroup(Integer groupId);

}
