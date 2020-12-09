package com.jxywkj.application.pet.api.consign;

import com.alibaba.fastjson.JSONObject;
import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.ConsignOrderRefund;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.model.consign.dto.OrderUpdateDTO;
import com.jxywkj.application.pet.model.consign.params.OrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.service.facade.common.OrderRebateService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


/**
 * @Description
 * @Author Administrator
 * @Date 2019-07-13 0:53
 * @Version 1.0
 */
@Api(description = "用户订单")
@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    @Autowired
    ConsignOrderService consignOrderService;

    @Autowired
    TransportService transportService;

    @Autowired
    StationService stationService;

    @Resource
    ConsignStationOrderService consignStationOrderService;

    @Resource
    OrderRebateService orderRebateService;

    @Resource
    InsureService insureService;


    @ApiOperation(value = "客户下单")
    @PostMapping("/insertOrder")
    public JsonResult insertConsignOrder(@RequestBody OrderDTO orderDTO) throws Exception {
        return JsonResult.success(consignOrderService.insertConsignOrder(orderDTO));
    }

    @PostMapping("/getOrderPrice")
    @ApiOperation(value = "获取运输价格")
    public JsonResult getOrderPrice(@RequestBody OrderDTO orderDTO) throws Exception {
        Transport transportParam = new Transport();
        transportParam.setStartCity(orderDTO.getStartCity());
        transportParam.setEndCity(orderDTO.getEndCity());
        transportParam.setTransportType(String.valueOf(orderDTO.getTransportType()));

        List<Transport> transports = transportService.listTransportByCondition(transportParam);
        // 请求参数有误
        if (CollectionUtils.isEmpty(transports)) {
            return JsonResult.error(Code.INVALID_TRANSPORT, Code.INVALID_TRANSPORT.getMessage());
        }

        //测试，默认站点
        List<Station> stations = stationService.listStation(orderDTO.getStartCity());

        Station station;
        if (stations != null && stations.size() > 0) {
            station = stations.get(0);
        } else {
            return JsonResult.error(Code.ERROR, "起始城市没有站点！");
        }
        // 价格价格为校验
        OrderPrice orderPrice = consignOrderService.getOrderPrice(transports.get(0), orderDTO,station);
        if (orderPrice == null) {
            return JsonResult.error(Code.ERROR, "获取价格错误！");
        }

        return JsonResult.success(orderPrice);
    }

    @ApiOperation("通过订单类型查询订单列表")
    @GetMapping("/listOrderList")
    public JsonResult getOrderListByType(@RequestParam("orderStatus") String state, @RequestParam("customerNo") String customerNo) {
        return JsonResult.success(consignOrderService.listOrderState(state, customerNo));
    }

    @ApiOperation("获取订单明细")
    @GetMapping("/orderDetail")
    public JsonResult getOrderDetailByOrderNo(@RequestParam("orderNo") String orderNo, @RequestParam("customerNo") String customerNo) {
        return JsonResult.success(consignOrderService.getOrderDetailWidthState(orderNo, customerNo));
    }

    @ApiOperation(value = "取消订单")
    @PostMapping("/cancelOrder")
    public JsonResult cancelOrder(@RequestParam("customerNo") String customerNo, @RequestParam("orderNo") String orderNo) {
        return JsonResult.success(consignOrderService.cancelOrder(orderNo, customerNo));
    }

    @ApiOperation(value = "上传付款凭证")
    @GetMapping("/uploadPaymentVoucher")
    public JsonResult uploadPaymentVoucher(@RequestParam("orderNo")String orderNo,@RequestParam("paymentVoucher")String paymentVoucher){
        return JsonResult.success(consignOrderService.uploadPaymentVoucher(orderNo, paymentVoucher));
    }

    @ApiOperation(value = "审批付款凭证")
    @GetMapping("/api/order/examinePaymentVoucher")
    @Deprecated
    public JsonResult examinePaymentVoucher(@RequestParam("orderNo")String orderNo,@RequestParam("result")boolean result,@RequestParam("feedback")String feedback){
        return  JsonResult.success(consignOrderService.examinePaymentVoucher(orderNo, result, feedback));
    }

    @ApiOperation(value = "确认收货")
    @PostMapping("/confirmOrder")
    public JsonResult confirmOrder(@RequestParam(value = "fileList", required = false) String[] fileList,
                                   @RequestParam("orderNo") String orderNo) {
        return JsonResult.success(consignOrderService.confirmOrder(fileList, orderNo));
    }

    @ApiOperation(value = "获取未确认收货订单")
    @GetMapping("/listUncertainty")
    @Deprecated
    public JsonResult listUncertainty(@RequestParam("customerNo") String customerNo) {
        return JsonResult.success(consignOrderService.listByCityNameAndState(customerNo));
    }

    @ApiOperation(value = "更新订单联系人")
    @PutMapping("/update/contacts")
    public JsonResult updateOrderContacts(@RequestBody OrderUpdateDTO orderUpdateDTO) {
        return JsonResult.success(consignOrderService.updateOrderContacts(orderUpdateDTO));
    }

    @ApiOperation(value = "验证是否有确认收货订单权限（仅限于用户） 1 有 0 无")
    @GetMapping("/check/order")
    public JsonResult checkConfirm(@RequestParam("orderNo") String orderNo, @RequestParam("customerNo") String customerNo) {
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(customerNo)) {
            return JsonResult.error(Code.CHECK_ERROR, "参数不完整");
        }
        return JsonResult.success(consignOrderService.checkConfirm(orderNo, customerNo));
    }

    @ApiOperation(value = "修改订单价格")
    @PutMapping(value = "/update/price")
    public JsonResult updateOrderPrice(@RequestBody Order order) throws Exception{
        if (order == null || StringUtils.isBlank(order.getOrderNo()) || order.getPaymentAmount() == null) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数不完整");
        }
        return JsonResult.success(consignOrderService.updatePaymentAmount(order));
    }

    @PostMapping("/refund")
    @ApiOperation(value = "订单退款")
    public JsonResult refundOrder(@RequestBody ConsignOrderRefund consignOrderRefund) {
        if (consignOrderRefund == null || consignOrderRefund.getOrder() == null || consignOrderRefund.getServiceFeeAmount() == null) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数不完整");
        }
        return JsonResult.success(consignOrderService.refundOrder(consignOrderRefund));
    }

    @ApiOperation(value = "查询站点的所有订单")
    @GetMapping("/list/station")
    public JsonResult listByStationNoAndType(@RequestParam("stationNo") int stationNo,
                                             @RequestParam("customerNo")String customerNo,
                                             @RequestParam("state") String state,
                                             @RequestParam("keyword") String keyword,
                                             @RequestParam("orderDate") String orderDate,
                                             @RequestParam("offset") int offset,
                                             @RequestParam("limit") int limit) {
        List<String> s = JSONObject.parseArray(state, String.class);
        List<Order> orderList = consignStationOrderService.listByStationNoAndType(stationNo, customerNo ,s, keyword, orderDate, offset, limit);

        return JsonResult.success(orderList);
    }

    @ApiOperation(value = "通过订单编号查询订单金额")
    @GetMapping("/price")
    public JsonResult getPriceByOrderNo(@RequestParam("orderNo") String orderNo) {
        return JsonResult.success(consignOrderService.getOrderPriceByOrderNo(orderNo));
    }

    @ApiOperation(value = "通过订单编号获取订单详情")
    @GetMapping("/{orderNo:\\w+}")
    public JsonResult get(@PathVariable String orderNo) {
        return JsonResult.success(consignOrderService.getOrderDetailByOrderNo(orderNo));
    }

    @ApiOperation(value = "补商家返利")
    @PostMapping("append-rebate")
    public JsonResult appendRebate(String billNo) throws Exception {
        insureService.addDaDiOrderInsure(new Order("20201102nc002"));
        return JsonResult.success("投保成功");
    }

    @ApiOperation(value = "物理删除订单")
    @DeleteMapping("/deleteOrder")
    public JsonResult deleteOrder(@RequestParam("orderNo")String orderNo,@RequestParam("customerNo")String customerNo){
        int i = consignOrderService.deleteOrderByOrderNo(orderNo);
        return JsonResult.success(i>0?true:false);
    }

    @ApiOperation(value = "确认条例")
    @PostMapping("/confirmRegulation")
    public JsonResult confirmRegulation(@RequestParam("orderNo")String orderNo){
        int i = consignOrderService.confirmRegulation(orderNo);
        return JsonResult.success(i>0?true:false);
    }
}
