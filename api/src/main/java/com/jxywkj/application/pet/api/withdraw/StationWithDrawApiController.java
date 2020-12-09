package com.jxywkj.application.pet.api.withdraw;

import com.jxywkj.application.pet.model.consign.StationWithdraw;
import com.jxywkj.application.pet.service.facade.consign.StationWithdrawService;
import com.yangwang.sysframework.utils.JsonResult;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.Pagination;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author GuoPengCheng
 * @Date 2020-07-20 16:24
 * @Version 1.0
 */
@Api(description = "站点提现审批")
@RestController
@RequestMapping("/api/withDraw/station")
public class StationWithDrawApiController {

    @Autowired
    StationWithdrawService stationWithdrawService;

    @ApiOperation(value = "查询站点提现列表")
    @GetMapping("/selStationWithDrawList")
    public Pagination listStationWithDraw(@RequestParam(value = "withdrawNo", required = false) String withdrawNo,
                                          @RequestParam(value = "stationNo", required = false) String stationNo,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate,
                                          @RequestParam(value = "active", required = false, defaultValue = "false") String active,
                                          @RequestParam(value = "start", required = false) int start,
                                          @RequestParam(value = "limit", required = false) int limit) {
        Pagination pagination = new Pagination(start, limit);

        pagination.setRoot(stationWithdrawService.listStationWithdraw(withdrawNo, stationNo, startDate, endDate, TypeConvertUtil.$Boolean(active), start, limit));
        pagination.setTotalCount(stationWithdrawService.countStationWithdraw(withdrawNo, stationNo, startDate, endDate, TypeConvertUtil.$Boolean(active)));

        return pagination;
    }

    @ApiOperation(value = "同意站点提现请求")
    @PutMapping("/agreeStationWithDraw")
    public JsonResult passStationWithDraw(@RequestParam(value = "detail", required = false) String detail) throws Exception {
        List<StationWithdraw> stationWithdraws = (List<StationWithdraw>)JsonUtil.formObject(detail, StationWithdraw.class);
        stationWithdrawService.confirmWithdraws(stationWithdraws);
        return JsonResult.success("提现通过登记成功");
    }

    @ApiOperation(value = "驳回站点提现请求")
    @DeleteMapping("/{detail}")
    public JsonResult rejectStationWithDraw(@PathVariable(value = "detail") String detail) throws Exception {
        List<StationWithdraw> stationWithdraws = (List<StationWithdraw>)JsonUtil.formObject(detail, StationWithdraw.class);
        stationWithdrawService.rejectWithdraws(stationWithdraws);
        return JsonResult.success("提现驳回成功");
    }
}
