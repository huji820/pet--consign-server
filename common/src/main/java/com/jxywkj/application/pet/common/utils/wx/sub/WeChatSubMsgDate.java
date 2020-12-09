package com.jxywkj.application.pet.common.utils.wx.sub;

import java.util.Map;

/**
 * <p>
 * 微信订阅消息数据对象
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className WeChatSubMsgDate
 * @date 2020/6/5 10:47
 **/
public class WeChatSubMsgDate {
    /**数据对象*/
    private Map<String, InnerData> dateMap;
    /**用户openid*/
    private String openId;
    /**模板id*/
    private String templateId;

    public WeChatSubMsgDate(Map<String, InnerData> dateMap, String openId, String templateId) {
        this.dateMap = dateMap;
        this.openId = openId;
        this.templateId = templateId;
    }

    public WeChatSubMsgDate() {
    }

    public Map<String, InnerData> getDateMap() {
        return dateMap;
    }

    public void setDateMap(Map<String, InnerData> dateMap) {
        this.dateMap = dateMap;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
