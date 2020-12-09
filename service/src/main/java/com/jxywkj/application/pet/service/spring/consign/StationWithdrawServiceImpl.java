package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.common.enums.WithdrawEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.BalanceMapper;
import com.jxywkj.application.pet.dao.consign.StationWithdrawMapper;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessBalance;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.StationWithdraw;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceBufferService;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceService;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.business.BusinessWithdrawService;
import com.jxywkj.application.pet.service.facade.common.SmsSendService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName StationWithdrawServiceImpl
 * @Description 站点提现
 * @Author LiuXiangLin
 * @Date 2019/8/26 15:00
 * @Version 1.0
 **/
@Service
public class StationWithdrawServiceImpl implements StationWithdrawService {
    @Resource
    StationWithdrawMapper stationWithdrawMapper;

    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    StationService stationService;

    @Resource
    StaffService staffService;

    @Resource
    StationBalanceBufferService stationBalanceBufferService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    StationBalanceFlowService stationBalanceFlowService;

    @Resource
    BalanceService balanceService;

    @Resource
    BusinessBalanceBufferService businessBalanceBufferService;

    @Resource
    BusinessService businessService;

    @Resource
    BusinessBalanceService businessBalanceService;

    @Resource
    BusinessWithdrawService businessWithdrawService;

    @Resource
    BalanceMapper balanceMapper;

    @Resource
    SmsSendService smsSendService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveStationWithdraw(String customerNo, BigDecimal withdrawAmount) {
        // 员工校验
        Staff staff = staffService.getStaffByCustomerNoAndStatus(customerNo, StaffStateEnum.NORMAL.getType());
        if (staff == null) {
            return 0;
        }

        // 站点管理员校验
        Station station = stationService.getByCustomerNo(customerNo);
        if (station == null) {
            return 0;
        }
        staff.setStation(station);
        // 余额校验
        //BigDecimal stationAmount = stationBalanceService.getTotalByStationNo(station.getStationNo());
        //余额校验，包含个人余额、站点余额以及商家余额
        BigDecimal stationAmount = balanceService.getByCustomerNo(customerNo);
        if (stationAmount == null || stationAmount.compareTo(withdrawAmount) < 0) {
            return 0;
        }

        // 站点当时余额
        BigDecimal stationBalance = stationBalanceService.getTotalByStationNo(station.getStationNo());
        stationBalance = stationBalance==null?BigDecimal.ZERO:stationBalance;
        //个人余额
        BigDecimal staffBalance = balanceMapper.getByCustomerNo(customerNo);
        staffBalance = staffBalance==null?BigDecimal.ZERO:staffBalance;

        //商家余额
        Business business = businessService.getBusinessByPhone(staff.getPhone(), BusinessStateEnum.NORMAL);

        if(business == null){
            stationWithdraw(staff,withdrawAmount);
            return 1;
        }
        BusinessBalance balance = businessBalanceService.getByBusinessNo(business.getBusinessNo());
        BigDecimal businessBalance = balance==null?BigDecimal.ZERO:balance.getTotalAmount();

        BigDecimal subtract1 = withdrawAmount.subtract(stationBalance);
        BigDecimal subtract2 = subtract1.subtract(businessBalance);
        BigDecimal subtract3 = subtract2.subtract(staffBalance);

        if (subtract1.compareTo(new BigDecimal(0)) >= 0) {
            //提取站点所有余额
            stationWithdraw(staff,stationBalance);
            if (subtract2.compareTo(new BigDecimal(0)) >= 0) {
                businessWithdrawService.saveBusinessWithdraw(business.getBusinessNo(),businessBalance);
            } else {
                businessWithdrawService.saveBusinessWithdraw(business.getBusinessNo(),subtract1);
            }
        }else{
            stationWithdraw(staff,withdrawAmount);
        }

        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int confirmWithdraw(String stationWithdrawNo) {
        // 获取提现单据
        StationWithdraw stationWithdraw = stationWithdrawMapper.getByWithdrawNo(stationWithdrawNo);

        if (stationWithdraw != null && WithdrawEnum.TO_BE_AUDITED.getState().equals(stationWithdraw.getState())) {
            // 更新订单状态
            stationWithdrawMapper.updateWithdrawState(stationWithdrawNo, WithdrawEnum.COMPLETED.getState());

            // 发送站内信
            stationMessageService.saveWithdrawSuccess(stationWithdraw);

            // 删除临时减扣表
            return stationBalanceBufferService.deleteByWithdrawNo(stationWithdrawNo);

        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int confirmWithdraws(List<StationWithdraw> stationWithdraws) {
        int result = 0;
        for (StationWithdraw stationWithdraw : stationWithdraws) {
            result = confirmWithdraw(stationWithdraw.getWithdrawNo());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int rejectWithdraws(List<StationWithdraw> stationWithdraws) {
        int result = 0;
        for (StationWithdraw stationWithdraw : stationWithdraws) {
            result = rejectWithdraw(stationWithdraw.getWithdrawNo());
        }
        return result;
    }

    @Override
    public List<StationWithdraw> listByStationNo(String stationNo, int offset, int limit) {
        return stationWithdrawMapper.listByStationNo(stationNo, offset, limit);
    }


    @Override
    public List<StationWithdraw> listStationWithdraw(String withdrawNo, String stationNo, String startDate, String endDate, boolean active, int start, int end) {
        return stationWithdrawMapper.listStationWithdraw(withdrawNo, stationNo, startDate, endDate, active, start, end);
    }

    @Override
    public int countStationWithdraw(String withdrawNo, String stationNo, String startDate, String endDate, boolean active) {
        return stationWithdrawMapper.countStationWithdraw(withdrawNo, stationNo, startDate, endDate, active);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int rejectWithdraw(String stationWithdrawNo) {
        // 获取提现单
        StationWithdraw stationWithdraw = stationWithdrawMapper.getByWithdrawNo(stationWithdrawNo);

        // 状态判断
        if (stationWithdraw == null || !WithdrawEnum.TO_BE_AUDITED.getState().equals(stationWithdraw.getState())) {
            return 0;
        }

        // 修改订单状态
        stationWithdrawMapper.updateWithdrawState(stationWithdrawNo, WithdrawEnum.REJECT.getState());

        // 删除临时表中的数据
        stationBalanceBufferService.deleteByWithdrawNo(stationWithdrawNo);

        // 发送站内信
        stationMessageService.saveRejectWithdraw(stationWithdraw);

        // 修改站点余额
        stationBalanceService.subtractTotalAmountByWithdraw(String.valueOf(stationWithdraw.getStation().getStationNo()), stationWithdraw.getAmount().negate());

        // 添加余额流水
        stationBalanceFlowService.save(stationWithdraw.getStation(), stationWithdraw.getAmount(), stationWithdraw.getWithdrawNo(), null, BalanceFlowTypeEnum.WITHDRAW_REJECT);

        return 1;
    }

    private void stationWithdraw(Staff staff,BigDecimal withdrawAmount){
        // 创建提现对象
        StationWithdraw stationWithdraw = getStationWithdraw(staff, withdrawAmount);

        // 减扣金额
        stationBalanceService.subtractTotalAmountByWithdraw(String.valueOf(staff.getStation().getStationNo()), withdrawAmount);

        // 添加临时减扣
        stationBalanceBufferService.saveWithdraw(stationWithdraw);

        // 新增提现记录
        stationWithdrawMapper.saveAStationWithdraw(stationWithdraw);

        //发送提现短信通知管理员
        try {
            smsSendService.sendWithdrawNotice(stationWithdraw.getStation().getStationName(), stationWithdraw.getWithdrawNo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 余额流水
        stationBalanceFlowService.save(staff.getStation(), stationWithdraw.getAmount().negate(), stationWithdraw.getWithdrawNo(), null, BalanceFlowTypeEnum.WITHDRAW_CONFIRM);

    }

    /**
     * @return com.jxywkj.application.pet.model.consign.StationWithdraw
     * @Author LiuXiangLin
     * @Description 获取提现对象
     * @Date 15:17 2019/8/26
     * @Param [staff, withdrawAmount]
     **/
    private StationWithdraw getStationWithdraw(Staff staff, BigDecimal withdrawAmount) {
        StationWithdraw result = new StationWithdraw();
        result.setWithdrawNo(getWithdrawNo());
        result.setWithdrawTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        result.setStaff(staff);
        result.setAmount(withdrawAmount);
        result.setState(WithdrawEnum.TO_BE_AUDITED.getState());
        result.setStation(staff.getStation());

        return result;
    }

    /**
     * @return java.lang.String
     * @Author LiuXiangLin
     * @Description 获取订单主键
     * @Date 15:17 2019/8/26
     * @Param []
     **/
    private String getWithdrawNo() {
        return StringUtils.getUuid();
    }
}
