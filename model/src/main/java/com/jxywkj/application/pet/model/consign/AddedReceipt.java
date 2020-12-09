package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AddedReceipt
 * @Description 送宠到家实体类
 * @Author LiuXiangLin
 * @Date 2019/7/25 11:31
 * @Version 1.0
 **/
@Data
@ApiModel("送货上门")
public class AddedReceipt extends AbstractAddedOnDoor {
    @ApiModelProperty("编号")
    int receiptNo;
}
