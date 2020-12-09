package com.jxywkj.application.pet.model.dto.dadi.response;

import com.jxywkj.application.pet.model.dto.dadi.XmlHead;
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
 * @className InsureRequestDto
 * @date 2020/8/10 11:48
 **/
@Data
@ApiModel(value = "大地请求返回类")
public class InsureResponseDto {

    @ApiModelProperty(value = "")
    String jobName;
    String interfaceService;
    String interfaceMethod;

    XmlHead xmlHead;
    List<TbResult> tbresultList;
}
