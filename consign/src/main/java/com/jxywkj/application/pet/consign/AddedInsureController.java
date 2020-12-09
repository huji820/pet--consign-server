package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.AddedInsure;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.AddedInsureService;
import com.jxywkj.application.pet.service.facade.consign.InsureService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName AddedInsureController
 * @Description 保价率
 * @Author LiuXiangLin
 * @Date 2019/8/5 17:22
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/insure")
public class AddedInsureController {

    @Resource
    AddedInsureService addedInsureService;

    @Resource
    InsureService insureService;

    @PutMapping("/update")
    public JsonResult update(@RequestBody AddedInsure addedInsure) {
        return JsonResult.success(addedInsureService.update(addedInsure));
    }

    @GetMapping("/get")
    public JsonResult getInsure(@SessionAttribute Staff staff){
        return JsonResult.success(addedInsureService.getByStationNo(staff.getStation().getStationNo()));
    }

    @PostMapping("")
    public com.yangwang.sysframework.utils.JsonResult insureCallback(String sn, String idCode, String pcode, String insureCode, short status) {
        insureService.updateInsureCode(sn,insureCode,status);
        return com.yangwang.sysframework.utils.JsonResult.success("回调成功");
    }
}
