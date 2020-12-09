package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Position;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.PositionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 位置
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className PositionController
 * @date 2020/4/10 17:49
 **/
@RestController
@RequestMapping(value = "/consign/position")
public class PositionController {
    @Resource
    PositionService positionService;

    /**
     * <p>
     * 新增或者更新数据
     * </p>
     *
     * @param position 位置对象
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 9:18 2020/4/13
     **/
    @PostMapping()
    public JsonResult saveOrUpdate(@SessionAttribute("staff") Staff staff, @RequestBody Position position) {
        position.setStation(staff.getStation());
        positionService.saveOrUpdate(position);
        return JsonResult.success();
    }

    /**
     * <p>
     * 通过站点编号以及类型后获取位置信息
     * </p>
     *
     * @param staff 站点编号
     * @param type  类型
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 10:16 2020/4/13
     **/
    @GetMapping()
    public JsonResult get(@SessionAttribute("staff") Staff staff, @RequestParam("type") int type) {
        return JsonResult.success(positionService.getByStationNoAndType(staff.getStation().getStationNo(), type));
    }
}
