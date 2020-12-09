package com.jxywkj.application.pet.model.dto.dadi.request;

import com.jxywkj.application.pet.model.dto.dadi.XmlHead;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "大地请求类")
public class InsureRequestDto {

    List<TbPolicyDto> tbPolicyDtoList;
    XmlHead xmlHead;
}
