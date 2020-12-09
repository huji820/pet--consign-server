package com.jxywkj.application.pet.admin.withdraw;

import com.jxywkj.application.pet.model.consign.StationWithdraw;
import com.jxywkj.application.pet.service.facade.consign.StationWithdrawService;
import com.yangwang.sysframework.utils.JsonResult;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.Pagination;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-06 16:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/withDraw/station")
public class StationWithDrawController {

    @Autowired
    StationWithdrawService stationWithdrawService;

    @GetMapping("")
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

    @PutMapping("")
    public JsonResult passStationWithDraw(@RequestParam(value = "detail", required = false) String detail) throws Exception {
        List<StationWithdraw> stationWithdraws = (List<StationWithdraw>)JsonUtil.formObject(detail, StationWithdraw.class);
        stationWithdrawService.confirmWithdraws(stationWithdraws);
        return JsonResult.success("提现通过登记成功");
    }

    @DeleteMapping("/{detail}")
    public JsonResult rejectStationWithDraw(@PathVariable(value = "detail") String detail) throws Exception {
        List<StationWithdraw> stationWithdraws = (List<StationWithdraw>)JsonUtil.formObject(detail, StationWithdraw.class);
        stationWithdrawService.rejectWithdraws(stationWithdraws);
        return JsonResult.success("提现驳回成功");
    }
}
