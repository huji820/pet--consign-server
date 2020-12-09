package com.jxywkj.application.pet.model.vo.report;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-07 23:53
 * @Version 1.0
 */
@Data
@ApiModel(value = "首页简报")
public class HomeSimpleReportVO {

    int stationNum;

    int businessNum;

    int customerNum;


}
