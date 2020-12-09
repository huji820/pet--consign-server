package com.jxywkj.application.pet.model.consign.vo;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.Staff;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName OrderAssignmentVO
 * @Description 订单分配VO
 * @Author LiuXiangLin
 * @Date 2019/8/24 15:04
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "订单分配VO")
public class OrderAssignmentVO extends Order {
    @ApiModelProperty(value = "所属员工")
    List<Staff> staffList;

    @ApiModelProperty(value = "操作人")
    Staff operator;
}
