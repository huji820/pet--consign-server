package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Order;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface ConsignOrderPriceMapper {

    BigDecimal getOrderPaymentAmount(@Param("order")Order order);
}
