package cn.px.zly.admin.module.other.service;

import cn.px.core.common.exeption.BusinessException;
import cn.px.core.util.StringUtil;
import cn.px.manager.oss.OssManager;
import cn.px.zly.properties.ZlyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 上传文件服务
 * @author 品讯科技
 * 2021-03-06 下午2:35
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private OssManager ossManager;
    @Autowired
    private ZlyProperties properties;

    @Override
    public String uploadFile(String objectName, MultipartFile file) {
        if(StringUtil.isBlank(objectName)) {
            throw new BusinessException("对象名称为空");
        }
        String urlPrefix = properties.getUpload().getUrlPrefix();
        String bucketName = properties.getUpload().getBucketName();
        try(InputStream inputStream = file.getInputStream()) {
            ossManager.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            log.error("upload file error.", e);
        }
        return urlPrefix+objectName;
    }
}
