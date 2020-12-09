package com.jxywkj.application.pet.common.utils;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yangwang.sysframework.sms.SmsRequestBody;
import com.yangwang.sysframework.sms.SmsUtil;
import com.yangwang.sysframework.sms.TemplateData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AliSmsUtils
 * @date 2020/5/18 11:54
 **/
@Component
public class AliSmsUtils {
    @Resource
    SmsUtil smsUtil;

    @Value("${aliyun.sms.signName}")
    String signName;

    /**
     * <p>
     * 发送短信
     * </p>
     *
     * @param templateData 数据
     * @param phone        电话号码
     * @param templateNo   模板ID
     * @return void
     * @author LiuXiangLin
     * @date 11:56 2020/5/18
     **/
    public void sendSms(TemplateData templateData, String phone, String templateNo) throws Exception {
        SmsRequestBody smsRequestBody = new SmsRequestBody();
        smsRequestBody.setPhone(phone);
        smsRequestBody.setSignName(signName);
        smsRequestBody.setTemplateNo(templateNo);
        smsRequestBody.setTemplateData(templateData);

        boolean  bol = smsUtil.sendSms(smsRequestBody);

    }
}
