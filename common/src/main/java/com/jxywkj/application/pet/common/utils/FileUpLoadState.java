package com.jxywkj.application.pet.common.utils;

import com.jxywkj.application.pet.common.enums.MediaFileTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName FileUpLoadState
 * @Description 文件上传返回值
 * @Author LiuXiangLin
 * @Date 2019/7/22 15:29
 * @Version 1.0
 **/
@Data
public class FileUpLoadState {
    public static final String SUCCESS = "success";

    @ApiModelProperty("状态")
    String state;

    @ApiModelProperty("新文件名称")
    String newFileName;

    @ApiModelProperty("新文件地址")
    String fileAddress;

    @ApiModelProperty("文件类型")
    MediaFileTypeEnum mediaFileTypeEnum;

}
