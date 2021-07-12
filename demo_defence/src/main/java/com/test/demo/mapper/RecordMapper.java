package com.test.demo.mapper;

import com.test.demo.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
public interface RecordMapper extends BaseMapper<Record> {
        List<Record> selectRecordByTch(Integer groupId,@Param("teacherId")Integer teacherId);
}
