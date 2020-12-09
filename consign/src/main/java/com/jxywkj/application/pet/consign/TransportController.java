package com.jxywkj.application.pet.consign;


import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.Pagination;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.apache.xmlbeans.impl.store.Jsr173;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author LiuXiangLin
 * @Description 网点路线
 * @Date 9:06 2019/8/27
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/consign/transport")
public class TransportController {
    @Autowired
    TransportService transportService;

    @GetMapping("")
    public Pagination listTransport(@RequestParam("endCity") String endCity, @RequestParam(value = "transportType", required = false, defaultValue = "-1") int transportType, @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "30") int pageSize, @SessionAttribute("staff") Staff staff) {
        Pagination pagination = new Pagination(pageIndex - 1, pageSize);
        pagination.setRoot(transportService.listTransport(endCity, transportType, staff.getStation().getStationNo(), pagination.getStart(), pageSize));
        pagination.setTotalCount(transportService.countTransport(endCity, transportType, staff.getStation().getStationNo()));
        return pagination;
    }

    @GetMapping("/list/start")
    public com.yangwang.sysframework.utils.JsonResult listStartCity(@RequestParam("endCity") String endCity, @RequestParam(value = "transportType", required = false, defaultValue = "-1") int transportType, @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "30") int pageSize, @SessionAttribute("staff") Staff staff) {
        return com.yangwang.sysframework.utils.JsonResult.success(transportService.listStartCity());
    }

    @PostMapping("")
    public JsonResult insertTransport(@RequestBody Transport transport, @SessionAttribute("staff") Staff staff) {
        transportService.insertTransport(transport,staff);
        return JsonResult.success("新增运输线路成功");
    }

    @PostMapping("/address")
    public JsonResult insertAddress(@SessionAttribute("staff") Staff staff,
                                    @RequestParam("province") String province,

                                    @RequestParam("startingWeight") BigDecimal startingWeight,
                                    @RequestParam("transportType") String transportType,
                                    @RequestParam("continueRetailPrice") BigDecimal continueRetailPrice,
                                    @RequestParam("startingRetailPrice") BigDecimal startingRetailPrice,
                                    @RequestParam("continueJoinPrice") BigDecimal continueJoinPrice,
                                    @RequestParam("startingJoinPrice") BigDecimal startingJoinPrice,
                                    @RequestParam("endCity") String endCity,
                                    @RequestParam("maxWeight") BigDecimal maxWeight,
                                    @RequestParam("endRegion") String endRegion) throws Exception {
        Transport transport = new Transport();
        transport.setTransportType(transportType);
        transport.setStartingWeight(startingWeight);
        transport.setContinueRetailPrice(continueRetailPrice);
        transport.setStartingRetailPrice(startingRetailPrice);
        transport.setContinueJoinPrice(continueJoinPrice);
        transport.setStartingJoinPrice(startingJoinPrice);
        transport.setEndCity(endCity);
        transport.setMaxWeight(maxWeight);
        transport.setEndRegion(endRegion);
        // 查询是否已经存在该运输路线
        List<Transport> existsTransports = transportService.listByCityAndType(staff.getStation().getCity(), endCity, TypeConvertUtil.$int(transportType));
        if (!CollectionUtils.isEmpty(existsTransports)) {
            return JsonResult.error(Code.CHECK_ERROR, "该运输路线已经存在！");
        }

        transportService.insertAddress(transport, staff, province);
        return JsonResult.success("手输线路添加成功");
    }

    @PutMapping("")
    public JsonResult updateTransportPrice(@RequestBody Transport transport) {
        transportService.updateTransport(transport);
        return JsonResult.success("修改成功");
    }

    @DeleteMapping("/{transportNo:[\\d]+}")
    public JsonResult deleteTransport(@PathVariable("transportNo") int transportNo) {
        transportService.deleteTransport(transportNo);
        return JsonResult.success("删除运输线路成功");
    }

    @PutMapping("/maxWeight")
    public JsonResult updateMaxPrice(@RequestBody Transport transport) {
        if (transport != null && transport.getMaxWeight() != null) {
            return JsonResult.success(transportService.updateTransportPrice(transport));
        }
        return JsonResult.error(Code.PARAM_ERROR, "请求参数不完整！");
    }

    @PutMapping("/startWeight")
    public JsonResult updateStartWeight(@RequestBody Transport transport) {
        if (transport != null && transport.getStartingWeight() != null) {
            return JsonResult.success(transportService.updateTransportStartWeight(transport));
        }
        return JsonResult.error(Code.PARAM_ERROR, "请求参数不完整");
    }

    /***
     * <p>
     * 通过距离查询一定范围内的所有运输路线
     * </p>
     *
     * @param staff session中的员工对象
     * @param instance 距离
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 18:06 2020/4/16
     **/
    @GetMapping("/distance")
    public JsonResult listDistance(@SessionAttribute("staff") Staff staff, @RequestParam("instance") BigDecimal instance) {
        return JsonResult.success(transportService.listByInstance(staff.getStation(), instance));
    }

}
