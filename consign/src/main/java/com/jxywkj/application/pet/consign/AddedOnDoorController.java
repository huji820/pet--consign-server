package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.AbstractAddedOnDoor;
import com.jxywkj.application.pet.model.consign.AddedReceipt;
import com.jxywkj.application.pet.model.consign.AddedSend;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.AddedReceiptService;
import com.jxywkj.application.pet.service.facade.consign.AddedSendService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AddedOnDoorController
 * @Description 上门服务
 * @Author LiuXiangLin
 * @Date 2019/8/5 16:55
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/onDoor")
public class AddedOnDoorController {
    @Resource
    AddedSendService addedSendService;

    @Resource
    AddedReceiptService addedReceiptService;

    /**
     * <p>
     * 新增或者更新送宠上门
     * </p>
     *
     * @param addedSendList 送宠配置列表
     * @param staff         session中的staff对象
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 10:45 2020/4/14
     **/
    @PutMapping("/send")
    public JsonResult updateOnDoorSend(@RequestBody List<AddedSend> addedSendList, @SessionAttribute Staff staff) {
        AbstractAddedOnDoor.addSetStation(addedSendList, staff.getStation());
        return JsonResult.success(addedSendService.saveOrUpdateList(addedSendList));
    }

    /**
     * <p>
     * 获取送宠上门配置
     * </p>
     *
     * @param staff session中的员工对象
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 10:46 2020/4/14
     **/
    @GetMapping("/getSend")
    public JsonResult getOnDoorSend(@SessionAttribute Staff staff) {
        return JsonResult.success(addedSendService.listByStationNo(staff.getStation().getStationNo()));
    }


    /**
     * <p>
     * 更新或者修改上门接宠配置
     * </p>
     *
     * @param addedReceipt 上门接宠配置
     * @param staff        员工对象
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 10:47 2020/4/14
     **/
    @PutMapping("/receive")
    public JsonResult updateOnDoorReceive(@RequestBody List<AddedReceipt> addedReceipt, @SessionAttribute Staff staff) {
        AbstractAddedOnDoor.addSetStation(addedReceipt, staff.getStation());
        return JsonResult.success(addedReceiptService.saveOrUpdateList(addedReceipt));
    }

    /**
     * <p>
     * 获取上门接宠配置
     * </p>
     *
     * @param staff session中的员工对象
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 10:47 2020/4/14
     **/
    @GetMapping("/getReceipt")
    public JsonResult getOnDoorReceipt(@SessionAttribute Staff staff) {
        return JsonResult.success(addedReceiptService.listByStationNo(staff.getStation().getStationNo()));
    }
}
