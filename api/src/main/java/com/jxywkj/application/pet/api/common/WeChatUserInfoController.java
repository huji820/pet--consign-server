package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.yangwang.sysframework.wechat.boot.WxUserInfoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 * 获取微信用户信息
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className WeChatUserInfoControler
 * @date 2020/2/29 14:59
 **/
@RestController
@RequestMapping("/api/wechat/userinfo")
@Api(description = "获取微信用户信息")
public class WeChatUserInfoController {
    @Resource
    WxUserInfoUtils wxUserInfoUtils;

    /**
     * <p>
     * 通过微信code获取用户信息
     * </p>
     *
     * @param code 微信登录code
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 15:08 2020/2/29
     **/
    @GetMapping("/open")
    @ApiOperation(value = "通过微信code获取用户信息")
    public JsonResult getUserInfoByCode(@RequestParam("code") String code) throws IOException {
        return JsonResult.success(wxUserInfoUtils.getUserOpenId(code));
    }

    /**
     * <p>
     * 通过数据获取unionId和具体的信息。
     * </p>
     *
     * @param encryptedData 加密数据
     * @param sessionKey    解密key
     * @param iv            偏移量
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 14:23 2020/3/3
     **/
    @GetMapping("/union")
    @ApiOperation(value = "通过参数获取用户信息（包含unionid）")
    public JsonResult getUserInfoByRawData(@RequestParam("encryptedData") String encryptedData,
                                           @RequestParam("sessionKey") String sessionKey,
                                           @RequestParam("iv") String iv) {
        return JsonResult.success(wxUserInfoUtils.decryptUserInfo(encryptedData, sessionKey, iv));
    }

    /**
     * <p>
     * 通过加密数据获取电话号码
     * </p>
     *
     * @param encryptedData 加密数据
     * @param sessionKey    解密key
     * @param iv            偏移量
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 14:26 2020/3/3
     **/
    @GetMapping("/phone")
    @ApiOperation(value = "通过加密数据获取电话号码")
    public JsonResult getUserPhone(@RequestParam("encryptedData") String encryptedData,
                                   @RequestParam("sessionKey") String sessionKey,
                                   @RequestParam("iv") String iv) {
        return JsonResult.success(wxUserInfoUtils.decryptUserInfo(encryptedData, sessionKey, iv).getPurePhoneNumber());
    }
}
