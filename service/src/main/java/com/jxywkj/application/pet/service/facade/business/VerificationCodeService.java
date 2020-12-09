package com.jxywkj.application.pet.service.facade.business;


import java.io.IOException;

/**
 * @ClassName VerificationCodeService
 * @Description 短信验证码
 * @Author LiuXiangLin
 * @Date 2019/8/30 8:56
 * @Version 1.0
 **/
public interface VerificationCodeService {
    
    /**
     * @Author LiuXiangLin
     * @Description 发送一条短信验证码
     * @Date 8:57 2019/8/30
     * @Param [phone]
     * @return String
     **/
    String sendVerificationCode(String phone) throws Exception;
}
