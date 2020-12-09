package com.jxywkj.application.pet.api.business;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.Business2;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.vo.AuthVo;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.yangwang.sysframework.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName BusinessAPIController
 * @Description 商家
 * @Author LiuXiangLin
 * @Date 2019/8/21 14:00
 * @Version 1.0
 **/
@Api(description = "商家")
@RestController
@RequestMapping("api/business")
public class BusinessApiController {
    @Resource
    StationService stationService;

    @Resource
    BusinessService businessService;

    @ApiOperation("获取商家电话号码")
    @GetMapping("/getPhoneByCityName")
    public JsonResult getBusinessPhoneByCityName(@RequestParam("cityName") String cityName) {
        List<Station> stationList = stationService.listStation(cityName);
        String phone = CollectionUtils.isEmpty(stationList) ? null : stationList.get(0).getServicePhone();
        if (!StringUtil.isNotNull(phone)) {
            phone = CollectionUtils.isEmpty(stationList) ? null : stationList.get(0).getPhone();
        }
        return JsonResult.success(phone);
    }

    @ApiOperation("获取商家列表")
    @GetMapping("/listByPosition")
    public JsonResult getBusinessByPosition(@RequestParam("longitude") double longitude,
                                            @RequestParam("latitude") double latitude,
                                            @RequestParam("limit") int limit,
                                            @RequestParam("offset") int offset) {
        return JsonResult.success(businessService.listByPosition(longitude, latitude, offset, limit, BusinessStateEnum.NORMAL));
    }

    @GetMapping("/listAllUnauditedBusiness")
    @ApiOperation(value = "获取未审核的商户")
    public JsonResult listUnauditedBusiness(@RequestParam("customerNo") String customerNo) {
        Station station = stationService.getByCustomerNo(customerNo);
        if (station == null) {
            return JsonResult.error(Code.CHECK_ERROR, "不是管理员");
        }
        return JsonResult.success(businessService.listUnauditedBusiness(station));
    }

    @PutMapping("/review")
    @ApiOperation(value = "审核商户通过")
    public JsonResult reviewBusiness(@RequestBody Business business) {
        if (business != null && business.getBusinessNo() != null) {
            business.setState(BusinessStateEnum.NORMAL.getType());
            return JsonResult.success(businessService.updateBusinessState(business));
        }
        return JsonResult.error(Code.CHECK_ERROR, "参数不完整");
    }

    @PutMapping("/reject")
    @ApiOperation(value = "驳回商户")
    public JsonResult rejectBusiness(@RequestBody Business business) {
        if (business == null) {
            return JsonResult.error(Code.CHECK_ERROR, "参数为空");
        }

        return JsonResult.success(businessService.rejectBusiness(business));
    }

    @GetMapping("/{businessNo:\\w+}")
    @ApiOperation(value = "通过商家编号查询认证")
    public JsonResult getAuthByBusinessNo(@PathVariable String businessNo) {
        return businessService.getAuthByBusinessNo(businessNo);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新商家信息")
    public JsonResult update(@RequestBody Business2 business2) {
        return businessService.update(business2);
    }

    @PostMapping("/insetBusiness")
    @ApiOperation(value = "上传商家认证")
    public JsonResult insetBusiness(@RequestBody AuthVo authVo) {
        return businessService.insetBusiness(authVo);
    }
}
