package com.jxywkj.application.pet.api.wechat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信校验
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className WeChatCheckController
 * @date 2019/11/12 8:58
 **/
@RestController()
@RequestMapping("/weapp/jump/**")
public class WeChatCheckController {

    /**
     * 只要是/weapp/jump的子路径 则表示是普通二维码的跳转小程序 都要返回指定的校验文件
     */
    @GetMapping()
    public String getCheckFile() {
        return "f73547a37456980169ec08285cecaadf";
    }
}
