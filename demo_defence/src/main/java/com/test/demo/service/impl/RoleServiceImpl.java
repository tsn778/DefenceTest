package com.test.demo.service.impl;

import com.test.demo.common.JsonResult;
import com.test.demo.entity.Role;
import com.test.demo.entity.SysToken;
import com.test.demo.mapper.RoleMapper;
import com.test.demo.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
