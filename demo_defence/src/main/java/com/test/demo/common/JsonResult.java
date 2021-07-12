package com.test.demo.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求数据的返回包
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {

    //状态码
    private Integer code;
    //提示信息
    private String msg;
    //所需数据
    private Object data;

    /**
     * 请求成功结果集
     */
    public static JsonResult buildSuccess(String msg, Object data){
        return new JsonResult(Constance.SUCCESS_CODE,msg,data);
    }

    /**
     * 请求失败结果集
     */
    public static JsonResult buildFailure(String msg, Object data){
        return new JsonResult(Constance.ERROR_CODE,msg,data);
    }

    /**
     * 自定义请求结果集
     */
    public static JsonResult build(int code, String message, Object data){
        return new JsonResult(code,message,data);
    }
}
