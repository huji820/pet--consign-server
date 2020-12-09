package com.jxywkj.application.pet.business.task;

import com.jxywkj.application.pet.service.spring.business.VerificationCodeServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName VerificationCodeTask
 * @Description 清除清除验证码的定时器
 * @Author LiuXiangLin
 * @Date 2019/8/30 11:43
 * @Version 1.0
 **/
@Component
@Configuration
@EnableScheduling
public class VerificationCodeTask {

    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanVerificationCodeMap() {
        VerificationCodeServiceImpl.VERIFICATION_CODE_MAP.clear();
    }
}
