package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.common.CustomerMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName MessageMapper
 * @Description 站内信
 * @Author LiuXiangLin
 * @Date 2019/9/9 14:32
 * @Version 1.0
 **/
@Component
public interface CustomerMessageMapper {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条消息
     * @Date 14:33 2019/9/9
     * @Param [customerMessage]
     **/
    int saveAMessage(@Param("customerMessage") CustomerMessage customerMessage);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新一条数据
     * @Date 14:35 2019/9/9
     * @Param [messageId, messageStatus]
     **/
    int updateMessage(@Param("messageId") int messageId, @Param("messageStatus") String messageStatus);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 批量更新消息类型
     * @Date 14:36 2019/9/9
     * @Param [messageId, messageStatus]
     **/
    int updateMessageList(@Param("messageId") List<Integer> messageId, @Param("messageStatus") String messageStatus);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 批量更新所有的消息类型
     * @Date 14:37 2019/9/9
     * @Param [recId, messageType]
     **/
    int updateAllMessageByRecId(@Param("recId") String recId, @Param("messageType") String messageType);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 批量更新某个状态的所有消息为一个新的状态
     * @Date 14:39 2019/9/9
     * @Param [recId, oldMessageType, newMessageType]
     **/
    int updateAllMessageByRecIdAndType(@Param("recId") String recId,
                                       @Param("oldMessageType") String oldMessageType,
                                       @Param("newMessageType") String newMessageType);

    /**
     * @param [customerMessage, orderNo]
     * @return int
     * @author LiuXiangLin
     * @description 新增多条更新订单 发送给所有相关人员的站内信（员工）
     * @date 9:38 2019/10/14
     **/
    int saveMessageWithOrderAssigment(@Param("customerMessage") CustomerMessage customerMessage, @Param("orderNo") String orderNo);

    /**
     * @param [phone, lastGetTime, status]
     * @return int
     * @author LiuXiangLin
     * @description 通过收件人电话号码和最后更细获取以及状态查询总数
     * @date 15:41 2019/10/14
     **/
    int countByPhone(@Param("phone") String phone,
                     @Param("lastGetTime") String lastGetTime,
                     @Param("status") String status);

    /**
     * @param [phone, offset, limit]
     * @return java.util.List<com.jxywkj.application.pet.model.common.CustomerMessage>
     * @author LiuXiangLin
     * @description 通过电话号码查询数据
     * @date 15:45 2019/10/14
     **/
    List<CustomerMessage> listByPhone(@Param("phone") String phone,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);
}
