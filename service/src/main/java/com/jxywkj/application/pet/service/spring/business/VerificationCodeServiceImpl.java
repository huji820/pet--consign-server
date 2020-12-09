package com.jxywkj.application.pet.service.spring.business;

import com.jxywkj.application.pet.common.utils.AliSmsUtils;
import com.jxywkj.application.pet.service.facade.business.VerificationCodeService;
import com.yangwang.sysframework.sms.SmsRequestBody;
import com.yangwang.sysframework.sms.SmsUtil;
import com.yangwang.sysframework.sms.TemplateData;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName VerificationCodeServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/8/30 9:30
 * @Version 1.0
 **/
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    /**
     * 短信验证码最近一次使用时间
     */
    public static final Map<String, Date> VERIFICATION_CODE_MAP = new ConcurrentHashMap<>();

    /***/
    private static final BigDecimal ONE_MIN_MILS = BigDecimal.valueOf(60000);

    @Resource
    AliSmsUtils aliSmsUtils;

    @Override
    public String sendVerificationCode(String phone) throws Exception {
        // 获取一个随机数
        String code = String.valueOf(new Random().nextInt(8999) + 1000);

        TemplateData templateData = new TemplateData();
        templateData.put("code", code);

        // 发送短信
        aliSmsUtils.sendSms(templateData, phone, "SMS_182672504");

        return code;

    }


    /**
     * @return boolean
     * @Author LiuXiangLin
     * @Description 是否相隔了一分钟
     * @Date 10:03 2019/8/30
     * @Param [beforDate, afterDate]
     **/
    private boolean differOnMin(Date beforeDate, Date afterDate) {
        if (beforeDate == null) {
            return true;
        }
        return TypeConvertUtil.$BigDecimal(afterDate.getTime()).subtract(TypeConvertUtil.$BigDecimal(beforeDate.getTime())).abs().compareTo(ONE_MIN_MILS) > 0;
    }
}
