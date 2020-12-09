package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-25 19:45
 * @Version 1.0
 */
@Data
@ApiModel(value = "登录日志表")
public class LoginLog {

    int id;

    String loginHost;

    Station station;

    Staff staff;

    String loginTime;


}
