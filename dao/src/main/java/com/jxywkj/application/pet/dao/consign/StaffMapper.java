package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工的Mapper
 *
 * @Descriptions
 * @Author Administrator
 * @Date 2019-06-20 21:11
 * @Version 1.0
 */
@Mapper
public interface StaffMapper {

    /**
     * <p>
     * 根据用户编号查询用户
     * </p>
     *
     * @param customerNo 用户编号
     * @param status     状态
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @date 16:40 2020/3/4
     **/
    Staff getStaffByCustomerNoAndStatus(@Param("customerNo") String customerNo, @Param("status") short status);


    /**
     * <p>
     * 新增职员
     * </p>
     *
     * @param staff 员工对象
     * @return int
     * @author LiuXiangLin
     * @date 15:44 2020/3/9
     **/
    int insertStaff(@Param("staff") Staff staff);

    /**
     * 根据手机号码和密码获取员工信息
     *
     * @param phone 电话好号码
     * @param pwd   密码
     * @param state 员工状态
     * @return
     */
    Staff getStaffByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd, @Param("state") short state);


    /**
     * <p>
     * 通过状态查询所有的员工人数
     * </p>
     *
     * @param state 员工状态
     * @return int
     * @author LiuXiangLin
     * @date 15:45 2020/3/9
     **/
    int countStaff(@Param("state") short state);

    /**
     * <p>
     * 通过条件查询员工
     * </p>
     *
     * @param staffNo   员工编号
     * @param phone     员工电话号码
     * @param stationNo 站点编号
     * @param staffName 员工名称
     * @param state     员工状态
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @author LiuXiangLin
     * @date 15:45 2020/3/9
     **/
    List<Staff> listStaff(@Param("staffNo") String staffNo,
                          @Param("phone") String phone,
                          @Param("stationNo") int stationNo,
                          @Param("staffName") String staffName,
                          @Param("state") short state
    );


    /**
     * <p>
     * 查询后台所有的员工
     * </p>
     *
     * @param stationNo 站点编号
     * @param state     状态
     * @param start     分页开始位置
     * @param limit     查询条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @author LiuXiangLin
     * @date 15:47 2020/3/9
     **/
    List<Staff> listAdminStaff(@Param("stationNo") int stationNo,
                               @Param("state") short state,
                               @Param("start") int start,
                               @Param("limit") int limit);


    /**
     * <p>
     * 查询站点所有的员工数量
     * </p>
     *
     * @param stationNo 站点编号
     * @param state     员工状态
     * @return int
     * @author LiuXiangLin
     * @date 15:49 2020/3/9
     **/
    int countAdminStaff(@Param("stationNo") int stationNo, @Param("state") short state);

    /**
     * <p>
     * 更新员工数据
     * </p>
     *
     * @param phone     电话号码
     * @param staffName 员工名称
     * @param role 员工角色
     * @param staffNo   员工编号
     * @param staffSex  员工性别
     * @author LiuXiangLin
     * @date 15:50 2020/3/9
     **/
    void updateStaff(@Param("phone") String phone,
                     @Param("staffName") String staffName,
                     @Param("role") Integer role,
                     @Param("staffNo") int staffNo,
                     @Param("staffSex") String staffSex);

    /**
     * <p>
     * 删除用户（假删）
     * </p>
     *
     * @param staffNo 员工编号
     * @author LiuXiangLin
     * @date 14:57 2020/1/10
     **/
    void deleteStaff(int staffNo);


    /**
     * <p>
     * 通过电话号码查询员工
     * </p>
     *
     * @param phoneNumber 电话号码
     * @param state       员工状态
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @date 15:51 2020/3/9
     **/
    Staff getStaffByPhoneNumberAndState(@Param("phoneNumber") String phoneNumber,
                                        @Param("state") short state);


    /**
     * <p>
     * 查询某一个站点的所有员工
     * </p>
     *
     * @param stationNo 站点编号
     * @param state     员工状态
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @author LiuXiangLin
     * @date 15:52 2020/3/9
     **/
    List<Staff> listByStationN(@Param("stationNo") int stationNo, @Param("state") short state);


    /**
     * <p>
     * 通过站点编号以及员工状态查询所有的员工数据
     * </p>
     *
     * @param state     员工状态
     * @param stationNo 站点编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @author LiuXiangLin
     * @date 15:52 2020/3/9
     **/
    List<Staff> listByStationNoAndState(@Param("state") short state,
                                        @Param("stationNo") int stationNo);


    /**
     * <p>
     * 跟新员工状态
     * </p>
     *
     * @param staff 员工对象
     * @return int
     * @author LiuXiangLin
     * @date 15:53 2020/3/9
     **/
    int updateStaffState(@Param("staff") Staff staff);


    /**
     * <p>
     * 通过员工编号以及状态查询员工
     * </p>
     *
     * @param staffNo 员工对象
     * @param state   员工状态
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @date 15:53 2020/3/9
     **/
    Staff getByStaffNo(@Param("staffNo") int staffNo, @Param("state") short state);


    /**
     * <p>
     * 通过电话号码查询员工（不校验状态）
     * </p>
     *
     * @param phone 电话号码
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @date 15:54 2020/3/9
     **/
    Staff getByPhone(@Param("phone") String phone);

    /**
     * 通过customerNo获取员工
     * @param customerNo
     * @return
     */
    Staff getByCustomerNo(@Param("customerNo") String customerNo);

    /**
     * <p>
     * 获取管理员员工对象
     * </p>
     *
     * @param phone 电话号码
     * @param state 员工状态
     * @return com.jxywkj.application.pet.model.consign.Staff
     * @author LiuXiangLin
     * @date 11:03 2019/11/21
     **/
    Staff getStationAdmin(@Param("phone") String phone, @Param("state") short state);

    /**
     * <p>
     * 删除用户（真删）
     * </p>
     *
     * @param staffNo 员工编号
     * @return int
     * @author LiuXiangLin
     * @date 14:58 2020/1/10
     **/
    int removeStaff(@Param("staffNo") int staffNo);

    /**
     * <p>
     * 通过站点编号以及角色查询在职员工
     * </p>
     *
     * @param stationNo 站点编号
     * @param roles 角色
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Staff>
     * @author LiuXiangLin
     * @date 17:36 2020/6/4
     **/
    List<Staff> listByStationNoAndRoles(@Param("stationNo") int stationNo,@Param("roles") int[] roles);
}
