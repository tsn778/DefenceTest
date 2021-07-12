package com.test.demo.mapper;

import com.test.demo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface RoleMapper extends BaseMapper<Role> {
    Set<Role> findRolesByUserId(Integer userId);
}
