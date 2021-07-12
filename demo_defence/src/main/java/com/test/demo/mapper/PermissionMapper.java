package com.test.demo.mapper;

import com.test.demo.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    Set<Permission> findPersByRoleId(Integer roleId);
}
