package com.jxywkj.application.pet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className DADIINsureRequestDto
 * @date 2020/8/6 18:16
 **/
@Data
@ApiModel(value = "大地投保请求DTO")
public class DADIInsureRequestDto {

    @ApiModelProperty(value = "请求头")
    XmlHead xmlHead;

    @ApiModelProperty(value = "请求总节点")
    List<TbPolicyDto> tbPolicyDtoList;
}
