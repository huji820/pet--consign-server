package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderLedgerService;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import com.yangwang.sysframework.utils.*;
import com.yangwang.sysframework.utils.office.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-18 21:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/consign/order/ledger")
public class OrderLedgerController {

    @Autowired
    ExcelUtil excelUtil;

    @Autowired
    ConsignOrderLedgerService consignOrderLedgerService;

    @Autowired
    TransportService transportService;

    @Autowired
    ConsignOrderService consignOrderService;


    @GetMapping("")
    public Pagination listOrderLedger(String startDate, String endDate, String endCity,
                                      String petTypeName,
                                      String senderName, String senderPhone,
                                      String receiverName, String receiverPhone,
                                      boolean sync, int pageIndex, int pageSize,
                                      @SessionAttribute("staff") Staff staff) {
        Pagination pagination = new Pagination(pageIndex - 1, pageSize);

        pagination.setRoot(consignOrderLedgerService.listOrderLedger(staff.getStation().getStationNo(), startDate, endDate, endCity, petTypeName, senderName, senderPhone, receiverName, receiverPhone, sync, pagination.getStart(), pageSize));
        pagination.setTotalCount(consignOrderLedgerService.countOrderLedger(staff.getStation().getStationNo(), startDate, endDate, endCity, petTypeName, senderName, senderPhone, receiverName, receiverPhone, sync));
        return pagination;
    }

    @PutMapping
    public JsonResult updateOrderLedger(@RequestBody OrderLedger orderLedger) {
        consignOrderLedgerService.updateOrderLedger(orderLedger);
        return JsonResult.success("修改账单成本金额成功");
    }

    @DeleteMapping("/{orderNo}")
    public JsonResult deleteOrderLedger(@PathVariable("orderNo") String orderNo, @SessionAttribute("staff") Staff staff) throws Exception {
        consignOrderLedgerService.deleteOrderLedger(orderNo, staff.getStation().getStationNo());
        return JsonResult.success("删除成功");
    }

    @PostMapping("")
    public JsonResult importExcel(@RequestBody List<OrderLedger> orderLedgers,
                                  @SessionAttribute("staff") Staff staff) throws Exception {

        consignOrderLedgerService.insertAndSyncOrderLedger(orderLedgers, staff.getStation());

        return JsonResult.success("");
    }

    /*@PostMapping("")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file, @SessionAttribute("staff") Staff staff) throws Exception {
        List<Object[]> excelList = excelUtil.read(file.getInputStream(), file.getOriginalFilename(), 2);

        OrderLedger[] ledgers = new OrderLedger[excelList.size()];
        OrderLedger ledger = null;

        for (int i = 0; i < excelList.size(); i++) {

            ledger = new OrderLedger(getOrderNo(StringUtil.$Str(excelList.get(i)[0]), getCityShortName(StringUtil.$Str(excelList.get(i)[1]))),
                    staff.getStation(),
                    StringUtil.$Str(excelList.get(i)[0]), StringUtil.$Str(excelList.get(i)[0]), "", StringUtil.$Str(excelList.get(i)[1]), StringUtil.$Str(excelList.get(i)[2]),
                    new PetSort(1, StringUtil.$Str(excelList.get(i)[3])),
                    new PetGenre(1, ""), TypeConvertUtil.$BigDecimal(excelList.get(i)[6]), TypeConvertUtil.$int(excelList.get(i)[4]),
                    StringUtil.$Str(excelList.get(i)[7]), StringUtil.$Str(excelList.get(i)[8]), StringUtil.$Str(excelList.get(i)[9]), StringUtil.$Str(excelList.get(i)[10]),
                    TypeConvertUtil.$BigDecimal(excelList.get(i)[11]), new BigDecimal(0), false);

            ledgers[i] = ledger;

            ledger = null;
        }

        consignOrderLedgerService.insertOrderLedger(ledgers);

        return JsonResult.success("");
    }*/

    @PostMapping("ledger2Order")
    public JsonResult ledger2Order(@RequestBody OrderLedger orderLedger, @SessionAttribute("staff") Staff staff) throws Exception {
        Order order = consignOrderService.getOrder(orderLedger.getOrderNo());
        if (order != null) {
            return JsonResult.err(2, "订单已同步，或者单号重复");
        }

        Transport transport = transportService.getTransport(staff.getStation().getStationNo(), staff.getStation().getStartCity(), orderLedger.getEndCity(), orderLedger.getTransportType());
        if (transport == null) {
            return JsonResult.err(1, "运输路线不存在，请检查目的城市输入是否正确");
        }

        orderLedger.setStation(staff.getStation());
        consignOrderService.insertConsignLedgerOrder(orderLedger, transport);
        return JsonResult.success("同步订单成功");
    }
}
