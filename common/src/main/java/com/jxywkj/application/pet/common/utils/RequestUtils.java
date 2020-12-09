package com.jxywkj.application.pet.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * request请求工具类
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className RequestUtils
 * @date 2020/1/4 17:37
 **/
public class RequestUtils {
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        try {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (NullPointerException e) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
