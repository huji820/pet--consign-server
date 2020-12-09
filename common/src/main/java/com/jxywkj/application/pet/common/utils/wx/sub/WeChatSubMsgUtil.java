package com.jxywkj.application.pet.common.utils.wx.sub;

import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.network.HttpUtil;
import com.yangwang.sysframework.wechat.boot.TokenUtil;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 微信订阅消息工具类
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className WeChatSubMsgUtil
 * @date 2020/6/5 10:48
 **/
@Component
public class WeChatSubMsgUtil {
    @Resource
    TokenUtil tokenUtil;

    @Resource
    HttpUtil httpUtil;

    /**
     * 请求链接
     */
    private static final String REQUEST_URI = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

    /**
     * <p>
     * 发送订阅消息
     * </p>
     *
     * @param weChatSubMsgDate 数据对象
     * @return void
     * @author LiuXiangLin
     * @date 10:53 2020/6/5
     **/
    public void sendMsg(WeChatSubMsgDate weChatSubMsgDate) throws Exception {
        if (weChatSubMsgDate == null) {
            throw new RuntimeException("模板数据对象为空！");
        }

        // 获取token
        String token = tokenUtil.getToken();

        // 拼接成链接
        String requestRui = REQUEST_URI + "?access_token=" + token;

        Map<String, Object> requestBodyParam = new HashMap<>(3);
        requestBodyParam.put("touser", weChatSubMsgDate.getOpenId());
        requestBodyParam.put("template_id", weChatSubMsgDate.getTemplateId());
        requestBodyParam.put("data", weChatSubMsgDate.getDateMap());

        httpUtil.postRequestBody(requestRui, requestBodyParam);
    }
}
