package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.OrderAmountTrendMapper;
import com.jxywkj.application.pet.model.consign.OrderAmountTrend;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.params.OrderAmountTrendDTO;
import com.jxywkj.application.pet.service.facade.consign.OrderAmountTrendService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderAmountTrendServiceImpl
 * @date 2020/1/6 16:54
 **/
@Service
public class OrderAmountTrendServiceImpl implements OrderAmountTrendService {
    @Resource
    OrderAmountTrendMapper orderAmountTrendMapper;

    @Override
    public Pagination listByParam(OrderAmountTrendDTO orderAmountTrendDTO, Staff staff) {
        Pagination pagination = new Pagination();
        // 设置参数
        if (orderAmountTrendDTO != null) {
            if (orderAmountTrendDTO.getLimit() == null) {
                orderAmountTrendDTO.setLimit(30);
            }
            if (orderAmountTrendDTO.getOffset() == null) {
                orderAmountTrendDTO.setOffset(0);
            }

            orderAmountTrendDTO.setStationNo(staff.getStation().getStationNo());

            // 查询数据
            pagination.setRoot(orderAmountTrendMapper.listByParam(orderAmountTrendDTO));
            pagination.setTotalCount(orderAmountTrendMapper.getTotalByParam(orderAmountTrendDTO));
        }
        return pagination;
    }
}
