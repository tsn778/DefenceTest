package com.test.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demo.entity.Group;
import com.test.demo.entity.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fff
 * @since 2021-04-28
 */
public interface UserInfoService extends IService<UserInfo> {
    //查询学院人员信息
    IPage<UserInfo> selectInInstitute(Integer instituteId,String gradeYear, int currentPage, int pageSize);

}
