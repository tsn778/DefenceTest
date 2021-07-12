package com.test.demo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.test.demo.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author tsn77
 * @Date 2021/5/20 14:39
 * @Version 1.0
 */
@Data
public class TeacherInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer groupTeacherId;
    private Integer userId;

    /**
     * 账号
     */
    private String account;

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
     * 类型：0为需要参加的人；1为不用答辩的人
     */
    private Integer state;

    private String instituteName;
}
