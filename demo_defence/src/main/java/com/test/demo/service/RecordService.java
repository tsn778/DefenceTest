package com.test.demo.service;

import com.test.demo.common.JsonResult;
import com.test.demo.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface RecordService extends IService<Record> {
    JsonResult selectRecord(Integer userId, Integer groupId);
    List<Record> selectRecordByGT(Integer groupId,Integer teacherId);
}
