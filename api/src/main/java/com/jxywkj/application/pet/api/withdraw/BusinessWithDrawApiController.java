package com.jxywkj.application.pet.api.withdraw;

import com.jxywkj.application.pet.model.business.BusinessWithdraw;
import com.jxywkj.application.pet.service.facade.business.BusinessWithdrawService;
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
@Api(description = "商家提现审批")
@RestController
@RequestMapping("/api/withDraw/business")
public class BusinessWithDrawApiController {

    @Autowired
    BusinessWithdrawService businessWithdrawService;

    @ApiOperation(value = "查询商家提现列表")
    @GetMapping("/selBusinessWithDrawList")
    public Pagination listbusinessWithDraw(@RequestParam(value = "withdrawNo", required = false) String withdrawNo,
                                          @RequestParam(value = "businessNo", required = false) String businessNo,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate,
                                          @RequestParam(value = "active", required = false, defaultValue = "false") String active,
                                          @RequestParam(value = "start", required = false) int start,
                                          @RequestParam(value = "limit", required = false) int limit) {
        Pagination pagination = new Pagination(start, limit);

        pagination.setRoot(businessWithdrawService.listBusinessWithdraw(withdrawNo, businessNo, startDate, endDate, TypeConvertUtil.$Boolean(active), start, limit));
        pagination.setTotalCount(businessWithdrawService.countBusinessWithdraw(withdrawNo, businessNo, startDate, endDate, TypeConvertUtil.$Boolean(active)));

        return pagination;
    }

    @ApiOperation(value = "同意商家提现请求")
    @PutMapping("/agreeBusinessWithDraw")
    public JsonResult passbusinessWithDraw(@RequestParam(value = "detail", required = false) String detail) throws Exception {
        List<BusinessWithdraw> businessWithdraws = (List<BusinessWithdraw>)JsonUtil.formObject(detail, BusinessWithdraw.class);
        businessWithdrawService.configWithdraw(businessWithdraws);
        return JsonResult.success("提现通过登记成功");
    }

    @ApiOperation(value = "驳回商家体现请求")
    @DeleteMapping("/{detail}")
    public JsonResult rejectbusinessWithDraw(@PathVariable(value = "detail") String detail) throws Exception {
        List<BusinessWithdraw> businessWithdraws = (List<BusinessWithdraw>)JsonUtil.formObject(detail, BusinessWithdraw.class);
        businessWithdrawService.rejectWithdraw(businessWithdraws);
        return JsonResult.success("提现驳回成功");
    }
}
