package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lkmc2 on 2018/2/10.
 * 文件服务接口
 */

public interface IFileService {
    /**
     * 上传文件
     * @param file 文件
     * @param path 上传文件路径
     * @return 上传成功后的文件名或者null
     */
    String upload(MultipartFile file, String path);
}
