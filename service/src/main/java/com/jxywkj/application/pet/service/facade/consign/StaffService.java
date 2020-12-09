package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Staff;

import java.util.List;

/**
 * @Descriptions
 * @Author Administrator
 * @Date 2019-06-20 21:11
 * @Version 1.0
 */
public interface StaffService {

    /**
     * <p>
     * 根据微信的customerNo获取员工
     * </p>
     *
     * @param customerNo 用户编号
     * @param status     员工状态
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @date 16:39 2020/3/4
     **/
    Staff getStaffByCustomerNoAndStatus(String customerNo, short status);

    /**
     * 新增职员
     *
     * @param staff
     */
    int insertStaff(Staff staff);

    /**
     * 根据手机号码和密码获取员工信息
     *
     * @param phone
     * @param pwd
     * @return
     */
    Staff getStaffByPhoneAndPwd(String phone, String pwd);

    /**
     * <p>
     * 通过条件查询员工集合
     * </p>
     *
     * @param staffNo   员工编号
     * @param phone     电话号码
     * @param stationNo 站点编号
     * @param staffName 站点名称
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @author LiuXiangLin
     * @date 10:59 2019/11/21
     **/
    List<Staff> listStaff(String staffNo, String phone, int stationNo, String staffName);

    /**
     * 后台查询员工
     *
     * @param stationNo
     * @param start
     * @param limit
     * @return
     */
    List<Staff> listAdminStaff(int stationNo, int start, int limit);

    /**
     * 后台查询员工数量
     *
     * @param stationNo
     * @return
     */
    int countAdminStaff(int stationNo);

    /**
     * <p>
     * 跟新员工
     * </p>
     *
     * @param phone     电话号码
     * @param staffName 员工名称
     * @param role 员工权限
     * @param staffNo   员工编号
     * @param staffSex  员工性别
     * @return void
     * @author LiuXiangLin
     * @date 10:59 2019/11/21
     **/
    void updateStaff(String phone, String staffName, Integer role, int staffNo, String staffSex);

    /**
     * <p>
     * 删除员工
     * </p>
     *
     * @param staffNo 员工编号
     * @return void
     * @author LiuXiangLin
     * @date 11:00 2019/11/21
     **/
    void deleteStaff(int staffNo);

    /**
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @Author LiuXiangLin
     * @Description 通过电话号码查询用户
     * @Date 13:58 2019/7/19
     * @Param [phoneNumber]
     **/
    Staff getStaffByPhoneNumberAndState(String phoneNumber, short state);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @Author LiuXiangLin
     * @Description 通过站点查询数据
     * @Date 16:17 2019/8/24
     * @Param [stationNo]
     **/
    List<Staff> listByStationNo(int stationNo);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @Author LiuXiangLin
     * @Description 通过站点编号以及员工状态查询数据
     * @Date 9:40 2019/9/19
     * @Param [state, stationNo]
     **/
    List<Staff> listByStationNoAndState(short state, int stationNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新员工状态
     * @Date 10:37 2019/9/19
     * @Param [staffList]
     **/
    int reviewStaff(Staff staff);

    /**
     * @param [staffNo]
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @description 通过员工编号查询数据
     * @date 17:13 2019/10/11
     **/
    Staff getByStaffNo(int staffNo);

    /**
     * @param [phone]
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @description 通过电话号码查询数据
     * @date 17:42 2019/10/14
     **/
    Staff getByPhone(String phone);

    /**
     * 通过customerNo获取员工
     * @param customerNo
     * @return
     */
    Staff getByCustomerNo(String customerNo);

    /**
     * <p>
     * 验证码登录
     * </p>
     *
     * @param phone 电话号码
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @date 11:01 2019/11/21
     **/
    Staff verificationLogin(String phone);

    /**
     * <p>
     * 驳回员工申请
     * </p>
     *
     * @param staff 员工对象
     * @return int
     * @author LiuXiangLin
     * @date 14:43 2020/1/10
     **/
    int rejectStaff(Staff staff);

    /**
     * <p>
     * 通过站点编号以及角色查询在职的员工
     * </p>
     *
     * @param stationNo 站点编号
     * @param role 角色
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @author LiuXiangLin
     * @date 17:34 2020/6/4
     **/
    List<Staff> listByStationNoAnRole(int stationNo, int... role);
}
