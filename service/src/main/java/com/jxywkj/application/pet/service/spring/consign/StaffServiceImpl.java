package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.dao.consign.StaffMapper;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工的实现类
 *
 * @Description
 * @Author Administrator
 * @Date 2019-06-20 21:34
 * @Version 1.0
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Resource
    StaffMapper staffMapper;

    @Resource
    CustomerMessageService customerMessageService;

    @Override
    public Staff getStaffByCustomerNoAndStatus(String customerNo, short status) {
        return staffMapper.getStaffByCustomerNoAndStatus(customerNo, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertStaff(Staff staff) {
        return staffMapper.insertStaff(staff);
    }

    @Transactional(readOnly = true)
    @Override
    public Staff getStaffByPhoneAndPwd(String phone, String pwd) {
        return staffMapper.getStaffByPhoneAndPwd(phone, pwd, StaffStateEnum.NORMAL.getType());
    }

    /**
     * @return
     * @Author Aze
     * @Description: 获取职员信息
     * @Date:
     * @Param
     */

    @Override
    public List<Staff> listStaff(String staffNo, String phone, int stationNo, String staffName) {
        return staffMapper.listStaff(staffNo, phone, stationNo, staffName, StaffStateEnum.NORMAL.getType());
    }

    /**
     * @return
     * @Author Aze
     * @Description: 修改员工基本信息
     * @Date:
     * @Param
     */

    @Override
    public void updateStaff(String phone, String staffName, Integer role, int staffNo, String staffSex) {
        staffMapper.updateStaff(phone, staffName, role, staffNo, staffSex);
    }

    @Override
    public void deleteStaff(int staffNo) {
        staffMapper.deleteStaff(staffNo);
    }

    @Override
    public Staff getStaffByPhoneNumberAndState(String phoneNumber, short state) {
        return staffMapper.getStaffByPhoneNumberAndState(phoneNumber, state);
    }

    @Override
    public List<Staff> listByStationNo(int stationNo) {
        return staffMapper.listByStationN(stationNo, StaffStateEnum.NORMAL.getType());
    }

    @Override
    public List<Staff> listByStationNoAndState(short state, int stationNo) {
        return staffMapper.listByStationNoAndState(state, stationNo);
    }

    @Override
    public int reviewStaff(Staff staff) {
        staff.setState(StaffStateEnum.NORMAL.getType());
        return staffMapper.updateStaffState(staff);
    }

    @Override
    public List<Staff> listAdminStaff(int stationNo, int start, int limit) {
        return staffMapper.listAdminStaff(stationNo, StaffStateEnum.NORMAL.getType(), start, limit);
    }

    @Override
    public int countAdminStaff(int stationNo) {
        return staffMapper.countAdminStaff(stationNo, StaffStateEnum.NORMAL.getType());
    }

    @Override
    public Staff getByStaffNo(int staffNo) {
        return staffMapper.getByStaffNo(staffNo, StaffStateEnum.NORMAL.getType());
    }

    @Override
    public Staff getByPhone(String phone) {
        return staffMapper.getByPhone(phone);
    }

    @Override
    public Staff getByCustomerNo(String customerNo) {
        return staffMapper.getByCustomerNo(customerNo);
    }

    @Override
    public Staff verificationLogin(String phone) {
        return staffMapper.getStationAdmin(phone, StaffStateEnum.NORMAL.getType());
    }

    @Override
    public int rejectStaff(Staff staff) {
        Staff existsStaff = staffMapper.getByStaffNo(staff.getStaffNo(), StaffStateEnum.UNAUDITED.getType());
        if (existsStaff != null) {
            // 删除员工
            staffMapper.removeStaff(staff.getStaffNo());

            // 发送站内信
            return customerMessageService.saveRejectStaff(staff);
        }

        return 0;
    }

    @Override
    public List<Staff> listByStationNoAnRole(int stationNo, int... role) {
        return staffMapper.listByStationNoAndRoles(stationNo, role);
    }
}
