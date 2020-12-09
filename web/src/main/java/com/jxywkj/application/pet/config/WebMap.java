package com.jxywkj.application.pet.config;

import com.jxywkj.application.pet.interceptor.AdminSessionInterceptor;
import com.jxywkj.application.pet.interceptor.ConsignSessionInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hj
 * @date 2018/10/18
 */
@Configuration
public class WebMap implements WebMvcConfigurer {

    @Value("${file.load-Path}")
    private String loadPath;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 首页
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/encyclopedias").setViewName("encyclopedias");

        registry.addViewController("/consign/toLoginPage").setViewName("consign/login");
        registry.addViewController("/consign/toMap").setViewName("consign/center");
        registry.addViewController("/consign/toTransport").setViewName("consign/transport");
        registry.addViewController("/consign/toOrder").setViewName("consign/order");
        registry.addViewController("/consign/toStaff").setViewName("consign/staff");
        registry.addViewController("/consign/toStaff_add").setViewName("consign/staff_add");
        registry.addViewController("/consign/toSettlement").setViewName("consign/settlement");
        registry.addViewController("/consign/toPartner").setViewName("consign/partner");
        registry.addViewController("/consign/toEdit").setViewName("consign/edit");
        registry.addViewController("/consign/index").setViewName("consign/index");
        registry.addViewController("/consign/transportConfig").setViewName("consign/transport_config");
        registry.addViewController("/consign/petCageConfig").setViewName("consign/pet_cage_config");
        registry.addViewController("/consign/supplementPrice").setViewName("consign/supplement_price");
        registry.addViewController("/consign/pickUpPet").setViewName("consign/order_assignment");
        registry.addViewController("/consign/blacklistConfig").setViewName("consign/blacklist_config");
        registry.addViewController("/consign/toOrderLedger").setViewName("consign/ledger");
        registry.addViewController("/consign/toFlow").setViewName("consign/flow");
        registry.addViewController("/consign/toPosition").setViewName("consign/position");
        registry.addViewController("/consign/batchTransport").setViewName("consign/batch_transport");


        /*商户后端*/
        registry.addViewController("/business/loginPage").setViewName("business/login");
        registry.addViewController("/business/indexPage").setViewName("business/business_index");
        registry.addViewController("/business/couponPage").setViewName("business/coupon");
        registry.addViewController("/business/detailsPage").setViewName("business/details");

        /*后台端*/
        registry.addViewController("/admin/toLoginPage").setViewName("manager/login");
        registry.addViewController("/manager/index").setViewName("manager/index");
        registry.addViewController("/manager/welcome").setViewName("manager/welcome");
    }


    /**
     * @param registry 全局的session拦截器
     * @author hj
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ConsignSessionInterceptor())
                .addPathPatterns("/consign/**").addPathPatterns("/business/**")
                .excludePathPatterns("/404", "/static/**", "swagger-ui",
                        "/consign/toLoginPage", "/consign/logout", "/consign/login", "/consign/verificationLogin",
                        "/consign/index", "/out/**/**",
                        "/pay/callback/*", "/business/loginPage", "/toLoginBusiness", "/business/VerificationCode/**",
                        "/business/toLoginBusiness", "/business/insetBusiness")
                .excludePathPatterns("/swagger-resources/**", AppConfig.resourceName + "**");

        registry.addInterceptor(new AdminSessionInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/404", "/admin/toLoginPage", "/admin/login");
    }

    /**
     * Swagger2和图片资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/*").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler(SystemConfig.resourceName + "**").addResourceLocations(loadPath);
    }

}
