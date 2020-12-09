package com.jxywkj.application.pet.admin.withdraw;

import com.jxywkj.application.pet.model.business.BusinessWithdraw;
import com.jxywkj.application.pet.service.facade.business.BusinessWithdrawService;
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
@RequestMapping("/admin/withDraw/business")
public class BusinessWithDrawController {

    @Autowired
    BusinessWithdrawService businessWithdrawService;

    @GetMapping("")
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

    @PutMapping("")
    public JsonResult passbusinessWithDraw(@RequestParam(value = "detail", required = false) String detail) throws Exception {
        List<BusinessWithdraw> businessWithdraws = (List<BusinessWithdraw>)JsonUtil.formObject(detail, BusinessWithdraw.class);
        businessWithdrawService.configWithdraw(businessWithdraws);
        return JsonResult.success("提现通过登记成功");
    }

    @DeleteMapping("/{detail}")
    public JsonResult rejectbusinessWithDraw(@PathVariable(value = "detail") String detail) throws Exception {
        List<BusinessWithdraw> businessWithdraws = (List<BusinessWithdraw>)JsonUtil.formObject(detail, BusinessWithdraw.class);
        businessWithdrawService.rejectWithdraw(businessWithdraws);
        return JsonResult.success("提现驳回成功");
    }
}
