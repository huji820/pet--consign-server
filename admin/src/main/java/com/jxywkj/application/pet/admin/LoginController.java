package com.jxywkj.application.pet.admin;

import com.jxywkj.application.pet.business.VerificationCodeController;
import com.jxywkj.application.pet.model.admin.Manager;
import com.jxywkj.application.pet.model.common.VerificationCodeRecord;
import com.jxywkj.application.pet.service.facade.admin.ManagerService;
import com.yangwang.sysframework.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-06-19 21:25
 * @Version 1.0
 */
@Controller("adminLoginController")
@RequestMapping("admin")
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
    ManagerService managerService;


    /**
     * 使用验证码登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ModelAndView verificationLogin(String username, String verificationCode, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        // 获取session中的对象
        /*VerificationCodeRecord verificationCodeRecord =
                (VerificationCodeRecord) session.getAttribute(VerificationCodeController.SESSION_VERIFICATION_CODE_ATTRIBUTE);

        // 清除session中的数据
        session.removeAttribute(VerificationCodeController.SESSION_VERIFICATION_CODE_ATTRIBUTE);
        // session中的数据为空
        if (verificationCodeRecord == null || !StringUtil.isNotNull(verificationCodeRecord.getVerificationCode()) || !StringUtil.isNotNull(verificationCodeRecord.getPhone())) {
            // 验证码为空
            mv.setViewName("redirect:/admin/toLoginPage?errMsg=" + ERROR_NULL_VERIFICATION);
            return mv;
        }

        // 手机号码或者验证码不匹配
        if (!verificationCodeRecord.getPhone().equals(username) || !verificationCodeRecord.getVerificationCode().equals(verificationCode)) {
            mv.setViewName("redirect:/admin/toLoginPage?errMsg=" + ERROR_WRONG_VERIFICATION);
            return mv;
        }*/
        // 通过电话号码查询数据
        Manager manager = managerService.getManager(username);
        if (manager == null) {
            mv.setViewName("redirect:/admin/toLoginPage?errMsg=" + ERROR_ACCOUNT_WRONG);
            return mv;
        }
        // 登录成功
        session.setAttribute("manager", manager);
        mv.setViewName("redirect:/admin/control");

        return mv;
    }

    @GetMapping("control")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("manager/index");
        return mv;
    }


    @GetMapping("logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        session.removeAttribute("manager");
        mv.setViewName("redirect:/admin/toLoginPage");
        return mv;
    }
}

