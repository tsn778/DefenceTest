package com.test.demo.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class SysToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.NONE)
    private Integer userId;

    private LocalDateTime expireTime;

    private String token;

    private LocalDateTime updateTime;


}
