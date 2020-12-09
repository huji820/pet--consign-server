package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.OrderPremiumMapper;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.OrderPremiumService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.PinyinUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderPremiumServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/8/23 17:05
 * @Version 1.0
 **/
@Service
public class OrderPremiumServiceImpl implements OrderPremiumService {
    @Resource
    OrderPremiumMapper orderPremiumMapper;

    @Override
    public List<OrderPremium> listByOrderNo(String orderNo) {
        return orderPremiumMapper.listByOrderNo(orderNo);
    }

    @Override
    public BigDecimal getPriceDifferenceTotal(String orderNo) {
        return orderPremiumMapper.getPriceDifferenceTotal(orderNo, OrderStatusEnum.PAID.getState());
    }

    @Override
    public int insetOrderSpread(OrderPremium orderPremium, Staff staff) {
        // 设置必须参数
        orderPremium.setBillNo(getOrderNo(staff.getStaffName()));
        orderPremium.setOrderDate(DateUtil.format(new Date(), DateUtil.FORMAT_SIMPLE));
        orderPremium.setOrderTime(DateUtil.format(new Date(), DateUtil.FORMAT_TIME));
        orderPremium.setStaff(staff);
        return orderPremiumMapper.saveOrderSpread(orderPremium);
    }

    /**
     * @param [staffName]
     * @return java.lang.String
     * @author LiuXiangLin
     * @description 获取订单编号
     * @date 15:29 2019/10/10
     **/
    private String getOrderNo(String staffName) {
        return StringUtils.getUuid() + PinyinUtil.cn2py(staffName, PinyinUtil.RET_PINYIN_TYPE_HEADCHAR).toUpperCase();
    }

}
