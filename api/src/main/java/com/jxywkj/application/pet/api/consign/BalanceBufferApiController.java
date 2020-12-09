package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessBalance;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceBufferService;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceService;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.consign.BalanceService;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceBufferService;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BalanceBufferApiController
 * @date 2019/12/7 9:26
 **/
@RestController
@Api(description = "冻结金额以及可用金额")
@RequestMapping("/api/balance/buffer")
public class BalanceBufferApiController {
    @Resource
    StationBalanceBufferService stationBalanceBufferService;

    @Resource
    BusinessBalanceBufferService businessBalanceBufferService;

    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    BusinessBalanceService businessBalanceService;

    @Resource
    BalanceService balanceService;

    @Resource
    BusinessService businessService;

    @GetMapping("/station")
    @ApiOperation(value = "站点冻结余额以及可用余额")
    public JsonResult getStationBuffer(@RequestParam("stationNo") int stationNo,@RequestParam("customerNo")String customerNo) {
        Map<String, BigDecimal> resultMap = new HashMap<>(3);
        //冻结金额
        BigDecimal frozen = stationBalanceBufferService.getTotalAmount(String.valueOf(stationNo));
        Business business = businessService.getBusinessByPhone(customerNo, BusinessStateEnum.NORMAL);
        BigDecimal totalAmount;
        frozen = frozen == null?BigDecimal.ZERO:frozen;
        if (business  == null) {
            totalAmount = new BigDecimal(0);
            System.err.println("buufer business is null,customerNo is " + customerNo);
        } else {
            totalAmount = businessBalanceBufferService.getTotalAmount(business.getBusinessNo());
        }

        frozen = totalAmount !=null?frozen.add(totalAmount):frozen;
        //可用金额
        BigDecimal usable = balanceService.getByCustomerNo(customerNo) == null ? BigDecimal.valueOf(0) : balanceService.getByCustomerNo(customerNo) .subtract(frozen);
        resultMap.put("frozen", frozen == null ? BigDecimal.valueOf(0) : frozen);
        resultMap.put("usable", usable);

        return JsonResult.success(resultMap);
    }

    @GetMapping("/business")
    @ApiOperation(value = "驿站冻结余额以及可用余额")
    public JsonResult getBusinessBuffer(@RequestParam("businessNo") String businessNo) {
        Map<String, BigDecimal> resultMap = new HashMap<>(3);
        resultMap.put("frozen", businessBalanceBufferService.getTotalAmount(businessNo));
        BusinessBalance businessBalance = businessBalanceService.getByBusinessNo(businessNo);
        resultMap.put("usable", businessBalance == null ? BigDecimal.valueOf(0) : businessBalance.getTotalAmount());

        return JsonResult.success(resultMap);
    }
}
