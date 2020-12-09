package com.jxywkj.application.pet.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 验证码记录
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className VerificationCodeRecord
 * @date 2019/11/21 10:22
 **/
@Data
public class VerificationCodeRecord implements Serializable {
    private static final long serialVersionUID = 7352427156985383844L;
    /**
     * 电话号码
     */
    String phone;

    /**
     * 验证码
     */
    String verificationCode;

    public VerificationCodeRecord(String phone, String verificationCode) {
        this.phone = phone;
        this.verificationCode = verificationCode;
    }
}
