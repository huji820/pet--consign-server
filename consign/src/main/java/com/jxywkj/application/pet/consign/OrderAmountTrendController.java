package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.OrderAmountTrend;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.params.OrderAmountTrendDTO;
import com.jxywkj.application.pet.service.facade.consign.OrderAmountTrendService;
import com.yangwang.sysframework.utils.JsonUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderAmountTrendController
 * @date 2020/1/6 17:12
 **/
@RestController
@RequestMapping("/consign/order-trend")
public class OrderAmountTrendController {
    @Resource
    OrderAmountTrendService orderAmountTrendService;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(OrderAmountTrendController.class);

    @GetMapping("")
    public JsonResult list(@Param("params") String params, @SessionAttribute("staff") Staff staff) {
        OrderAmountTrendDTO orderAmountTrendDTO = null;
        if (StringUtils.isNotBlank(params)) {
            try {
                orderAmountTrendDTO = JsonUtil.formObject(params, OrderAmountTrendDTO.class);
            } catch (Exception e) {
                logger.error("json转换异常");
                e.printStackTrace();
            }
            if (orderAmountTrendDTO != null) {
                return JsonResult.success(orderAmountTrendService.listByParam(orderAmountTrendDTO, staff));
            }
        }
        return JsonResult.error(Code.PARAM_ERROR, "请求参数错误");
    }
}
