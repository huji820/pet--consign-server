package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.business.VerificationCodeController;
import com.jxywkj.application.pet.common.enums.*;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.Business2;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.common.CustomerOpenId;
import com.jxywkj.application.pet.model.common.LoginInfo;
import com.jxywkj.application.pet.model.common.VerificationCodeRecord;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.business.BusinessPowerService;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.CustomerOpenIdService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.wechat.boot.WxUserInfoUtils;
import com.yangwang.sysframework.wechat.boot.model.WxUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @微信登录
 * @Description
 * @Author Administrator
 * @Date 2019-04-18 0:21
 * @Version 1.0
 */
@Api(description = "微信授权登录")
@RestController
@RequestMapping("/api/oAuth")
public class OAuthApiController {

    @Autowired
    CustomerService customerService;
    @Resource
    StaffService staffService;

    @Resource
    StationService stationService;

    @Resource
    BusinessService businessService;

    @Resource
    WxUserInfoUtils wxUserInfoUtils;

    @Resource
    CustomerOpenIdService customerOpenIdService;

    @Resource
    BusinessPowerService businessPowerService;

    /**
     * <p>
     * 通过unionId登录
     * </p>
     *
     * @param unionId 微信unionid
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 14:29 2020/3/3
     **/
    @PostMapping("/unionId")
    @ApiOperation(value = "通过unionid登录")
    public JsonResult loginByUnionId(@RequestParam("unionId") String unionId) {
        // 查询是否存在用户
        Customer customer = customerService.getCustomerByUnionId(unionId);
        if (customer != null) {
            perfectCustomer(customer);
            return JsonResult.success(customer);

        } else {
            return JsonResult.error(Code.NOT_EXISTS, unionId);
        }
    }

    /**
     * <p>
     * 通过电话号码登录
     * </p>
     *
     * @param phone 电话号码
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 14:29 2020/3/3
     **/
    @PostMapping("/phone")
    @ApiOperation(value = "通过电话号码登录")
    public JsonResult loginByPhone(@RequestParam("phone") String phone,
                                   @RequestParam("verificationCode") String verificationCode,
                                   HttpSession session) {
        // 验证码校验
        VerificationCodeRecord verificationCodeRecord = (VerificationCodeRecord) session.getAttribute(VerificationCodeController.SESSION_VERIFICATION_CODE_ATTRIBUTE);
        if (verificationCodeRecord == null
                || !verificationCodeRecord.getPhone().equals(phone)
                || !verificationCodeRecord.getVerificationCode().equals(verificationCode)) {
            return JsonResult.error(Code.CHECK_ERROR, "验证码错误");
        }
        // 查询用户
        Customer customer = customerService.getCustomerByPhoneNumber(phone);
        if (customer != null) {
            perfectCustomer(customer);
            return JsonResult.success(customer);

        } else {
            return JsonResult.error(Code.NOT_EXISTS, phone);
        }
    }


    /**
     * <p>
     * 小程序登录
     * </p>
     *
     * @param loginInfo 用户信息对象
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 14:47 2020/4/7
     **/
    @PostMapping("/we-Chat")
    @ApiOperation(value = "小程序登录")
    public JsonResult loginOrReg(@RequestBody LoginInfo loginInfo) {
        // 解密数据
        WxUserInfo wxUserInfo = wxUserInfoUtils.decryptUserInfo(loginInfo.getEncryptedData(), loginInfo.getWxUserInfo().getSessionKey(), loginInfo.getIv());

        // 通过电话号码登录
        Customer customer = customerService.getCustomerByPhoneNumber(wxUserInfo.getPurePhoneNumber());

        if (customer == null) {
            // 获取用户或者商家
            Business business = null;
            Station station = null;

            if (StringUtils.isNotBlank(loginInfo.getShareOpenId())) {
                business = businessService.getByOpenId(loginInfo.getShareOpenId());
                if (business == null) {
                    station = stationService.getByAdminOpenId(loginInfo.getShareOpenId());
                }
            }

            customer = new Customer();
            customer.setCustomerNo(wxUserInfo.getPurePhoneNumber());
            customer.setCustomerName(loginInfo.getWxUserInfo().getNickname());
            customer.setUnionId(loginInfo.getWxUserInfo().getUnionId());
            customer.setOpenId(wxUserInfo.getOpenid());
            customer.setHeaderImage(loginInfo.getWxUserInfo().getHeadImgUrl());
            customer.setPhone(wxUserInfo.getPurePhoneNumber());
            customer.setLastLogintime(DateUtil.formatFull(new Date()));
            customer.setRegistrationDate(DateUtil.format(new Date(), DateUtil.FORMAT_SIMPLE));
            customer.setRegistrationTime(DateUtil.format(new Date(), DateUtil.FORMAT_TIME));
            customer.setSex(loginInfo.getWxUserInfo().getSex());
            customer.setPoints(BigDecimal.ZERO);
            customer.setBalance(BigDecimal.ZERO);
            customer.setShareBusiness(business);
            customer.setShareStation(station);

            customerService.insetCustomer(customer);

            Business byUnionId = businessService.getByUnionId(loginInfo.getWxUserInfo().getUnionId());
            // 新增商家
            if (byUnionId == null) {
                Business2 business2 = new Business2.Builder()
                        .businessName(customer.getCustomerName())
                        .headImg(customer.getHeaderImage())
                        .businessNo(StringUtils.getUuid())
                        .registerTime(DateUtil.formatFull(new Date()))
                        .contact(customer.getCustomerName())
                        .contactPhone(customer.getPhone())
                        .password(Business.PASSWORD)
                        .payBond(BondStateEnum.UNPAID.getState())
                        .state(BusinessStateEnum.NORMAL.getType())
                        .authType(AuthTypeEnum.UN_AUTH.getType())
                        .fans(0)
                        .followQty(0)
                        .unionId(customer.getUnionId())
                        .complete(0)
                        .credit(0)
                        .level("一级铲屎官")
                        .customerNo(customer.getCustomerNo())
                        .buildBusiness();
                business2.setPower(businessPowerService.get(business2));
                businessService.save(business2);
            }
        }

        customer.setOpenId(wxUserInfo.getOpenid()!=null&&wxUserInfo.getOpenid()!=""?wxUserInfo.getOpenid():loginInfo.getWxUserInfo().getOpenid());

        // 更新用户openid
        customerOpenIdService.saveOrUpdate(new CustomerOpenId(customer, AppTypeEnum.WE_APP_CONSIGN.getType(), loginInfo.getWxUserInfo().getOpenid()));

        // 更新用户信息
        Customer updateCustomer = new Customer();
        updateCustomer.setUnionId(loginInfo.getWxUserInfo().getUnionId());
        updateCustomer.setHeaderImage(loginInfo.getWxUserInfo().getHeadImgUrl());
        updateCustomer.setSex(loginInfo.getWxUserInfo().getSex());
        updateCustomer.setPhone(wxUserInfo.getPurePhoneNumber());
        updateCustomer.setCustomerName(loginInfo.getWxUserInfo().getNickname());
        customerService.updateCustomer(updateCustomer);

        perfectCustomer(customer);

        return JsonResult.success(customer);
    }


    /**
     * <p>
     * 完善用户对象
     * </p>
     *
     * @param customer 用户对象
     * @return void
     * @author LiuXiangLin
     * @date 10:41 2020/3/3
     **/
    private void perfectCustomer(Customer customer) {
        //获取openid
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(customer.getCustomerNo(), AppTypeEnum.WE_APP_CONSIGN);
        customer.setOpenId(customerOpenId.getOpenId());
        // 查询是否存在该员工
        Staff staff = staffService.getStaffByPhoneNumberAndState(customer.getPhone(), StaffStateEnum.NORMAL.getType());
        if(staff!=null&&staff.getStation().getStationNo()!=0){
            Station station = stationService.getStation(staff.getStation().getStationNo());
            staff.setStation(station);
        }
        customer.setStaff(staff);
        if (staff != null) {
            customer.setRole(staff.getRole());
        } else {
            customer.setRole(UserRolesEnum.ORDINARY_USERS.getUserRoles());
        }
        // 查询是否是商家
        Business business = businessService.getBusinessByPhone(customer.getPhone(), BusinessStateEnum.NORMAL);
        if (business != null) {
            customer.setBusiness(business);
        }
    }


    @GetMapping("/customer-no/{customerNo:\\w+}")
    @ApiOperation(value = "通过用户主键获取登录信息")
    public JsonResult getUserInfoByCustomerNo(@PathVariable String customerNo) {
        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        if (customer != null) {
            Business business = businessService.getBusinessByPhone(customer.getPhone(), BusinessStateEnum.NORMAL);
            customer.setBusiness(business);
            perfectCustomer(customer);
            return JsonResult.success(customer);
        }
        return JsonResult.error(Code.NOT_EXISTS, "用户不存在！");
    }
}
