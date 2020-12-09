package com.jxywkj.application.pet.api.consign;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.enums.MediaFileTypeEnum;
import com.jxywkj.application.pet.common.utils.AliOssObjectUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.TestService;
import com.jxywkj.application.pet.service.facade.common.WeChatSubMsgService;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderTaskService;
import com.jxywkj.application.pet.service.facade.consign.StationMessageService;
import com.jxywkj.application.pet.service.spring.consign.GaoDeMapUtils;
import com.yangwang.sysframework.wechat.boot.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName TestController
 * @Author LiuXiangLin
 * @Date 2019/9/12 19:02
 * @Version 1.0
 **/
@Api(description = "测试")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    ConsignOrderTaskService consignOrderTaskService;

    @Resource
    TokenUtil tokenUtil;

    @Resource
    AliOssObjectUtils aliOssObjectUtils;

    @Resource
    WeChatSubMsgService weChatSubMsgService;

    @Resource
    GaoDeMapUtils gaoDeMapUtils;

    @ApiOperation(value = "站内信测试接口")
    @PostMapping("/message")
    public JsonResult testMessage(@RequestParam("orderNo") String orderNo) {
        return JsonResult.success(customerMessageService.saveAOrderCustomerMessage(consignOrderService.getConsignOrderByOrderNo(orderNo)));
    }

    @PostMapping("/stationMessage")
    public JsonResult testStationMessage(@RequestParam("orderNo") String orderNo) {
        return JsonResult.success(stationMessageService.saveAnOrderMessage(consignOrderService.getConsignOrderByOrderNo(orderNo)));
    }

    @GetMapping("/testQueryOrder")
    public JsonResult testQueryOrder() {
        consignOrderTaskService.checkConsignOrder();
        return JsonResult.success();
    }

    @GetMapping("/testCheckOrder")
    public JsonResult testCheckOrder() {
        consignOrderTaskService.checkConsignOrder();
        return JsonResult.success();
    }

    @GetMapping("/getWeappToken")
    public JsonResult getWeappToken() {
        try {
            return JsonResult.success(tokenUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.error(Code.ERROR, "生成失败！");
    }

    @PostMapping("/upload")
    public JsonResult testUploadAli(@RequestPart MultipartFile[] multipartFiles) throws IOException {
        aliOssObjectUtils.uploadImg(multipartFiles[0], MediaFileTypeEnum.JPG);
        return JsonResult.success();
    }


    @GetMapping("/download")
    public JsonResult getUpload() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FnHixH9ffmT2i3D9CtL";
        String accessKeySecret = "g3JfvahPWon5iWw8LxercdX5A8L4ZJ";
        String bucketName = "taochonghui";
        String objectName = "testUpload0.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
//        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("F:/文件/testUpload0.jpg"));

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest("taochonghui", "testUpload2.jpg");
        generatePresignedUrlRequest.setExpiration(new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 100));
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);

        // 关闭OSSClient。
        ossClient.shutdown();

        return JsonResult.success(url.toString());

    }

    @Resource
    TestService testService;

    @GetMapping("/orderPremium")
    public JsonResult payOrderPremium() {
        testService.payOrderPremium();
        return JsonResult.success();
    }

    @GetMapping("/wexsubmsg")
    public JsonResult weChatSubMsg(@RequestParam("orderNo") String orderNo) throws Exception {
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);
        weChatSubMsgService.inPort(order);
        return JsonResult.success();
    }

    @ApiOperation("计算经纬度")
    @GetMapping("/getLonAndLat")
    public JsonResult getLonAndLat(@RequestParam("address") String address) {
        try {
            LonAndLat lonAndLat = gaoDeMapUtils.getLonAndLat(address);
            return JsonResult.success(lonAndLat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.failed();
    }
}


