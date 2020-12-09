package com.jxywkj.application.pet.out;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.consign.VariFlightService;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.file.FileUtil;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


/**
 * @Description
 * @Author Administrator
 * @Date 2019-08-09 15:55
 * @Version 1.0
 */
@Api
@RestController
@RequestMapping("/out/variflight")
public class VariFlightController {
    @Value("${variflight.appid}")
    String appid;

    @Value("${variflight.appsecurity}")
    String appsecurity;

    @Resource
    VariFlightService variFlightService;

    @RequestMapping("")
    public JsonResult push(@RequestBody String datas) {

        System.err.println("======================开始发送推送====================");

        PushFlightResponseData responseData = null;
        try {
            responseData = new PushFlightResponseData(
                    ((ArrayList<Map<String, Object>>) JsonUtil.formObject(datas, Map.class)).get(0)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.err.println("====================推送内容为" + datas + "======================");

        // 发送推送
        if (responseData != null) {
            variFlightService.orderPush(responseData);
        }

        System.err.println("=====================推送完毕======================");

        return JsonResult.success();
    }
}
