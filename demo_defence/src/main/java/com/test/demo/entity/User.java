package com.test.demo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Set;

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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    /**
     * 账号
     */
    private String account;

    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 学院
     */
    private Integer institute;

    /**
     * 班级
     */
    private String userClass;

    /**
     * 类型：0为需要参加的人；1为不用答辩的人
     */
    private Integer state;

    /**
     * 注册时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime registerTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;
    @TableField(exist = false)
    private Set<Role> roles;
    @TableField(exist = false)
    private String instituteName;
}
