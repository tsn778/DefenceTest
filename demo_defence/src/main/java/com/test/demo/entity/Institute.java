package com.test.demo.entity;

/**
 * @Author tsn77
 * @Date 2021/5/7 14:42
 * @Version 1.0
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 学院实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Institute implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    /**
     * 学院名称
     */
    private String name;

}
