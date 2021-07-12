package com.test.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.demo.entity.GroupStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.demo.entity.User;

/**
 * <p>
 * 学生具体分组情况 Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupStudentMapper extends BaseMapper<GroupStudent> {
    IPage<User> selectStuInG(IPage<User> page, Integer groupId);
    IPage<User> selectByNoGroup(IPage<User> page);
}
