package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.StringUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.utils.network.HttpUtil;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GaoDeMapUtils
 * @Description 高德地图工具类
 * @Author LiuXiangLin
 * @Date 2019/7/26 10:18
 * @Version 1.0
 **/
@Component
public class GaoDeMapUtils {

    @Value("${gaode.key}")
    String gaoDeKey;

    @Resource
    HttpUtil httpUtil;


    // 地理编码地址
    private static final String GEO_CODING_BASE_URL = "https://restapi.amap.com/v3/geocode/geo";
    // 开车路径规划
    private static final String DrIVER_PLANNING_BASE_RUL = "https://restapi.amap.com/v3/direction/driving";


    /**
     * @return java.math.BigDecimal
     * @Author LiuXiangLin
     * @Description 计算两个经纬度之间的距离
     * @Date 10:25 2019/7/26
     * @Param [startLonAndLat, endLonAndLat]
     **/
    public BigDecimal getDistance(LonAndLat startLonAndLat, LonAndLat endLonAndLat) throws Exception {
        String url = DrIVER_PLANNING_BASE_RUL + "?key=" + gaoDeKey + "&origin=" + startLonAndLat.getStringLonAndLat()
                + "&destination=" + endLonAndLat.getStringLonAndLat() + "&strategy=0&extensions=base";

        // 发送请求
        Response response = httpUtil.get(url);

        Map<String, Object> path = JsonUtil.formObject(response.body().string(), Map.class);
        List<Map<String, Object>> paths = (ArrayList) ((HashMap) path.get("route")).get("paths");

        return TypeConvertUtil.$BigDecimal(paths.get(0).get("distance"));
    }


    /**
     * @return com.jxywkj.application.pet.model.consign.params.LonAndLat
     * @Author LiuXiangLin
     * @Description 通过地理编码获取经纬度
     * @Date 10:44 2019/7/26
     * @Param [address]
     **/
    public LonAndLat getLonAndLat(String address) throws Exception {
        // 发送请求并获取返回数据
        Response response = httpUtil.get(GEO_CODING_BASE_URL + "?address=" + address + "&key=" + gaoDeKey);
        // 将请求数据转换为Map集合
        Map<String, Object> msg = JsonUtil.formObject(response.body().string(), Map.class);
        // 获取返回的数据
        List<Map<String, Object>> geocodes = ((ArrayList<Map<String, Object>>) msg.get("geocodes"));

        if (geocodes == null || geocodes.size() == 0) {
            throw new RuntimeException("获取地址位置失败！");
        }

        // 获取经纬度
        String lonAndLat = StringUtil.$Str(((ArrayList<Map<String, Object>>) msg.get("geocodes")).get(0).get("location"));

        // 将经纬度拆成数组
        String[] lonAndLatArray = lonAndLat.split(",");

        return new LonAndLat(lonAndLatArray[0], lonAndLatArray[1]);
    }
}
