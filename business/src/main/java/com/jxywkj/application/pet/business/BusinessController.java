package com.jxywkj.application.pet.business;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName BusinessController
 * @Description 商户
 * @Author LiuXiangLin
 * @Date 2019/8/28 15:08
 * @Version 1.0
 **/
@RestController
@RequestMapping("/business")
public class BusinessController {
    @Resource
    BusinessService businessService;

    @GetMapping("/getByBusinessNo")
    public JsonResult getByBusinessNo(@RequestParam("businessNo") String businessNo) {
        return JsonResult.success(businessService.getByBusinessNo(businessNo, BusinessStateEnum.NORMAL));
    }

    @GetMapping("/getSession")
    public JsonResult getBusinessBySession(@SessionAttribute("business") Business business) {
        return JsonResult.success(business);
    }
    @GetMapping("/selBusinessByNo")
    public JsonResult selBusinessByNo(@RequestParam("businessNo") String businessNo){
        return JsonResult.success(businessService.selByBusinessByNo(businessNo));
    }

}
