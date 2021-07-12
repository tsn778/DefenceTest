package com.test.demo.mapper;

import com.test.demo.entity.GroupSort;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 学生排序 Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface GroupSortMapper extends BaseMapper<GroupSort> {
        List<GroupSort> selectSort(Integer groupId);
        List<GroupSort> resetSort(Integer groupId);
        List<GroupSort> selectSortByGroup(Integer groupId);
        GroupSort selectSortById(Integer groupId,Integer userId);
}
