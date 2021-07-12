package com.test.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author fff
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    /**
     * 学号或教工号也是用户账号
     */
    private String account;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 学院
     */
    private Integer instituteId;
    /**
     * 结业年级
     */
    private String gradeYear;
    /**
     * 专业
     */
    private String major;

    /**
     * 用户班级
     */
    private String userClass;

    /**
     * 论文题目，学生需要
     */
    private String thesisTitle;

    /**
     * 指导老师
     */
    @TableField("user_Instructor")
    private String userInstructor;

    /**
     * 状态
     */
    private Integer state;
    /**
     * 学院名称
     */
    @TableField(exist = false)
    private  String institute;
    @TableField(exist = false)
    private Integer groupId;
    @TableField(exist = false)
    private String groupName;
    @TableField(exist = false)
    private Integer teacherId;
    @TableField(exist = false)
    private Integer status;
    @TableField(exist = false)
    private Integer groupStudentId;
    @TableField(exist = false)
    private Integer timePeriod;
    @TableField(exist = false)
    private Integer sort;
    @TableField(exist = false)
    private String defenceTime;
    @TableField(exist = false)
    private String defencePlace;
    @TableField(exist = false)
    private String teacherName;

}
