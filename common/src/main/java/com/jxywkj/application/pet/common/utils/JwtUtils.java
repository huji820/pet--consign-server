package com.jxywkj.application.pet.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className JwtUtils
 * @date 2020/3/3 10:53
 **/
@Component
public class JwtUtils {
    /**
     * 签名过期时间
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 私钥
     */
    private static final String TOKEN_SECRET = "jxywkj@0820";

    /**
     * <p>
     * 生成签名
     * </p>
     *
     * @param customerNo 用户编号
     * @param staffNo    员工编号
     * @param businessNo 商家编号
     * @param stationNo  站点编号
     * @param role       用户角色
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 11:24 2020/3/3
     **/
    public String sign(String customerNo, String staffNo, String businessNo, String stationNo, String role) {
        // 过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        // 生成签名
        return JWT.create()
                .withHeader(header)
                .withClaim("customerNo", customerNo)
                .withClaim("staffNo", staffNo)
                .withClaim("businessNo", businessNo)
                .withClaim("stationNo", stationNo)
                .withClaim("role", role)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * <p>
     * 验证token
     * </p>
     *
     * @param token 令牌
     * @return boolean
     * @author LiuXiangLin
     * @date 11:26 2020/3/3
     **/
    public boolean verity(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }
        return true;
    }

}
