package com.test.demo.common;


/**
 * 基础配置静态类
 */
public interface Constance {
    /**
     * 两个日期的数据格式
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 文件映射
     */
    public static final String UPLOAD_PATH = "/Files";
    public static final String UPLOAD_LOCATION_MAPPING_PATH = "";
    public static final String UPLOAD_LOCATION_PATH = "";

    /**
     * 200 - 请求数据成功
     * 500 - 请求数据失败
     * 403 - 请求登录凭证失效
     * 18404 - 没有登录凭证token
     */
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer ERROR_CODE = 500;
    public static final Integer FORBIDDEN_CODE = 403;
    public static final Integer TOKEN_NULL = 18404;


    /**
     * 数据库每一页的记录数目为10
     */
    public static final Integer PAGE_RECORD = 10;

    /**
     * TOKEN 的过期时间(小时为单位 用在Service中)
     */
    public static final Integer TOKEN_EXPIRE=12;
}
