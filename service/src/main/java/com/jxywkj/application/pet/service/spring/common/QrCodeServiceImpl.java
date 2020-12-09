package com.jxywkj.application.pet.service.spring.common;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.utils.QrCodeUtils;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className QrCodeServiceImpl
 * @date 2019/12/13 16:34
 **/
@Service
public class QrCodeServiceImpl implements QrCodeService {
    private QrCodeUtils qrCodeUtils;

    private BusinessService businessService;

    private String basePath;

    private String pictureHost;

    /**
     * 微信自定义扫码跳转链接
     */
    private static final String BASE_QR_CODE_CONTENT = "https://consign.taochonghui.com/weapp/jump/share?";

    /**
     * 商家图片地址
     */
    private static final String BUSINESS_QR_PATH = "/qr/business/";

    /**
     * logo图标地址
     */
    private static final String LOGO_PATH = "/qr/logo/taochonghuiLogo.png";

    /**
     * 点 ======> .
     */
    private static final String DOT = ".";

    /**
     * 文件夹路径
     */
    private File basePathFile;

    /**
     * 图标文件
     */
    private File logoFile;


    @Autowired
    public QrCodeServiceImpl(QrCodeUtils qrCodeUtils,
                             BusinessService businessService,
                             @Value("${file.path}") String basePath,
                             @Value("${pictureHost}") String pictureHost) {

        this.qrCodeUtils = qrCodeUtils;
        this.businessService = businessService;
        this.basePath = basePath;
        this.pictureHost = pictureHost;
        init();
    }

    /**
     * <p>
     * 创建文件夹路径
     * </p>
     *
     * @author LiuXiangLin
     * @date 8:57 2019/12/16
     **/
    private void init() {
        basePathFile = new File(basePath + BUSINESS_QR_PATH);
        logoFile = new File(basePath + LOGO_PATH);
    }

    @Override
    public String getBusinessQrPic(String businessNo) throws WriterException, IOException {
        // 获取商家
        Business business = businessService.getByBusinessNo(businessNo, BusinessStateEnum.NORMAL);
        if (business == null) {
            return null;
        }

        // 创建文件 有商家编号为图片名称
        File file = new File(basePath + BUSINESS_QR_PATH + businessNo + DOT + QrCodeUtils.DEFAULT_FORMAT);

        // 如果图片不存在
        if (!file.exists() || !file.isFile()) {
            // 如果文件夹不存在
            if (!basePathFile.exists()) {
                if (!basePathFile.mkdirs()) {
                    throw new IOException("创建路径失败！路径为：" + basePathFile.getPath());
                }
            }
            // 创建图片
            if (file.createNewFile()) {
                // 二维码内容
                String qrContent = BASE_QR_CODE_CONTENT + "type=rqimg&businessno=" + businessNo;
                // 如果存在图标则生成带图标的二维码
                if (logoFile.exists() && logoFile.isFile()) {
                    // 生成带logo的二维码
                    ImageIO.write(qrCodeUtils.createQrCodeWithLogo(qrContent, logoFile), QrCodeUtils.DEFAULT_FORMAT, file);
                } else {
                    // 生成不带logo的二维码
                    BitMatrix bitMatrix = qrCodeUtils.createQrCodeBitMatrix(qrContent);
                    MatrixToImageWriter.writeToPath(bitMatrix, QrCodeUtils.DEFAULT_FORMAT, file.toPath());
                }
            } else {
                throw new IOException("创建文件失败！文件名称为：" + file.getPath());
            }
        }

        return pictureHost + BUSINESS_QR_PATH + businessNo + DOT + QrCodeUtils.DEFAULT_FORMAT;
    }
}
