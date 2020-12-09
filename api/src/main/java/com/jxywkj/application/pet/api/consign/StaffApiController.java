package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.business.VerificationCodeController;
import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.common.VerificationCodeRecord;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import com.jxywkj.application.pet.service.facade.consign.StationMessageService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @ClassName StaffApiController
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/17 15:19
 * @Version 1.0
 **/
@RestController
@Api(description = "员工")
@RequestMapping(value = "/api/staff")
public class StaffApiController {
    @Resource
    StationService stationService;

    @Resource
    StaffService staffService;

    @Resource
    CustomerService customerService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    CustomerMessageService customerMessageService;

    @GetMapping("/{customerNo}")
    @ApiOperation(value = "管理员查询其站点的所有员工")
    public JsonResult listByAdmin(@PathVariable String customerNo) {
        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        if (customer == null) {
            return JsonResult.success();
        }
        Staff staff = staffService.getByCustomerNo(customerNo);
        if(staff == null){
            return JsonResult.success();
        }
        if(staff.getRole().equals(1)||staff.getRole().equals(2)){
            return JsonResult.success(staffService.listByStationNo(staff.getStation().getStationNo()));
        }
        return JsonResult.success();
    }

    @GetMapping("/listByProvinceAndCity")
    @ApiOperation(value = "通过省市查询所有站点信息")
    public JsonResult listByProvinceAndCity(@RequestParam("province") String province, @RequestParam("city") String city) {
        return JsonResult.success(stationService.listStationByProvinceAndCity(province, city));
    }

    @PostMapping("/applyForStaff")
    @ApiOperation(value = "申请成为员工")
    public JsonResult applyForStaff(@RequestBody Staff staff, HttpSession session) {
        VerificationCodeRecord verificationCodeRecord = (VerificationCodeRecord) (session.getAttribute(VerificationCodeController.SESSION_VERIFICATION_CODE_ATTRIBUTE));

        if (verificationCodeRecord == null || verificationCodeRecord.getVerificationCode().isEmpty() || staff == null || StringUtils.isBlank(staff.getPhone())) {
            return JsonResult.error(Code.CHECK_ERROR, "参数不完整");
        }

        if (!verificationCodeRecord.getVerificationCode().equals(staff.getVerificationCode())) {
            return JsonResult.error(Code.CHECK_ERROR, "验证码错误");
        }

        Staff existStaff = staffService.getByPhone(staff.getPhone());

        if (existStaff != null && StaffStateEnum.UNAUDITED.getType() == existStaff.getState()) {
            return JsonResult.error(Code.NAME_ALREADY_EXISTS, "该员工正在审核中！");
        }
        if (existStaff != null && StaffStateEnum.NORMAL.getType() == existStaff.getState()) {
            return JsonResult.error(Code.NAME_ALREADY_EXISTS, "该员工已经存在！");
        }
        if (existStaff != null && StaffStateEnum.USELESS.getType() == existStaff.getState()) {
            return JsonResult.error(Code.NAME_ALREADY_EXISTS, "该员工已经停用！");
        }
        if (existStaff != null) {
            return JsonResult.error(Code.NAME_ALREADY_EXISTS, "该员工已经存在！");
        }

        staff.setPwd("999");
        staff.setState(StaffStateEnum.UNAUDITED.getType());

        int result = staffService.insertStaff(staff);

        if (result > 0) {
            // 发送一条站内信给管理员
            stationMessageService.saveApplyStaffMessage(staff.getStation());
            // 发送站内信给员工提示重新登录
            customerMessageService.saveStaffAuditPass(staff);

            // 清除session中的验证码
            session.removeAttribute(VerificationCodeController.SESSION_VERIFICATION_CODE_ATTRIBUTE);

            return JsonResult.success(result);
        }

        return JsonResult.error(Code.INSERT_ERROR, "新增失败！");
    }

    @GetMapping("/listUnauditedStaff")
    @ApiOperation(value = "获取待审核员工列表")
    public JsonResult listUnauditedStaff(@RequestParam("phone") String phone) {
        Station station = stationService.getByPhone(phone);

        if (station != null) {
            return JsonResult.success(staffService.listByStationNoAndState(StaffStateEnum.UNAUDITED.getType(), station.getStationNo()));
        }

        return JsonResult.error(Code.CHECK_ERROR, Code.CHECK_ERROR.getMessage());
    }

    @PutMapping("/review")
    @ApiOperation(value = "审核员工")
    public JsonResult reviewStaff(@RequestBody Staff staff) {
        return JsonResult.success(staffService.reviewStaff(staff));
    }

    @PutMapping("/reject")
    @ApiOperation(value = "驳回员工申请")
    public JsonResult rejectStaff(@RequestBody Staff staff) {
        if (staff == null) {
            return JsonResult.error(Code.CHECK_ERROR, "参数为空");
        }

        return JsonResult.success(staffService.rejectStaff(staff));
    }

    @ApiOperation("修改员工")
    @PutMapping("update")
    public JsonResult updateStaff(@RequestParam("phone") String phone,
                                  @RequestParam("staffSex") String staffSex, @RequestParam("staffName") String staffName,
                                  @RequestParam("role") Integer role, @RequestParam("staffNo") int staffNo) {
        staffService.updateStaff(phone, staffName, role, staffNo, staffSex);
        return JsonResult.success("修改成功");
    }

    @ApiOperation("删除职员")
    @PutMapping("/delete")
    public JsonResult deleteStaff(@RequestParam("staffNo") int staffNo) {
        staffService.deleteStaff(staffNo);
        return JsonResult.success("淘汰成功");
    }
}
