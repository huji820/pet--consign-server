package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.common.enums.AppTypeEnum;
import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.common.BlackList;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.common.CustomerOpenId;
import com.jxywkj.application.pet.model.common.VerificationCodeRecord;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.BlackListService;
import com.jxywkj.application.pet.service.facade.common.CustomerOpenIdService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.vdurmont.emoji.EmojiParser;
import com.yangwang.sysframework.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ClassName CustomerApiController
 * @Description: 客户Cotroller
 * @Author Aze
 * @Date 2019/7/13 10:38
 * @Version 1.0
 **/
@Api(description = "用户管理")
@RestController("customerController")
@RequestMapping("/api/customer")
public class CustomerApiController {
    @Autowired
    CustomerService customerService;

    @Resource
    StationService stationService;

    @Resource
    BusinessService businessService;

    @Resource
    BlackListService blackListService;

    @Resource
    CustomerOpenIdService customerOpenIdService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "微信的openID", dataType = "String", required = true),
            @ApiImplicitParam(name = "customerName", value = "用户名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "headerImage", value = "用户头像", dataType = "String", required = false),
            @ApiImplicitParam(name = "phone", value = "用户电话", dataType = "String", required = true),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "String", required = false)
    })
    @ApiOperation("注册用户")
    @PostMapping("")
    public JsonResult insertCustomer(@RequestParam(value = "openId", required = false) String openId,
                                     @RequestParam(value = "customerName", required = false) String customerName,
                                     @RequestParam(value = "headerImage", required = false) String headerImage,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam(value = "sex") String sex,
                                     @RequestParam(value = "verificationCode") String verificationCode,
                                     @RequestParam(value = "shareOpenId", required = false) String shareOpenId,
                                     @RequestParam(value = "businessNo", required = false) String businessNo,
                                     @RequestParam(value = "unionId", required = false) String unionId,
                                     @RequestParam(value = "appType") String type,
                                     HttpSession session
    ) {
        // appType校验
        if (AppTypeEnum.filter(type) == null
                && StringUtils.isBlank(unionId) && StringUtils.isBlank(phone)) {
            return JsonResult.error(Code.CHECK_ERROR, "app类型错误");
        }

        // 判断当前账号是否存在
        Customer customer = customerService.getCustomerByPhoneNumber(phone);
        if (customer == null) {
            customer = customerService.getCustomerByUnionId(unionId);
        }
        if (customer != null) {
            customer.setCustomerName(EmojiParser.removeAllEmojis(customerName));
            customer.setHeaderImage(headerImage);
            customer.setSex(sex);
            customer.setUnionId(unionId);
            // 更新用户信息
            customerService.updateCustomer(customer);
            // 更新用户openid
            if (StringUtils.isNotBlank(openId)) {
                customerOpenIdService.saveOrUpdate(new CustomerOpenId(customer, openId, type));
            }
            return JsonResult.success("重新绑定成功");
        } else {
            // 获取session中的验证码
            VerificationCodeRecord verificationCodeRecord = (VerificationCodeRecord) session.getAttribute("verificationCode");
            if (verificationCodeRecord == null || verificationCode == null || !verificationCode.equals(verificationCodeRecord.getVerificationCode())) {
                return JsonResult.error(Code.CHECK_ERROR, "验证码错误！");
            }
            // 清除session中的二维码
            session.removeAttribute("verificationCode");

            Date now = new Date();
            customer = new Customer(phone, EmojiParser.removeAllEmojis(customerName), headerImage, phone, sex);
            customer.setCustomerNo(StringUtils.getUuid());
            customer.setRegistrationDate(DateUtil.format(now));
            customer.setLastLogintime(DateUtil.format(now, DateUtil.FORMAT_FULL));
            customer.setRegistrationTime(DateUtil.format(now, DateUtil.FORMAT_TIME));
            customer.setUnionId(unionId);

            // 是否被拉入的黑名单
            BlackList blackList = blackListService.getByPhone(phone);
            if (blackList != null) {
                return JsonResult.error(Code.CHECK_ERROR, "无法注册！");
            }

            // 如果自己就是站点，不设置分享人
            Station regStation = stationService.getByPhone(phone);
            // 设置分享人
            if (regStation == null && !StringUtils.isBlank(shareOpenId)) {
                // 获取站点信息
                Station station = stationService.getByAdminOpenId(shareOpenId);
                if (station != null) {
                    customer.setShareStation(station);
                } else {
                    // 获取商家信息
                    Business business = businessService.getBusinessByPhone(customer.getPhone(), BusinessStateEnum.NORMAL);
                    if (business != null) {
                        customer.setShareBusiness(business);
                    }
                }
            }

            // 商家分享(不能同时属于两个分享人)
            if (!StringUtils.isBlank(businessNo) && customer.getShareStation() == null) {
                // 获取站点信息
                Business business = businessService.getByBusinessNo(businessNo, BusinessStateEnum.NORMAL);
                if (business != null) {
                    customer.setShareBusiness(business);
                }
            }

            // 新增一条数据
            customerService.insetCustomer(customer);

            // openid绑定
            if (StringUtils.isNotBlank(openId)) {
                customerOpenIdService.saveOrUpdate(new CustomerOpenId(customer, type, openId));
            }

            return JsonResult.success("注册成功");
        }
    }
}