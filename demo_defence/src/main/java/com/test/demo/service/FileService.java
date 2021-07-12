package com.test.demo.service;

import com.test.demo.common.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author tsn77
 * @Date 2021/4/29 15:25
 * @Version 1.0
 */
public interface FileService {
    JsonResult upload(Integer userId,Integer groupId,Integer type,String path,MultipartFile[] multipartFiles, HttpServletResponse response, HttpServletRequest request);
    JsonResult download(String fileName, HttpServletResponse response, HttpServletRequest request);

}
