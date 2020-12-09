package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.business.VerificationCodeController;
import com.jxywkj.application.pet.common.utils.RequestUtils;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.common.VerificationCodeRecord;
import com.jxywkj.application.pet.model.consign.LoginLog;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.LoginLogService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import com.yangwang.sysframework.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-06-19 21:25
 * @Version 1.0
 */
@Controller("consignLoginController")
public class LoginController {

    /**
     * 账号密码错误
     */
    private static final String ERROR_ACCOUNT_WRONG = "1";

    /**
     * 验证码为空
     */
    private static final String ERROR_NULL_VERIFICATION = "2";

    /**
     * 验证码错误
     */
    private static final String ERROR_WRONG_VERIFICATION = "3";
    @Value("${amap.key}")
    String key;

    @Autowired
    StaffService staffService;

    @Autowired
    LoginLogService loginLogService;

    /**
     * 使用账号密码登录
     */
    @PostMapping("/consign/login")
    @ApiOperation(value = "登录")
    public ModelAndView login(String username, String pwd, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Staff staff = staffService.getStaffByPhoneAndPwd(username, pwd);
        if (staff != null) {
            session.setAttribute("staff", staff);
            mv.setViewName("redirect:/consign/index");
        } else {
            //当登陆失败后,重定向回根目录并返回一个账号或密码错误状态参数cpbh
            mv.setViewName("redirect:/consign/toLoginPage?errMsg=" + ERROR_ACCOUNT_WRONG);
        }
        return mv;
    }

    /**
     * 使用验证码登录
     */
    @PostMapping("/consign/verificationLogin")
    @ApiOperation(value = "登录")
    public ModelAndView verificationLogin(String username, String verificationCode, HttpSession session, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // 获取session中的对象
        VerificationCodeRecord verificationCodeRecord =
                (VerificationCodeRecord) session.getAttribute(VerificationCodeController.SESSION_VERIFICATION_CODE_ATTRIBUTE);

        // session中的数据为空
//        if (verificationCodeRecord == null || StringUtils.isBlank(verificationCodeRecord.getVerificationCode()) || StringUtils.isBlank(verificationCodeRecord.getPhone())) {
//            // 验证码为空
//            mv.setViewName("redirect:/consign/toLoginPage?errMsg=" + ERROR_NULL_VERIFICATION);
//            return mv;
//        }
//
//        // 手机号码或者验证码不匹配
//        if (!verificationCodeRecord.getPhone().equals(username) || !verificationCodeRecord.getVerificationCode().equals(verificationCode)) {
//            mv.setViewName("redirect:/consign/toLoginPage?errMsg=" + ERROR_WRONG_VERIFICATION);
//            return mv;
//        }
        // 清除session中的验证码
        session.removeAttribute(VerificationCodeController.SESSION_VERIFICATION_CODE_ATTRIBUTE);
        // 通过电话号码查询数据
        Staff staff = staffService.verificationLogin(username);
        if (staff == null) {

            mv.setViewName("redirect:/consign/toLoginPage?errMsg=" + ERROR_ACCOUNT_WRONG);
            return mv;
        }

        String ipAddress = RequestUtils.getIpAddress(request);

        LoginLog loginLog = new LoginLog();

        loginLog.setLoginHost(ipAddress);
        loginLog.setLoginTime(DateUtil.formatFull(new Date()));
        loginLog.setStaff(staff);
        loginLog.setStation(staff.getStation());

        loginLogService.insertLoginLog(loginLog);
        // 登录成功
        session.setAttribute("staff", staff);
        mv.setViewName("redirect:/consign/index");


        return mv;
    }

    @GetMapping("")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        session.removeAttribute("staff");
        mv.setViewName("redirect:/consign/toLoginPage");
        return mv;
    }

    /**
     * 用于开发的接口，上线之前记得注释或者删除
     */
    @GetMapping("/testLogin")
    public ModelAndView testLogin(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        // 通过电话号码查询数据
        Staff staff = staffService.verificationLogin("15879067924");
        session.setAttribute("staff", staff);
        mv.setViewName("redirect:/consign/index");
        return mv;
    }
}
