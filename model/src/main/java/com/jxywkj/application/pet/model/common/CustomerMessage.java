package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName CustomerMessage
 * @Description 站内信
 * @Author LiuXiangLin
 * @Date 2019/9/9 14:09
 * @Version 1.0
 **/
@Data
@ApiModel(value = "站内信")
public class CustomerMessage {

    /*State 表达的是形态，而 Status 表达的是从一种形态转换成另一种形态的过程中，那些有显著特征的离散中间值。*/

    @ApiModelProperty(value = "主键")
    Integer messageNo;

    @ApiModelProperty(value = "发送者编号")
    String sendNo;

    @ApiModelProperty(value = "接收者编号")
    String receiveNo;

    @ApiModelProperty(value = "消息标题")
    String messageTitle;

    @ApiModelProperty(value = "消息内容")
    String messageContent;

    @ApiModelProperty(value = "消息状态")
    String status;

    @ApiModelProperty(value = "发送时间")
    String sendTime;

    @ApiModelProperty(value = "更新时间")
    String updateTime;

    @ApiModelProperty(value = "链接")
    String link;
}
