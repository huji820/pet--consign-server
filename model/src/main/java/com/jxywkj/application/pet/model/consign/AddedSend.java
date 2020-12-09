package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AddedSend
 * @Description 送宠到家
 * @Author LiuXiangLin
 * @Date 2019/7/25 10:47
 * @Version 1.0
 **/
@Data
@ApiModel("送宠到家")
public class AddedSend extends AbstractAddedOnDoor {
    @ApiModelProperty("编号")
    int sendNo;
}
