package com.jxywkj.application.pet.model.common;

import com.jxywkj.application.pet.model.consign.OrderField;
import com.jxywkj.application.pet.model.consign.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName PostPower
 * @Description 角色权限
 * @Author LiuXiangLin
 * @Date 2019/9/25 9:13
 * @Version 1.0
 **/
@ApiModel(description = "角色权限")
@Data
public class PostPower {
    @ApiModelProperty(value = "所属权限")
    Post post;

    @ApiModelProperty(value = "表列")
    OrderField orderField;

    @ApiModelProperty(value = "订单列表集合")
    List<OrderField> orderFieldList;
}
