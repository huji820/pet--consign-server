package com.jxywkj.application.pet.common.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @ClassName RegexUtils
 * @Description 正则验证工具类
 * @Author LiuXiangLin
 * @Date 2019/8/30 9:15
 * @Version 1.0
 **/
@Component
public class RegexUtils {
    /**
     * 电话号码长度
     */
    private static final int PHONE_LENGTH = 11;

    /**
     * 电话号码正则
     */
    private static final String PHONE_REGEX = "^[1][3,4,5,6,7,8,9][0-9]{9}$";


    /**
     * @return boolean
     * @Author LiuXiangLin
     * @Description 手机号码正则
     * @Date 9:16 2019/8/30
     * @Param [phone]
     **/
    public boolean isPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        if (phone.length() != PHONE_LENGTH) {
            return false;
        }

        return Pattern.matches(PHONE_REGEX, phone);
    }

}
