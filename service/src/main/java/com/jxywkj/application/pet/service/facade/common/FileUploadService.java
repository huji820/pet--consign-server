package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.common.utils.FileUpLoadState;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className FileUploadService
 * @date 2019/10/31 18:24
 **/
public interface FileUploadService {
    /**
     * 上传文件
     *
     * @param multipartFiles 文件流对象
     * @return com.yangwang.application.pet.common.utils.FileUpLoadState
     * @author LiuXiangLin
     * @date 18:24 2019/10/31
     **/
    List<FileUpLoadState> uploadFiles(MultipartFile[] multipartFiles);
}
