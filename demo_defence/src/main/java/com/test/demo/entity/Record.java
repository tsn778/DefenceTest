package com.test.demo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer groupId;
    /**
     * 论文内容
     */
    private String userThesis;

    /**
     * ppt
     */
    private String userPpt;

    /**
     * 演示视频
     */
    private String userVideo;

    /**
     * 链接地址
     */
    private String userAddr;

    /**
     * 相关资料
     */
    private String userData;

    /**
     * 答辩记录
     */
    private String userRecord;

    /**
     * 逻辑删除
     */
    private Integer deleted;
    @TableField(exist = false)
    private  Integer teacherId;
    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String account;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String thesisTitle;


}
