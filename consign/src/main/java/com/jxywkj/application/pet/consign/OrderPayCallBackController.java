package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.service.facade.consign.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName orderPayCallBackController
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/7/18 9:07
 * @Version 1.0
 **/
@RestController
@RequestMapping("/pay/callback")
public class OrderPayCallBackController {
    @Resource
    OrderCallBackService orderCallBackService;


    private static final String WE_CHAT_SUCCESS_CALL_BACK_RETURN =
            "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[支付回调已收到]]></return_msg></xml>";

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 1.回写订单支付状态
     * 2.向寄宠人发送微信模板消息/短信消息(如果寄宠人的手机号码，存在于客户表中，就发送模板消息，如果不存在，则发送手机短消息)
     * 3.向收宠人发送微信模板消息/短信消息(如果寄宠人的手机号码，存在于客户表中，就发送模板消息，如果不存在，则发送手机短消息)
     * 4.向station对象的联系人发送微信模板消息/短信消息(如果寄宠人的手机号码，存在于客户表中，就发送模板消息，如果不存在，则发送手机短消息)
     * 5.定向收宠人发送一张服务券(比如：狗狗洗澡券)，券的关联关系为手机号,如果收宠人还没注册，发送手机短信消息，通知收宠物注册会员，一但客户注册完成，提示有券未使用
     * 备注：短信，微信模板消息都有模板编号
     * @Date 9:10 2019/7/18
     * @Param []
     **/
    @RequestMapping("transport/{orderNo:[\\w]+}")
    public String orderCallBack(@PathVariable("orderNo") String orderNo) throws Exception {
        orderCallBackService.payConsignOrder(orderNo);
        return WE_CHAT_SUCCESS_CALL_BACK_RETURN;
    }

    /**
     * <p>
     * 充值回调地址
     * </p>
     *
     * @param orderNo 充值单号
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 8:44 2020/1/2
     **/
    @RequestMapping("recharge/{orderNo:[\\w]+}")
    public String rechargeBallBack(@PathVariable("orderNo") String orderNo) {
        orderCallBackService.payRechargeOrder(orderNo);
        return WE_CHAT_SUCCESS_CALL_BACK_RETURN;
    }

    /**
     * <p>
     * 补价单回调
     * </p>
     *
     * @param billNo 补价单单号
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 8:45 2020/1/2
     **/
    @RequestMapping("premium/{billNo:[\\w]+}")
    public String premiumCallBack(@PathVariable("billNo") String billNo) {
        orderCallBackService.payPremiumOrder(billNo);
        return WE_CHAT_SUCCESS_CALL_BACK_RETURN;
    }
}
