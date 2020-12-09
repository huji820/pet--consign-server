package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Post;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-06-26 17:38
 * @Version 1.0
 */
@Api("员工管理")
@RestController
@RequestMapping("/consign/staff")
public class StaffController {

    @Autowired
    StaffService staffService;


    @ApiOperation("获取员工信息 ")
    @GetMapping("")
    public JsonResult listStaff(@RequestParam("phone") String phone,
                                @RequestParam("staffNo") String staffNo,
                                @RequestParam("staffName") String staffName,
                                @SessionAttribute("staff") Staff staff) {
        int stationNo = staff.getStation().getStationNo();
        return JsonResult.success(staffService.listStaff(staffNo, phone, stationNo, staffName));
    }


    @ApiOperation("新增员工 ")
    @PostMapping("insert")
    public JsonResult insertStaff(@RequestParam("phone") String phone, @RequestParam("staffPost") String staffPost,
                                  @RequestParam("staffSex") String staffSex, @RequestParam("pwd") String pwd,
                                  @RequestParam("staffName") String staffName, @SessionAttribute("staff") Staff my) {
        Staff staff = new Staff();
        staff.setStation(my.getStation());
        staff.setPhone(phone);
        staff.setSex(staffSex);
        staff.setStaffName(staffName);
        staff.setPost(new Post(Integer.parseInt(staffPost)));
        staff.setPwd(pwd);
        staff.setState(StaffStateEnum.NORMAL.getType());
        int i = staffService.insertStaff(staff);
        return i > 0 ? JsonResult.success("新增职员成功") : JsonResult.error(Code.ERROR, "添加失败");
    }

    @ApiOperation("修改员工")
    @PutMapping("update")
    public JsonResult updateStaff(@RequestParam("phone") String phone, @RequestParam("staffPost") String staffPost,
                                  @RequestParam("staffSex") String staffSex, @RequestParam("staffName") String staffName,
            @RequestParam("role") Integer role, @RequestParam("staffNo") int staffNo) {
        staffService.updateStaff(phone, staffName, role, staffNo, staffSex);
        return JsonResult.success("修改成功");
    }

    @ApiOperation("删除职员")
    @PutMapping("/deleteStaff")
    public JsonResult deleteStaff(@RequestParam("staffNo") int staffNo) {
        staffService.deleteStaff(staffNo);
        return JsonResult.success("淘汰成功");
    }


    /**
     * 获取用户登录的编号
     *
     * @param staff
     * @return
     */
    @GetMapping("/getLoginInfo")
    @ApiOperation(value = "获取登录信息")
    public JsonResult loginInfo(@SessionAttribute("staff") Staff staff) {
        if (staff != null) {
            return JsonResult.success(staff.getStaffNo());
        } else {
            return JsonResult.error(Code.NOT_EXISTS, "获取登录信息失败");
        }
    }


    /*
     * @Author Aze
     * @Description: 用户登出时
     * @Date:
     * @Param
     * @return
     */
    @GetMapping("/loginOut")
    @ApiOperation(value = "用户登出")
    public void loginOut(HttpSession session) {
        session.removeAttribute("staff");
        session.invalidate();//使Session变成无效，及用户退出

    }

    @GetMapping("/listByStation")
    public JsonResult listAllStaff(@SessionAttribute("staff") Staff staff) {
        return JsonResult.success(staffService.listByStationNo(staff.getStation().getStationNo()));
    }

}
