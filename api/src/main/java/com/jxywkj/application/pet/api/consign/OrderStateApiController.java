package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles;
import com.jxywkj.application.pet.model.consign.params.OrderInOrOutPutParam;
import com.jxywkj.application.pet.service.facade.consign.OrderStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OrderStateAPIController
 * @Description 订单详情
 * @Author LiuXiangLin
 * @Date 2019/8/8 9:50
 * @Version 1.0
 **/
@Api(description = "订单运输状态")
@RestController
@RequestMapping("/api/consign/orderState")
public class OrderStateApiController {

    @Resource
    OrderStateService orderStateService;

    @ApiOperation("上传文件（已经弃用）")
    @PostMapping("/uploadFile")
    @Deprecated
    public JsonResult uploadFile(@RequestPart MultipartFile[] multipartFiles, @RequestParam String orderNo) throws IOException {
        return JsonResult.success(orderStateService.uploadOrderMedia(orderNo, multipartFiles));
    }

    @PostMapping("/uploadMediaFiles")
    @ApiOperation("添加订单图片和视频")
    public JsonResult addOrderMedia(@RequestPart MultipartFile[] multipartFiles, @RequestParam String orderNo,
                                    String node, Long delayTime, String remarks) throws IOException {
        List<OrderStateTempFiles> orderStateTempFilesList = orderStateService.addOrderMedia(orderNo, multipartFiles,node,delayTime,remarks);
        return CollectionUtils.isEmpty(orderStateTempFilesList) ? JsonResult.error(Code.ERROR, "新增失败！") : JsonResult.success(orderStateTempFilesList);
    }

    @ApiOperation("出港或入港")
    @PostMapping("/inOrOutPort")
    public JsonResult intPort(@RequestBody OrderInOrOutPutParam orderInOrOutPutParam) {
        if(orderInOrOutPutParam.getDelayedTime()==null){
            orderInOrOutPutParam.setDelayedTime(0L);
        }
        //创建一个线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                int i = orderStateService.addInPortState(
                        orderInOrOutPutParam.getFileList(),
                        orderInOrOutPutParam.getOrderType(),
                        orderInOrOutPutParam.getOrderNo(),
                        orderInOrOutPutParam.getSn());
            }
        }, orderInOrOutPutParam.getDelayedTime(), TimeUnit.MILLISECONDS);

        return JsonResult.success(1);
    }

    @ApiOperation("获取该订单的所有的位置数据")
    @GetMapping("/selectByOrderNo")
    public JsonResult selectByOrderNo(@RequestParam("orderNo")String orderNo){
        return JsonResult.success(orderStateService.selectByOrderNo(orderNo));
    }
}
