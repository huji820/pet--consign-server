package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.dto.OrderUpdateDTO;
import com.jxywkj.application.pet.model.consign.params.StaffOrderQueryDTO;
import com.jxywkj.application.pet.model.consign.vo.OrderVO;
import com.jxywkj.application.pet.model.dto.OrderQueryDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ConsignOrderMapper
 * @Description: 托运订单Mapper
 * @Author Aze
 * @Date 2019/7/13 15:07
 * @Version 1.0
 **/
@Component
public interface ConsignOrderMapper {
    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 新增低订单
     * @Date 9:02 2019/8/8
     * @Param [order]
     **/

    void insertConsignOrder(@Param("order") Order order);


    void updateConsignOrder(Order order);


    /**
     * 获取订单
     *
     * @param orderNo
     * @return
     */
    Order getOrder(@Param("orderNo") String orderNo);


    /**
     * @return com.jxywkj.application.pet.model.consign.Order
     * @Author LiuXiangLin
     * @Description 通过订单号查询订单
     * @Date 9:03 2019/8/8
     * @Param [orderNo]
     **/
    Order getConsignOrderByOrderNo(@Param("orderNo") String orderNo);


    /**
     * @param startTime, endTime
     * @return int
     * @author LiuXiangLin
     * @description 查询一段时间内的订单数 都是大于等于小于等于操作
     * @date 16:12 2019/7/16
     **/
    int countOrderByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新订单状态
     * @Date 10:29 2019/7/18
     * @Param [orderNo, state]
     **/
    int updateOrderState(@Param("orderNo") String orderNo, @Param("state") String state);

    /**
     * 上传付款凭证
     * @param orderNo
     * @param paymentVoucher
     * @return
     */
    int uploadPaymentVoucher(@Param("orderNo")String orderNo,@Param("paymentVoucher")String paymentVoucher);

    /**
     * 审核付款凭证反馈
     * @param orderNo
     * @param reviewVoucherRemarks
     * @return
     */
    int updateReviewVoucherRemarks(@Param("orderNo")String orderNo,@Param("reviewVoucherRemarks")String reviewVoucherRemarks);

    int updateOrderPrice(@Param("orderNo") String orderNo, @Param("beforeAmount") BigDecimal beforeAmount,
                         @Param("afterAmount") BigDecimal afterAmount, @Param("orderState") String orderState);


    /**
     * <p>
     * 通过订单状态以及用户OPENID 查询订单列表
     * </p>
     *
     * @param customerNo 用户编号
     * @param state      状态
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 11:12 2020/3/5
     **/
    List<Order> listOrderByState(@Param("customerNo") String customerNo, @Param("state") String... state);


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 获取该站点的所有订单
     * @Date 9:38 2019/7/27
     * @Param [stationNo]
     **/
    List<Order> listOrderListByStationNo(@Param("stationNo") int stationNo);


    /**
     * @return com.jxywkj.application.pet.model.consign.Order
     * @Author LiuXiangLin
     * @Description 查询订单详情和订单状态（运输状态）
     * @Date 9:07 2019/8/8
     * @Param [orderNo]
     **/
    Order getOrderDetailWidthState(@Param("orderNo") String orderNom,
                                   @Param("showColumns") List<String> showColumns,
                                   @Param("staff") Staff staff);


    /**
     * <p>
     * 取消订单
     * </p>
     *
     * @param orderNo    订单编号
     * @param customerNo 用户编号
     * @param orderStat  修改后的订单状态
     * @return int
     * @author LiuXiangLin
     * @date 9:54 2020/3/5
     **/
    int updateOrderCancel(@Param("orderNo") String orderNo, @Param("customerNo") String customerNo, @Param("orderState") String orderStat);

    /**
     * 删除订单
     * @param orderNo
     * @return
     */
    int deleteOrderByOrderNo(@Param("orderNo")String orderNo);

    /**
     * 通过单号模糊查询单号
     *
     * @param staff   员工
     * @param orderNo 订单编号
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 17:27 2019/10/26
     **/
    String getStaffOrderNoByOrderNo(@Param("staff") Staff staff, @Param("orderNo") String orderNo);


    /**
     * <p>
     * 通过用户编号和订单类型查询订单列表
     * </p>
     *
     * @param customerNo 用户编号
     * @param orderType  订单类型
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 9:47 2020/3/5
     **/
    List<Order> listOrderByParamType(@Param("customerNo") String customerNo, @Param("orderType") String orderType);


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 通过城市名称和订单状态查询列表
     * @Date 15:54 2019/8/23
     * @Param [cityName, state, imgBaseUrl]
     **/
    List<Order> listByCityNameAndState(@Param("cityName") String cityName, @Param("state") String state);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新订单PrepayId
     * @Date 19:07 2019/8/26
     * @Param [orderNo, prepayId]
     **/
    int updateOrderPrepayId(@Param("orderNo") String orderNo, @Param("prepayId") String prepayId);


    /**
     * @return String
     * @Author LiuXiangLin
     * @Description 通过开始时间和城市名称查询最后的订单号
     * @Date 10:06 2019/8/27
     * @Param [startTime, endTime, city]
     **/
    String getLastOrderNoByTimeAndCity(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("city") String city);

    /**
     * <p>
     * 通过单号模糊查询单号列表
     * </p>
     *
     * @param customerNo 用户编号
     * @param orderNo    订单编号
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 9:58 2020/3/5
     **/
    String getCustomerOrderNoByOrderNo(@Param("customNo") String customerNo, @Param("orderNo") String orderNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 查询员工的所有经手的单子总计
     * @Date 9:47 2019/9/28
     * @Param [staff]
     **/
    int countAllStaffOrder(@Param("staff") Staff staff);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 查询员工的所有经手的单子总计
     * @Date 9:47 2019/9/28
     * @Param [staffOrderQueryDTO]
     **/
    List<Order> listAllStaffOrder(@Param("staffOrderQueryDTO") StaffOrderQueryDTO staffOrderQueryDTO);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 查询管理员订单总条数
     * @Date 11:08 2019/9/28
     * @Param [staff]
     **/
    int countAllAdminOrder(@Param("staff") Staff staff);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 查询管理员总数据
     * @Date 11:11 2019/9/28
     * @Param [staffOrderQueryDTO]
     **/
    List<Order> listAllAdminOrder(@Param("staffOrderQueryDTO") StaffOrderQueryDTO staffOrderQueryDTO);

    /**
     * @param orderUpdateDTO
     * @return int
     * @author LiuXiangLin
     * @description 更新订单联系人 如果有数据为空 则不不更新
     * @date 16:25 2019/10/10
     **/
    int updateContacts(@Param("orderUpdateDTO") OrderUpdateDTO orderUpdateDTO);

    /**
     * <p>
     * 通过订单号以及收货人编号查询是否存在
     * </p>
     *
     * @param orderNo    订单编号
     * @param customerNo 用户编号
     * @return java.lang.Integer 空值或者0 都表示不存在 1 表示存在
     * @author LiuXiangLin
     * @date 17:19 2019/11/8
     **/
    Integer getExistsByOrderNoAndReceiverNo(@Param("orderNo") String orderNo, @Param("customerNo") String customerNo);


    /**
     * <p>
     * 通过订单类型查询单号
     * </p>
     *
     * @param type      订单类型
     * @param afterTime 订单类型
     * @param offset    排除条数
     * @param limit     显示条数（为0则表示全部查询）
     * @return java.util.List<java.lang.String>
     * @author LiuXiangLin
     * @date 15:24 2019/11/21
     **/
    List<String> listOrderNoByType(@Param("type") String type, @Param("afterTime") String afterTime, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * <p>
     * 通过商家单号查单据
     * </p>
     *
     * @param outTradeNo 商家支付单号
     * @return com.jxywkj.application.pet.model.consign.Order
     * @author LiuXiangLin
     * @date 11:16 2019/11/25
     **/
    Order getOrderByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    /**
     * <p>
     * 通过订单类型查询商家订单号
     * </p>
     *
     * @param type      订单类型
     * @param afterTime 时间
     * @param offset    排除条数
     * @param limit     显示条数
     * @return java.util.List<java.lang.String>
     * @author LiuXiangLin
     * @date 13:56 2019/11/25
     **/
    List<String> listOutTradeNoNoByType(@Param("type") String type, @Param("afterTime") String afterTime, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * <p>
     * 通过商家单号更新订单状态
     * </p>
     *
     * @param outTradeNo 商家单号
     * @param orderState 订单状态
     * @return int
     * @author LiuXiangLin
     * @date 14:04 2019/11/25
     **/
    int updateOrderStateByOutTradeNo(@Param("outTradeNo") String outTradeNo, @Param("orderState") String orderState);

    /**
     * <p>
     * 更新订单支付金额
     * 注意：此方法需要更新商户订单号
     * </p>
     *
     * @param order 订单对象
     * @return int
     * @author LiuXiangLin
     * @date 10:50 2019/11/26
     **/
    int updateOrderPaymentAmount(@Param("order") Order order);

    /**
     * 修改订单保险单号
     *
     * @param orderNo
     * @param insureCode
     * @return
     */
    int updateInsureCode(@Param("orderNo") String orderNo, @Param("insureCode") String insureCode);


    /**
     * <p>
     * 通过类型和时间查询订单列表
     * </p>
     *
     * @param type      订单类型
     * @param afterTime 时间
     * @param offset    排除条数
     * @param limit     显示条数（如果参数为0则全部查询）
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 11:04 2019/11/26
     **/
    List<Order> listOrderByTypeAndTime(@Param("type") String type, @Param("afterTime") String afterTime, @Param("offset") int offset, @Param("limit") int limit);


    /**
     * <p>
     * 通过订单类型以及订单状态和时间查询订单编号
     * </p>
     *
     * @param orderState      订单类型
     * @param orderStateState 订单状态
     * @param dateTime        订单状态时间
     * @param offset          排除条数
     * @param limit           显示条数（为0则表示不分页）
     * @return java.util.List<java.lang.String>
     * @author LiuXiangLin
     * @date 9:50 2020/1/2
     **/
    List<String> listByOrderStateAndType(@Param("orderState") String orderState,
                                         @Param("orderStateState") String orderStateState,
                                         @Param("dateTime") String dateTime,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    /**
     * <p>
     * 通过订单编号查询订单金额
     * </p>
     *
     * @param orderNo 订单金额
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 14:46 2020/3/5
     **/
    BigDecimal getPriceByOrderNo(@Param("orderNo") String orderNo);

    /**
     * <p>
     * 更新订单最大的sn
     * </p>
     *
     * @param orderNo 订单编号
     * @param sn      订单状态sn
     * @return int
     * @author LiuXiangLin
     * @date 16:24 2020/6/1
     **/
    int updateMaxStateSn(@Param("orderNo") String orderNo, @Param("sn") int sn);

    /**
     * <p>
     * 通过订单主键获取订单详情
     * </p>
     *
     * @param orderNo 订单编号
     * @return com.jxywkj.application.pet.model.consign.Order
     * @author LiuXiangLin
     * @date 15:26 2020/6/16
     **/
    Order getDetailByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 标记订单为线下付款
     * @param orderNo
     * @return
     */
    int updateOfflinePayment(@Param("orderNo") String orderNo);

    /**
     * 查看订单是否是线下付款订单
     * @param orderNo
     * @return
     */
    String getOfflinePayment(@Param("orderNo") String orderNo);

    /**
     * 确认条例
     * @param orderNo
     * @return
     */
    int confirmRegulation(@Param("orderNo")String orderNo);

    /**
     * @Description: 管理员获取订单
     * @Author: zxj
     * @Date: 2020/10/14 16:25
     * @param orderQueryDto:
     * @return: java.util.List<com.jxywkj.application.pet.model.consign.Order>
     **/
    List<OrderVO> listAdminOrder(OrderQueryDto orderQueryDto);

    int countListAdminOrder(OrderQueryDto orderQueryDto);
}
