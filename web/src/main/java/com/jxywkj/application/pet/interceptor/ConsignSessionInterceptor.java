package com.jxywkj.application.pet.interceptor;

import com.jxywkj.application.pet.common.utils.StringUtils;
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
public class ConsignSessionInterceptor implements HandlerInterceptor {
    private static final String CONSING_START_STR = "/consign";
    private static final String BUSINESS_START_STR = "/business";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        Business business = (Business) request.getSession().getAttribute("business");
        if (staff == null && StringUtils.startWith(request.getServletPath(), CONSING_START_STR)) {
            response.sendRedirect("/consign/toLoginPage");
            return false;
        }
        if (business == null && StringUtils.startWith(request.getServletPath(), BUSINESS_START_STR)) {
            response.sendRedirect("/business/loginPage");
            return false;
        }
        if (staff == null && business == null) {
            response.sendRedirect("/consign/toLoginPage");
            return false;
        }

        return true;
    }
}
