package com.test.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分组
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 学院id
     */
    private Integer instituteId;
    /**
     * 结业年级
     */
    private String gradeYear;
    /**
     * 组长id
     */

    private Integer groupLeaderId;

    /**
     * 秘书id
     */
    private Integer groupSecretaryId;

    /**
     * 状态;1为正常状态；2为失效
     */
    private Integer status;
    private Integer action;
    /**
     * 小组具体答辩时间
     */
    private String groupDefenceTime;

    /**
     * 小组答辩地址
     */
    private String groupDefencePlace;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String leaderName;
    @TableField(exist = false)
    private String secretaryName;
    @TableField(exist = false)
    private String institute;

}
