package com.jxywkj.application.pet.business;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.RegexUtils;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.common.VerificationCodeRecord;
import com.jxywkj.application.pet.service.facade.business.VerificationCodeService;
import com.jxywkj.application.pet.service.spring.business.VerificationCodeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 验证码
 *
 * @author LiuXiangLin
 * @date 17:08 2019/10/30
 **/
@RestController
@RequestMapping("/business/VerificationCode")
public class VerificationCodeController {
    @Resource
    RegexUtils regexUtils;

    @Resource
    VerificationCodeService verificationCodeService;

    /**
     * 验证码ATTRIBUTE
     */
    public static final String SESSION_VERIFICATION_CODE_ATTRIBUTE = "verificationCode";

    @GetMapping("")
    public JsonResult getVerificationCode(@RequestParam("phoneNumber") String phone, HttpServletRequest httpServletRequest) throws Exception {
        // 电话号码判断
        if (!regexUtils.isPhone(phone)) {
            return JsonResult.error(Code.ERROR_PHONE, Code.ERROR_PHONE.getMessage());
        }

        // 发送的验证码
        String code = verificationCodeService.sendVerificationCode(phone);

        if (StringUtils.isBlank(code)) {
            return JsonResult.error(Code.CHECK_ERROR, "短信发送失败！");
        }

        // 将验证码放入session中（在没有使用redis的情况下这是最方便的方法）
        httpServletRequest.getSession().setAttribute(SESSION_VERIFICATION_CODE_ATTRIBUTE, new VerificationCodeRecord(phone, code));

        // 记录本次请求的时间
        VerificationCodeServiceImpl.VERIFICATION_CODE_MAP.put(phone, new Date());

        return JsonResult.success();
    }
}
