package com.jxywkj.application.pet.interceptor;

import com.jxywkj.application.pet.model.admin.Manager;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.consign.Staff;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-07-03 0:07
 * @Version 1.0
 */
public class AdminSessionInterceptor implements HandlerInterceptor {
    private static final String CONSING_START_STR = "/admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Manager manager = (Manager) request.getSession().getAttribute("manager");
        /*if (manager == null && manager == null) {
            response.sendRedirect("/admin/toLoginPage");
            return false;
        }*/
        return true;
    }
}
