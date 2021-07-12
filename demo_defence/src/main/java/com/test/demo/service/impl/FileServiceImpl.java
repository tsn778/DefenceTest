package com.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.demo.common.JsonResult;
import com.test.demo.entity.Record;
import com.test.demo.mapper.RecordMapper;
import com.test.demo.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author tsn77
 * @Date 2021/4/29 15:28
 * @Version 1.0
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired(required = false)
    private RecordMapper recordMapper;
    private final static String  fileDir="files";
    private  final static String rootPath = "E:/myFile/";
    @Override
    public JsonResult upload(Integer userId,Integer groupId,Integer type,String path,MultipartFile[] multipartFiles, HttpServletResponse response, HttpServletRequest request) {
        File fileDir = new File(rootPath+path);
        QueryWrapper<Record> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).and(i->i.eq("group_id",groupId));
        Record recordOne = recordMapper.selectOne(queryWrapper);

        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        try {
            if (multipartFiles != null && multipartFiles.length > 0) {
                for(int i = 0;i<multipartFiles.length;i++){
                    try {
                        //以原来的名称命名,覆盖掉旧的
                        String keepPath=path+multipartFiles[i].getOriginalFilename();
                        String storagePath = rootPath+path+multipartFiles[i].getOriginalFilename();
                        System.out.println(storagePath);
                        System.out.println("上传的文件：" + multipartFiles[i].getName() + "," + multipartFiles[i].getContentType() + "," + multipartFiles[i].getOriginalFilename()
                                +"，保存的路径为：" + storagePath);
                        Streams.copy(multipartFiles[i].getInputStream(), new FileOutputStream(storagePath), true);
                        //或者下面的
                        // Path path = Paths.get(storagePath);
                        //Files.write(path,multipartFiles[i].getBytes());

                        switch (type){
                            case 1:
                                recordOne.setUserThesis(keepPath);
                                break;
                            case 2:
                                recordOne.setUserPpt(keepPath);
                                break;
                            case 3:
                                recordOne.setUserVideo(keepPath);
                                break;
                            case 4:
                                recordOne.setUserData(keepPath);
                                break;
                            case 5:
                                recordOne.setUserRecord(keepPath);
                                break;
                            default:
                                break;
                        }
                        if(recordOne==null){
                            recordOne.setUserId(userId);
                            recordOne.setGroupId(groupId);
                            recordMapper.insert(recordOne);
                        }
                        else
                        recordMapper.updateById(recordOne);
                    } catch (IOException e) {

                    }
                }
            }



        } catch (Exception e) {
            return JsonResult.buildFailure("上传失败,"+e.getMessage(),null);
        }
        return JsonResult.buildSuccess("上传成功!",null);

    }

    @Override
    public JsonResult download(String filePath, HttpServletResponse response, HttpServletRequest request) {
        OutputStream os = null;
        InputStream is= null;
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            //读取流
            File f = new File(rootPath+filePath);
            is = new FileInputStream(f);
            if (is == null) {
                System.out.println("下载附件失败，请检查文件“" + fileName + "”是否存在");
                return JsonResult.buildFailure("下载附件失败，请检查文件“" + fileName + "”是否存在",null);
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return JsonResult.buildFailure("下载附件失败,error:"+e.getMessage(),null);
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {

            }
        }
        return null;

    }
}
