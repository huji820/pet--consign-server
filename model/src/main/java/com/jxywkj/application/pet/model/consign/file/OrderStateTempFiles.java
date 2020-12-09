package com.jxywkj.application.pet.model.consign.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName OrderStateTempFiles
 * @Description 临时文件
 * @Author LiuXiangLin
 * @Date 2019/8/13 9:46
 * @Version 1.0
 **/

@Data
@ApiModel(value = "临时文件")
public class OrderStateTempFiles implements Serializable {
    private static final long serialVersionUID = -2821762131152496482L;
    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "上传日期")
    String uploadDate;

    @ApiModelProperty(value = "上传时间")
    String uploadTime;

    @ApiModelProperty(value = "文件地址")
    String fileAddress;

    @ApiModelProperty(value = "文件类型")
    String fileType;

    @ApiModelProperty(value = "文件名")
    String fileName;

    @ApiModelProperty(value = "显示路径")
    String viewAddress;

    public OrderStateTempFiles() {
    }

    public OrderStateTempFiles(String orderNo, String uploadDate, String uploadTime, String fileAddress, String fileType, String fileName) {
        this.orderNo = orderNo;
        this.uploadDate = uploadDate;
        this.uploadTime = uploadTime;
        this.fileAddress = fileAddress;
        this.fileType = fileType;
        this.fileName = fileName;
    }

    public OrderStateTempFiles(String orderNo, String uploadDate, String uploadTime, String fileAddress, String fileType, String fileName, String viewAddress) {
        this.orderNo = orderNo;
        this.uploadDate = uploadDate;
        this.uploadTime = uploadTime;
        this.fileAddress = fileAddress;
        this.fileType = fileType;
        this.fileName = fileName;
        this.viewAddress = viewAddress;
    }
}
