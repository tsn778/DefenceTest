package com.test.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author tsn77
 * @Date 2021/6/8 15:22
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefenceInfo {
    private Integer nowPeriod;
    private Integer now;
    private Integer am;

    private Integer pm;
    private Integer front;
    private Object object;
}
