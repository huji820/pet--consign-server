package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.dao.consign.OrderRemarksMapper;
import com.jxywkj.application.pet.model.consign.OrderRemarks;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.OrderRemarksService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName OrderRemarksServiceImpl
 * @Author LiuXiangLin
 * @Date 2019/9/23 11:20
 * @Version 1.0
 **/
@Service
public class OrderRemarksServiceImpl implements OrderRemarksService {
    @Resource
    OrderRemarksMapper orderRemarksMapper;

    @Resource
    StaffService staffService;

    @Override
    public int saveRemarks(OrderRemarks orderRemarks) {
        Staff staff = staffService.getByStaffNo(orderRemarks.getStaff().getStaffNo());
        if (staff == null) {
            return 0;
        }
        orderRemarks.setDateTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        orderRemarks.setStaff(staff);
        orderRemarks.setStation(staff.getStation());

        return orderRemarksMapper.saveRemarks(orderRemarks);
    }
}
