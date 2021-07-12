package com.test.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 学生排序
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupSort implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    @TableField("group_student_id")
    private Integer groupStudentId;

    /**
     * 学生id
     */
    private Integer userId;

    /**
     * 排序
     */
    private Integer sort;
    private Integer timePeriod;
    /**
     * 答辩时间
     */
    private String defenceTime;

    /**
     * 答辩地点
     */
    private String defencePlace;
    @TableField(exist=false)
    private String account;
    @TableField(exist=false)
    private String userName;
    @TableField(exist=false)
    private Integer test;
    @TableField(exist=false)
    private Integer state;
}
