package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderMedia;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName OrderMediaMapper
 * @Description 订单媒体
 * @Author LiuXiangLin
 * @Date 2019/7/22 14:41
 * @Version 1.0
 **/
@Mapper
public interface OrderMediaMapper {
    /**
     * @Author LiuXiangLin
     * @Description 添加一天数据
     * @Date 14:49 2019/7/22
     * @Param [orderMedia]
     * @return int
     **/
    int addAMedia(@Param("orderMedia") OrderMedia orderMedia);

    /**
     * @Author LiuXiangLin
     * @Description  添加多条数据
     * @Date 15:04 2019/8/13
     * @Param [orderMediaList]
     * @return int
     **/
    int addMediaList(@Param("orderMediaList") List<OrderMedia> orderMediaList);
    
    /**
     * @Author LiuXiangLin
     * @Description 通过地址获取图片或者视频
     * @Date 15:55 2019/9/21
     * @Param [address]
     * @return com.jxywkj.application.pet.model.consign.OrderMedia
     **/
    OrderMedia getByAddress(@Param("address") String address);

    /**
     * @Author LiuXiangLin
     * @Description 更新媒体对象的SN
     * @Date 16:32 2019/9/21
     * @Param [orderMedia]
     * @return int
     **/
    int updateOrderMediaSn(@Param("orderMedia")OrderMedia orderMedia);
}
