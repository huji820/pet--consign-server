package com.jxywkj.application.pet.model.consign;

import com.jxywkj.application.pet.model.common.CustomerMessage;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName StationMessage
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/10 13:59
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "站点站内信")
public class StationMessage extends CustomerMessage {

}
