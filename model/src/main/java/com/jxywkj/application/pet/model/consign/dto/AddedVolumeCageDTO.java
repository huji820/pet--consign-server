package com.jxywkj.application.pet.model.consign.dto;

import com.jxywkj.application.pet.model.consign.AddedVolumeCage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 按体积算笼具DTO对象
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedVolumeCageDTO
 * @date 2019/10/21 16:06
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "按体积算航空箱配置DTO对象")
public class AddedVolumeCageDTO extends AddedVolumeCage {
    @ApiModelProperty(value = "使用按体积方式算航空箱")
    Boolean useVolume;
}
