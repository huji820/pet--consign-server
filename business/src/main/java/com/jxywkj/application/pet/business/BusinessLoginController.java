package com.jxywkj.application.pet.business;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.common.VerificationCodeRecord;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.consign.StationMessageService;
import com.jxywkj.application.pet.service.spring.business.VerificationCodeServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName BusinessLoginController
 * @Description: 商户端的登录
 * @Author Aze
 * @Date 2019/8/14 11:48
 * @Version 1.0
 **/
@RestController
@RequestMapping("/business")
public class BusinessLoginController {
    @Resource
    BusinessService businessService;

    @Resource
    StationMessageService stationMessageService;

    @PostMapping("/toLoginBusiness")
    public JsonResult login(@RequestBody Business business, HttpSession session) {
        //管理员登陆操作
        Business businessLogin = businessService.login(business);
        if (businessLogin != null) {
            session.setAttribute("business", businessLogin);
            return JsonResult.success("/business/indexPage");
        }
        //返回的是请求
        return JsonResult.error(Code.ACCOUNT_ERROR, Code.ACCOUNT_ERROR.getMessage());
    }

    @PostMapping("/insetBusiness")
    public JsonResult insetBusiness(@RequestBody Business business, HttpServletRequest httpServletRequest) {
        // 获取session中的验证码
        VerificationCodeRecord verificationCodeRecord = (VerificationCodeRecord) (httpServletRequest.getSession().getAttribute("verificationCode"));

        if (verificationCodeRecord == null || StringUtils.isBlank(verificationCodeRecord.getVerificationCode())) {
            return JsonResult.error(Code.CHECK_ERROR, "验证码超时无效");
        }

        // 如果已存在 则不能重复注册
        Business businessLogin = businessService.getBusinessByPhone(business.getContactPhone(), null);
        if (businessLogin != null) {
            return JsonResult.error(Code.NAME_ALREADY_EXISTS, "该账号已被注册");
        } else {
            if (!StringUtils.isBlank(verificationCodeRecord.getVerificationCode()) && verificationCodeRecord.getVerificationCode().equals(business.getVerificationCode())) {
                business.setState(BusinessStateEnum.UNAUDITED.getType());
                int result = businessService.registerBusiness(business);
                if (result <= 0) {
                    return JsonResult.error(Code.INSERT_ERROR, "注册失败！");
                }
                // 清除验证码数据
                VerificationCodeServiceImpl.VERIFICATION_CODE_MAP.remove(business.getContactPhone());
                // 清除session中的验证码
                httpServletRequest.getSession().removeAttribute("verificationCode");
                // 将商家对象放入到session中
                httpServletRequest.getSession().setAttribute("business", business);
                // 给这个城市所有的站点发送一条推送
                stationMessageService.saveApplyBusinessMessage(business);

                return JsonResult.success("/business/indexPage");
            }
            return JsonResult.error(Code.CHECK_ERROR, "验证码错误！");
        }
    }

}
