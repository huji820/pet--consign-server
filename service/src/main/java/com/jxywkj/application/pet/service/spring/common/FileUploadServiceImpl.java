package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.common.enums.MediaFileTypeEnum;
import com.jxywkj.application.pet.common.utils.AliOssObjectUtils;
import com.jxywkj.application.pet.common.utils.FileUpLoadState;
import com.jxywkj.application.pet.common.utils.FileUtils;
import com.jxywkj.application.pet.service.facade.common.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className FileUploadServiceImpl
 * @date 2019/10/31 18:25
 **/
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Resource
    AliOssObjectUtils aliOssObjectUtils;

    @Resource
    FileUtils fileUtils;

    @Override
    public List<FileUpLoadState> uploadFiles(MultipartFile[] multipartFiles) {
        List<FileUpLoadState> result = new ArrayList<>();
        MediaFileTypeEnum mediaFileTypeEnum;
        for (MultipartFile multipartFile : multipartFiles) {
            // 校验文件类类型
            mediaFileTypeEnum = MediaFileTypeEnum.filter(fileUtils.getFileSuffix(multipartFile.getOriginalFilename()));
            if (mediaFileTypeEnum == null) {
                continue;
            }
            // 开始上传文件
            try {
                result.add(aliOssObjectUtils.uploadImg(multipartFile, mediaFileTypeEnum));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
