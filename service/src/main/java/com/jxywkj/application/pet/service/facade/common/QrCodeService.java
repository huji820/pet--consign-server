package com.jxywkj.application.pet.service.facade.common;

import com.google.zxing.WriterException;

import java.io.IOException;

/**
 * <p>
 * 二维码
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className QrCodeService
 * @date 2019/12/13 16:08
 **/
public interface QrCodeService {
    /**
     * <p>
     * 获取商家二维码图片地址
     * </p>
     *
     * @param businessNo 商家编号
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 16:33 2019/12/13
     **/
    String getBusinessQrPic(String businessNo) throws WriterException, IOException;
}
