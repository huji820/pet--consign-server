package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderCallBackService;
import com.jxywkj.application.pet.service.facade.consign.StationAppraiseService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/stationAppraise")
@Api(description = "站点服务评价")
public class StationAppraiseApiController {

    @Resource
    StationAppraiseService stationAppraiseService;

    public JsonResult releaseComments(@RequestParam("stationNo")String stationNo,
                                      @RequestParam("businessNo")String businessNo,
                                      Integer praiseDegree, String content,
                                      List<String> appraiseImgs){
        if(StringUtils.isBlank(stationNo)||StringUtils.isBlank(businessNo)){
            return JsonResult.error(Code.PARAM_ERROR, "请求参数错误");
        }
        int i = stationAppraiseService.releaseComments(stationNo, businessNo, praiseDegree, content, appraiseImgs);
        return JsonResult.success(i);
    }
}
