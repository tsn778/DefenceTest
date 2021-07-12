package com.test.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.demo.entity.User;
import com.test.demo.entity.UserInfo;
import com.test.demo.mapper.*;
import com.test.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-28
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    private final UserInfoMapper userInfoMapper;

    @Autowired(required = false)
    public UserInfoServiceImpl(UserInfoMapper userInfoMapper){
        this.userInfoMapper=userInfoMapper;

    }
    @Override
    public IPage<UserInfo> selectInInstitute(Integer instituteId,String gradeYear,int currentPage, int pageSize) {

        Page<UserInfo> page = new Page<>(currentPage,pageSize);
        IPage<UserInfo> result = userInfoMapper.selectUserInfo(page,instituteId,gradeYear);

        return result;
    }
}
