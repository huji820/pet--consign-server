package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderState;
import com.jxywkj.application.pet.model.consign.OrderTransport;
import com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName OrderStateService
 * @Description 订单状态
 * @Author LiuXiangLin
 * @Date 2019/7/22 15:23
 * @Version 1.0
 **/
public interface OrderStateService {

    List<OrderState> listOrderState(String orderNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 获取该订单的状态数
     * @Date 16:52 2019/7/22
     * @Param [orderNo]
     **/
    int countByOrderNo(String orderNo);

    /**
     * @return List<OrderState>
     * @Author zhouxiaojian
     * @Description 查询该订单的所有的位置数据
     * @param orderNo
     * @Date 9:02 2020/7/23
     */
    List<OrderState> selectByOrderNo(String orderNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 上传多个媒体图片
     * @Date 10:05 2019/8/8
     * @Param [orderNo, orderStateNo, multipartFiles]
     **/
    List<OrderStateTempFiles> uploadOrderMedia(String orderNo, MultipartFile[] multipartFiles) throws IOException;


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 11:23 2019/8/9
     * @Param [orderState]
     **/
    int saveOrderState(OrderState orderState);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 入港操作
     * @Date 10:56 2019/8/13
     * @Param [filesAddress, orderNo, sn]
     **/
    int addInPortState(String[] filesAddress, String orderType, String orderNo, int sn);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description
     * @Date 15:07 2019/8/16
     * @Param [orderNo]
     **/
    int addPayState(String orderNo);


    /**
     * @return com.jxywkj.application.pet.model.consign.OrderState
     * @Author LiuXiangLin
     * @Description 获取订单最后一个订单状态
     * @Date 11:25 2019/8/23
     * @Param [orderNo]
     **/
    OrderState getLastOrderState(String orderNo);


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles>
     * @Author LiuXiangLin
     * @Description 添加数据
     * @Date 11:03 2019/9/21
     * @Param [orderNo, multipartFiles]
     **/
    List<OrderStateTempFiles> addOrderMedia(String orderNo, MultipartFile[] multipartFiles,String node, Long delayTime, String remarks) throws IOException;

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条待派件
     * @Date 13:51 2019/9/24
     * @Param [orderState]
     **/
    int addDelivering(String orderNo);

    /**
     * <p>
     * 更新飞常准对接状态
     * </p>
     *
     * @param order                  订单对象
     * @param pushFlightResponseData 飞常准推送对象
     * @return int
     * @author LiuXiangLin
     * @date 14:01 2019/12/11
     **/
    int updateVariflightState(Order order, PushFlightResponseData pushFlightResponseData);
}
