package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderCallBackService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className StationApiController
 * @description 站点信息
 * @date 2019/10/14 10:56
 **/
@RestController
@RequestMapping("/station/api")
@Api(description = "站点信息")
public class StationApiController {
    @Resource
    StationService stationService;

    @Resource
    OrderCallBackService orderCallBackService;

    @Resource
    ConsignOrderService consignOrderService;

    @ApiOperation("添加站点收款码图片")
    @PostMapping("/uploadQR")
    public JsonResult addOrderMedia(@RequestPart MultipartFile[] multipartFiles, @RequestParam String stationNo) throws IOException {
        String imgAddress = stationService.addStationMedia(stationNo, multipartFiles);
        return JsonResult.success(imgAddress);
    }

    @ApiOperation(value = "通过站点编号查询数据")
    @GetMapping("/get/phone")
    public JsonResult getByStationNo(@RequestParam("stationNo") Integer stationNo) {
        if (stationNo == null) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数为空");
        }
        return JsonResult.success(stationService.getPhoneByStationNo(stationNo));
    }

    @ApiOperation(value = "完成支付")
    @GetMapping("/completePayment")
    public JsonResult completePayment(@RequestParam("orderNo")String orderNo,@RequestParam("customerNo")String customerNo){
        try {
            orderCallBackService.payConsignOrder(orderNo);
            //添加线下支付订单标识
            consignOrderService.updateOfflinePayment(orderNo);
        } catch (Exception e) {
            return JsonResult.failed();
        }
        return JsonResult.success();
    }

    @ApiOperation("根据位置获取站点列表")
    @GetMapping("/listByPosition")
    public JsonResult getStationByPosition(@RequestParam("longitude") double longitude,
                                            @RequestParam("latitude") double latitude,
                                            @RequestParam("limit") int limit,
                                            @RequestParam("offset") int offset) {
        return JsonResult.success(stationService.listByPosition(longitude, latitude, offset, limit));
    }

    @ApiOperation("通过省份获取省下面的所有站点")
    @GetMapping("/listByProvince")
    public JsonResult listByProvince(@RequestParam("province")String province){
        List<Station> stations = stationService.listByProvince(province);
        return JsonResult.success(stations);
    }

    @ApiOperation("查询通过城市分组的站点数据")
    @GetMapping("/listGroupByCity")
    public JsonResult listGroupByCity(){
        List<Station> stations = stationService.listGroupByCity();
        return JsonResult.success(stations);
    }

    @ApiOperation("排序获取城市的所有站点")
    @GetMapping("/listStationByCity")
    public JsonResult listStationByCity(@RequestParam("lng")double lng,
                                        @RequestParam("lat")double lat,
                                        @RequestParam("city")String city,
                                        String distanceSort,String orderNum,
                                        String serviceEval){
        List<Station> stations = stationService.listStationByCity(lng, lat, distanceSort, orderNum, serviceEval, city);
        return JsonResult.success(stations);
    }

    @ApiOperation("判断站点是否属于城市 true 属于  false 不属于")
    @GetMapping("/stationBelongToCity")
    public JsonResult stationBelongToCity(@RequestParam("city")String city,
                                          @RequestParam("stationNo")String stationNo){
        boolean b = stationService.stationBelongToCity(city, stationNo);
        return JsonResult.success(b);
    }
}
