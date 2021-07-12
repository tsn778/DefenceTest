package com.test.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Group;
import com.test.demo.entity.Record;
import com.test.demo.entity.SysToken;
import com.test.demo.entity.User;
import com.test.demo.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fff
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/demo/record")
public class RecordController {

    @Autowired
    private FileService fileService;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GroupService groupService;

    @ApiImplicitParam(name = "file", value = "文件流", dataType = "__file", paramType = "form")
    @RequestMapping  (path = "/upload",consumes = "multipart/*",method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public JsonResult uploadFile(@RequestParam("file") MultipartFile[] multipartFiles,@RequestParam(value = "userId")Integer userId ,@RequestParam(value = "groupId")Integer groupId ,@RequestParam(value = "type")Integer type , final HttpServletResponse response, final HttpServletRequest request,@RequestHeader("token")String token) {

        SysToken byToken = shiroService.findByToken(token);
        System.out.println("学生："+byToken.getUserId());
        Group group = groupService.selectGroupOne(groupId);
        String path=group.getInstitute()+"/"+group.getGradeYear();

        switch (type){
            case 1:
                path+="/论文/";
                break;
            case 2:
                path+="/PPT/";
                break;
            case 3:
                path+="/视频/";
                break;
            case 4:
                path+="/材料/";
                break;
            case 5:
                path+="/答辩记录/";
                break;
            default:
                break;
        }
        path+=group.getGroupName()+"/";
        return    fileService.upload(userId,groupId,type,path,multipartFiles, response, request);
    }

    @RequestMapping("/download")
    public JsonResult downloadFile(@RequestParam String fileName, final HttpServletResponse response, final HttpServletRequest request){
        return fileService.download(fileName, response, request);
    }
    @Autowired
    private DocumentConverter converter;  //用于转换


    @GetMapping("/testPreview")
    public void toPdfFile(@RequestParam("path")String path, HttpServletResponse response) {
        System.out.println(path);
        File file = new File("E:/myFile/"+path);//需要转换的文件
        try {
            File newFile = new File("E:/testMyDoc");//转换之后文件生成的地址
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            String savePath="E:/testMyDoc/"; //pdf文件生成保存的路径
            String fileName="JCccc"+ UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
            String fileType=".pdf"; //pdf文件后缀
            String newFileMix=savePath+fileName+fileType;  //将这三个拼接起来,就是我们最后生成文件保存的完整访问路径了

            //文件转化
            converter.convert(file).to(new File(newFileMix)).execute();
            //使用response,将pdf文件以流的方式发送的前端浏览器上
            ServletOutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(new File(newFileMix));// 读取文件
            int i = IOUtils.copy(in, outputStream);   // copy流数据,i为字节数
            in.close();
            outputStream.close();
            System.out.println("流已关闭,可预览,该文件字节大小："+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ApiOperation(value = "得到论文信息", notes = "参数:学生id,组id")
    @GetMapping("/recordInfo")
    public JsonResult StudentGroup(@RequestParam("userId")Integer userId,@RequestParam("groupId")Integer groupId,@RequestHeader("token")String token){
        return recordService.selectRecord(userId,groupId);
    }
    @ApiOperation(value = "得到论文信息列表", notes = "参数:教师id,组id")
    @GetMapping("/recordList")
    public List<Record> RecordListByGT(@RequestParam(value = "teacherId",required = false)Integer teacherId,@RequestParam("groupId")Integer groupId,@RequestHeader("token")String token){
        return recordService.selectRecordByGT(groupId,teacherId);
    }




}

