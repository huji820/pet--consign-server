package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.TransportTakeDetail;
import com.jxywkj.application.pet.service.facade.consign.TransportTakeDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 运输路线提货详情
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TransportTakeDetailController
 * @date 2020/4/21 10:48
 **/
@RequestMapping("/consign/transport/tale-detail")
@RestController
public class TransportTakeDetailController {
    @Resource
    TransportTakeDetailService transportTakeDetailService;

    @GetMapping("/transportNo/{transportNo:\\w+}/code/{code:\\w+}")
    public JsonResult get(@PathVariable("transportNo") int transportNo, @PathVariable(name = "code") String code) {
        return JsonResult.success(transportTakeDetailService.listByTransportNoAndCode(transportNo, code));
    }

    @PostMapping()
    public JsonResult saveOrUpdate(@RequestBody TransportTakeDetail transportTakeDetail) throws Exception {
        return JsonResult.success(transportTakeDetailService.saveOrUpdate(transportTakeDetail));
    }

    @GetMapping("/listByOrderNo")
    public JsonResult listByOrderNo(@RequestParam("orderNo")String orderNo){
        return JsonResult.success(transportTakeDetailService.listByOrderNo(orderNo));
    }
}
