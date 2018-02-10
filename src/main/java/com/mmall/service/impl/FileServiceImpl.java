package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by lkmc2 on 2018/2/10.
 * 文件服务实现类
 */

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class); //日志打印器

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename(); //获取原始文件名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1); //获取文件后缀名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName; //拼接上传到服务器的文件名

        logger.info("开始上传文件，上传文件的文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, uploadFileName);;

        File fileDir = new File(path); //文件路径生成文件
        if (!fileDir.exists()) { //文件不存在
            fileDir.setWritable(true); //设置文件夹可写
            fileDir.mkdirs(); //创建文件夹
        }

        File targetFile = new File(path, uploadFileName); //要上传的目标文件
        try {
            file.transferTo(targetFile); //将上传的文件复制到目标文件
            FTPUtil.uploadFile(Lists.newArrayList(targetFile)); //将targetFile上传到ftp服务器
            targetFile.delete(); //上传完后，将出upload下的文件
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName(); //返回上传成功后的文件名
    }

}
