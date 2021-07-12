package com.test.demo.service.impl;

import com.test.demo.entity.Permission;
import com.test.demo.mapper.PermissionMapper;
import com.test.demo.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
