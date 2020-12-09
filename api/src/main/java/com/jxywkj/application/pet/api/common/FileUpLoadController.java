package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.service.facade.common.FileUploadService;
import com.yangwang.sysframework.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className FileUpLoadController
 * @date 2019/10/31 18:08
 **/
@Api(description = "文件上传")
@RestController
@RequestMapping("/file")
public class FileUpLoadController {
    @Resource
    FileUploadService fileUploadService;

    /**
     * 上传文件
     *
     * @param multipartFile 文件流对象
     * @return com.yangwang.sysframework.utils.JsonResult
     * @author LiuXiangLin
     * @date 18:11 2019/10/31
     **/
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public JsonResult uploadFiles(@RequestPart MultipartFile[] multipartFile) {
        return JsonResult.success(fileUploadService.uploadFiles(multipartFile));
    }
}
