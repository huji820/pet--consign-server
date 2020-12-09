package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.common.Paging;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.dto.OrderUpdateDTO;
import com.jxywkj.application.pet.model.consign.params.OrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.model.consign.vo.OrderVO;
import com.jxywkj.application.pet.model.dto.OrderQueryDto;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConsignOrderService
 * @Description: 托运订单Service
 * @Author Aze
 * @Date 2019/7/13 10:03
 * @Version 1.0
 **/

public interface ConsignOrderService {

    /**
     * 新增订单
     *
     * @param orderDTO 订单对象
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 10:49 2019/10/22
     **/
    String insertConsignOrder(OrderDTO orderDTO) throws Exception;

    /**
     * 获取订单
     *
     * @param orderNo
     * @param orderNo
     * @return
     */
    Order getOrder(String orderNo);

    /**
     * 新增订单
     *
     * @param orderLedger 订单对象
     * @param transport   运输路线
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 10:49 2019/10/22
     **/
    String insertConsignLedgerOrder(OrderLedger orderLedger, Transport transport);

    /**
     * 获取城市的断码
     *
     * @param cityName
     * @return
     */
    String getCityShortName(String cityName);


    /**
     * 生成单据编号
     *
     * @param date
     * @param cityNameAbbreviation
     * @return
     */
    String getOrderNo(Date date, String cityNameAbbreviation);

    /**
     * @return java.math.BigDecimal
     * @Author LiuXiangLin
     * @Description 计算订单价格
     * @Date 15:35 2019/7/13
     * @Param []
     **/
    OrderPrice getOrderPrice(Transport transport, OrderDTO orderDTO,Station station) throws Exception;

    /**
     * @return String
     * @Author LiuXiangLin
     * @Description 通过开始时间和城市名称查询最后的订单号
     * @Date 10:06 2019/8/27
     * @Param [startTime, endTime, city]
     **/
    String getLastOrderNoByTimeAndCity(String startTime, String endTime, String city);

    /**
     * 修改保险投保单号
     *
     * @param orderNo
     * @param insureCode
     * @return
     */
    int updateInsureCode(String orderNo, String insureCode);

    /**
     * @return com.jxywkj.application.pet.model.consign.Order
     * @Author LiuXiangLin
     * @Description 通过订单号查询订单
     * @Date 15:25 2019/8/12
     * @Param [orderNo]
     **/
    Order getConsignOrderByOrderNo(String orderNo);


    /**
     * @return
     * @Author Aze
     * @Description:
     * @Date:
     * @Param
     */
    void updateConsignOrder(String transportNo, String leaveDate, String orderDate,
                            String orderTime, String petTypeNo, String petClassifyNo,
                            String weight, String num);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新支付状态为已支付
     * @Date 10:36 2019/7/18
     * @Param [order]
     **/
    int updateOrder2Paid(String orderNo);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 修改价格
     * @Date 18:30 2019/7/22
     * @Param [orderNo, beforeAmount, afterAmount]
     **/
    int updateOrderPrice(String orderNo, BigDecimal beforeAmount, BigDecimal afterAmount);


    /**
     * <p>
     * 通过订单状态和用户编号查询订单列表
     * </p>
     *
     * @param state      订单状态
     * @param customerNo 用户编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 9:50 2020/3/5
     **/
    List<Order> listOrderState(String state, String customerNo);


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 通过站点获取所有的订单信息
     * @Date 9:36 2019/7/27
     * @Param [stationNo]
     **/
    List<Order> listOrdersByStationNo(int stationNo);

    /**
     * <p>
     * 获取订单详情以及订单状态
     * </p>
     *
     * @param orderNo    订单编号
     * @param customerNo 用户编号
     * @return com.jxywkj.application.pet.model.consign.Order
     * @author LiuXiangLin
     * @date 9:51 2020/3/5
     **/
    Order getOrderDetailWidthState(String orderNo, String customerNo);


    /**
     * <p>
     * 获取订单的支付参数
     * </p>
     *
     * @param customerNo 用户编号
     * @param orderNo    订单编号
     * @param appType    app类型
     * @param ipAddress  ip地址
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuXiangLin
     * @date 13:56 2020/3/4
     **/
    Map<String, String> getWeChatPayParam(String customerNo, String orderNo, String appType, String ipAddress) throws Exception;

    /**
     * <p>
     * 获取代付订单的支付参数
     * </p>
     *
     * @param customerNo 用户编号
     * @param orderNo    订单编号
     * @param appType    app类型
     * @param ipAddress  ip地址
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuXiangLin
     * @date 13:56 2020/3/4
     **/
    Map<String, String> getOtherWeChatPayParam(String customerNo, String orderNo, String appType, String ipAddress) throws Exception;

    /**
     * <p>
     * 取消订单
     * </p>
     *
     * @param orderNo    订单编号
     * @param customerNo 用户编号
     * @return int
     * @author LiuXiangLin
     * @date 9:53 2020/3/5
     **/
    int cancelOrder(String orderNo, String customerNo);

    /**
     * <p>
     * 通过单号查询单号
     * </p>
     *
     * @param orderNo    订单编号
     * @param customerNo 用户编号
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 9:57 2020/3/5
     **/
    String getOrderByOrder(String orderNo, String customerNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新订单状态状态
     * @Date 16:43 2019/8/12
     * @Param [orderState]
     **/
    int updateOrderState(String orderNo, String orderState);

    /**
     * 上传付款凭证
     * @param orderNo
     * @param paymentVoucher
     * @return
     */
    int uploadPaymentVoucher(String orderNo,String paymentVoucher);

    /**
     * 审核付款凭证
     * @param orderNo
     * @param result
     * @return
     */
    int examinePaymentVoucher(String orderNo, boolean result, String feedback);


    /**
     * <p>
     * 确认收货
     * </p>
     *
     * @param fileList 文件列表
     * @param orderNo  订单编号
     * @return int
     * @author LiuXiangLin
     * @date 10:04 2020/3/5
     **/
    int confirmOrder(String[] fileList, String orderNo);


    /**
     * <p>
     * 通过城市名称和订单状态查询订单
     * </p>
     *
     * @param customerNo 用户编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 10:07 2020/3/5
     **/
    List<Order> listByCityNameAndState(String customerNo);
    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description
     * @Date 15:56 2019/8/23
     * @Param [openId]
     **/

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新订单PrepayId
     * @Date 19:05 2019/8/26
     * @Param [orderNo, prepayId]
     **/
    int updateOrderPrepayId(String orderNo, String prepayId);


    /**
     * @return com.jxywkj.application.pet.model.common.Paging<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 查询已经完成的订单
     * @Date 9:30 2019/9/28
     * @Param [queryParamStr]
     **/
    Paging<Order> listCompleteOrder(String queryParamStr) throws Exception;

    /**
     * @param orderUpdateDTO
     * @return int
     * @author LiuXiangLin
     * @description 更新订单联系人
     * @date 16:23 2019/10/10
     **/
    int updateOrderContacts(OrderUpdateDTO orderUpdateDTO);

    /**
     * <p>
     * 确认是否有权限确认收货
     * </p>
     *
     * @param orderNo    订单单号
     * @param customerNo 用户编号
     * @return int 0 没有权限 1 有权限
     * @author LiuXiangLin
     * @date 17:16 2019/11/8
     **/
    int checkConfirm(String orderNo, String customerNo);

    /**
     * <p>
     * 通过订单类型查询所有的订单编号
     * </p>
     *
     * @param type      类型
     * @param offset    排除条数
     * @param afterTime 时间
     * @param limit     显示条数（为0则表示不做分页，全部查询）
     * @return java.util.List<java.lang.String>
     * @author LiuXiangLin
     * @date 15:20 2019/11/21
     **/
    List<String> listOrderNoByType(String type, String afterTime, int offset, int limit);

    /**
     * <p>
     * 通过商户支付单号获取单据
     * </p>
     *
     * @param outTradeNo 商户支付单号
     * @return com.jxywkj.application.pet.model.consign.Order
     * @author LiuXiangLin
     * @date 11:15 2019/11/25
     **/
    Order getOrderByOutTradeNo(String outTradeNo);

    /**
     * <p>
     * 通过订单类型查询所有的订单商户订单号
     * </p>
     *
     * @param type      订单类型
     * @param afterTime 时间
     * @param offset    排除数量
     * @param limit     显示条数（为0则全部查询）
     * @return java.util.List<java.lang.String>
     * @author LiuXiangLin
     * @date 13:53 2019/11/25
     **/
    List<String> listOutTradeNoByType(String type, String afterTime, int offset, int limit);

    /**
     * <p>
     * 通过商家单号更新订单编号
     * </p>
     *
     * @param outTradeNo 商家单号
     * @param orderState 订单状态
     * @return int
     * @author LiuXiangLin
     * @date 14:02 2019/11/25
     **/
    int updateStateByOutTrade(String outTradeNo, String orderState);

    /**
     * <p>
     * 跟新订单金额
     * 需要传入的数据有
     * orderNo 订单编号
     * paymentAmount 订单金额
     * </p>
     *
     * @param order 订单对象
     * @return int
     * @author LiuXiangLin
     * @date 10:28 2019/11/26
     **/
    int updatePaymentAmount(Order order) throws Exception;


    /**
     * <p>
     * 通过订单类型和时间查询订单类列表
     * </p>
     *
     * @param type      订单类型
     * @param afterTime 时间
     * @param offset    排除条数
     * @param limit     显示条数（为0则全部查询）
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 11:02 2019/11/26
     **/
    List<Order> listOrderByTypeAndTime(String type, String afterTime, int offset, int limit);

    /**
     * <p>
     * 订单退款
     * </p>
     *
     * @param consignOrderRefund 退款对象
     * @return int
     * @author LiuXiangLin
     * @date 10:15 2019/11/27
     **/
    int refundOrder(ConsignOrderRefund consignOrderRefund);

    /**
     * <p>
     * 自动签收订单
     * </p>
     *
     * @param orderNo 订单编号
     * @return int
     * @author LiuXiangLin
     * @date 15:25 2019/12/31
     **/
    int automaticConfirmConsignOrder(String orderNo);

    /**
     * <p>
     * 查询超时的出港订单
     * </p>
     *
     * @param offset 排除条数
     * @param limit  显示条数（为0则表示不做分页）
     * @return java.util.List<java.lang.String>
     * @author LiuXiangLin
     * @date 9:47 2020/1/2
     **/
    List<String> listOutPortOvertime(int offset, int limit);

    /**
     * <p>
     * 通过订单编号查询订单价格
     * </p>
     *
     * @param orderNo 订单编号
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 14:42 2020/3/5
     **/
    BigDecimal getOrderPriceByOrderNo(String orderNo);

    /**
     * <p>
     * 通过订单编号以及用户编号模糊查询用户的订单编号
     * </p>
     *
     * @param orderNo    订单编号
     * @param customerNo 用户编号
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 15:47 2020/3/12
     **/
    String getCustomerOrderNoByOrderNo(String orderNo, String customerNo);

    /**
     * <p>
     * 通过订单编号以及员工信息模糊查询员工可以查看的单据
     * </p>
     *
     * @param orderNo 订单编号
     * @param staff   员工对象
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 15:49 2020/3/12
     **/
    String getStaffOrderNoByOrderNo(String orderNo, Staff staff);

    /**
     * <p>
     * 更新订单状态的最大sn值
     * </p>
     *
     * @param orderNo 订单编号
     * @param sn sn
     * @return int
     * @author LiuXiangLin
     * @date 16:23 2020/6/1
     **/
    int updateMaxStatusSn(String orderNo,int sn);

    /**
     * <p>
     * 通过订单编号查询订单明细
     * </p>
     *
     * @param orderNo 订单编号
     * @return com.jxywkj.application.pet.model.consign.Order
     * @author LiuXiangLin
     * @date 15:31 2020/6/16
     **/
    Order getOrderDetailByOrderNo(String orderNo);

    /**
     * 通过订单编号删除订单
     * @param orderNo
     * @return
     */
    int deleteOrderByOrderNo(String orderNo);

    /**
     * 标记订单为线下付款
     * @param orderNo
     * @return
     */
    int updateOfflinePayment(String orderNo);

    /**
     * 查看订单是否是线下付款订单
     * @param orderNo
     * @return
     */
    boolean getOfflinePayment(String orderNo);

    /**
     * 确认条例
     * @param orderNo
     * @return
     */
    int confirmRegulation(String orderNo);

    /**
     * @Description: 管理员获取订单
     * @Author: zxj
     * @Date: 2020/10/14 16:05
     * @param orderQueryDto:
     * @return: java.util.List<com.jxywkj.application.pet.model.consign.Order>
     **/
    List<OrderVO> listAdminOrder(OrderQueryDto orderQueryDto);

    int countListAdminOrder(OrderQueryDto orderQueryDto);
}



