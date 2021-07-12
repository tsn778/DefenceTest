package com.test.demo.vo;

import com.test.demo.entity.Group;
import com.test.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author tsn77
 * @Date 2021/4/19 17:07
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfo {
    private Integer Id;

    /**
     * 分组id
     */
    private Integer groupId;

    /**
     * 分组名称
     */
    private String groupName;

    private Integer userId;

    /**
     * 账号
     */
    private String account;

    private String email;

    /**
     * 姓名
     */
    private String userName;


}
