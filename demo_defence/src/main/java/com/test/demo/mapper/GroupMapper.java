package com.test.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.entity.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 分组 Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupMapper extends BaseMapper<Group> {
    IPage<Group> selectGroupInfo(IPage<Group> page, @Param("instituteId")Integer instituteId);
    Group selectGroupOne(Integer groupId);
    List<Group> selectMyGroup(Integer teacherId);
    List<Group> selectStudentGroup(Integer studentId);
}
