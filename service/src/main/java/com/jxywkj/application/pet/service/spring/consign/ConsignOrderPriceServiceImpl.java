package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.ConsignOrderPriceMapper;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderPriceService;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.utils.network.HttpUtil;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;


@Service
public class ConsignOrderPriceServiceImpl implements ConsignOrderPriceService {

    @Resource
    ConsignOrderPriceMapper consignOrderPriceMapper;
    @Resource
    HttpUtil httpUtil;

    @Value("http://127.0.0.1:7070")
    String transportServiceHost;
    /**
     * 获取订单最新价格
     */
    private static final String TRANSPORT_PRICE = "/api/order/pet/getPaymentAmount";

    @Override
    public BigDecimal getOrderPaymentAmount(Order order) {
        BigDecimal i = consignOrderPriceMapper.getOrderPaymentAmount(order);
        try {
            Response response =  httpUtil.get(transportServiceHost+TRANSPORT_PRICE+"?paymentAmount="+i+"&consignOrderNo="+order.getOrderNo());
                return TypeConvertUtil.$BigDecimal(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
