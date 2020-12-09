package com.jxywkj.application.pet.api.business;

import com.google.zxing.WriterException;
import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.service.facade.common.QrCodeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessQrImgApiController
 * @date 2019/12/13 16:57
 **/
@RestController
@RequestMapping("/api/business/qr")
@Api(description = "商家二维码")
public class BusinessQrImgApiController {
    private Logger logger = LoggerFactory.getLogger(BusinessQrImgApiController.class);

    @Resource
    QrCodeService codeService;

    @GetMapping
    public JsonResult getBusinessQr(@RequestParam("businessNo") String businessNo) {
        if (StringUtils.isBlank(businessNo)) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数为空");
        }
        String rqPath = null;
        try {
            rqPath = codeService.getBusinessQrPic(businessNo);
        } catch (WriterException | IOException e) {
            logger.error("获取商家二维码失败！");
            e.printStackTrace();
        }

        return StringUtils.isBlank(rqPath) ? JsonResult.error(Code.INSERT_ERROR, "生成二维码失败！") : JsonResult.success(rqPath);

    }
}
