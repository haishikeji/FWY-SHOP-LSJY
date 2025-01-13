package cn.px.zly.admin.module.other.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件服务
 * @author 品讯科技
 * 2021-03-06 下午2:34
 */
public interface UploadService {

    /**
     * 文件上传
     * @param objectName 上传文件路径和名称
     * @param file 上传文件
     * @return 上传文件全路径
     */
    String uploadFile(String objectName, MultipartFile file);
}
