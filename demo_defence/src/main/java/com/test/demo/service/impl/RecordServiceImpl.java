package com.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Record;
import com.test.demo.entity.User;
import com.test.demo.mapper.RecordMapper;
import com.test.demo.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;
    @Override
    public JsonResult selectRecord(Integer userId, Integer groupId) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).and(i->i.eq("group_id",groupId));
        Record record = recordMapper.selectOne(queryWrapper);
        return JsonResult.buildSuccess("已查询",record);
    }

    @Override
    public List<Record> selectRecordByGT(Integer groupId, Integer teacherId) {
        return recordMapper.selectRecordByTch(groupId,teacherId);
    }
}
