package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderMedia;

import java.util.List;

/**
 * @ClassName OrderMediaService
 * @Description 订单媒体Service
 * @Author LiuXiangLin
 * @Date 2019/7/22 16:59
 * @Version 1.0
 **/
public interface OrderMediaService {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 17:17 2019/7/22
     * @Param [orderMedia]
     **/
    int addAMedias(OrderMedia orderMedia);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加多条数据
     * @Date 15:03 2019/8/13
     * @Param [orderMediaList]
     **/
    int addAMediaList(List<OrderMedia> orderMediaList);


    /**
     * @return com.jxywkj.application.pet.model.consign.OrderMedia
     * @Author LiuXiangLin
     * @Description 通过图片地址查询图片或者视频
     * @Date 15:54 2019/9/21
     * @Param [address]
     **/
    OrderMedia getByAddress(String address);

    /**
     * @Author LiuXiangLin
     * @Description 修改对象的SN
     * @Date 16:27 2019/9/21
     * @Param [orderMedia]
     * @return int
     **/
    int updateOrderMediaSn(OrderMedia orderMedia);
}
