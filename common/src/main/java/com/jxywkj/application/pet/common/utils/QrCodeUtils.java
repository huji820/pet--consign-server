package com.jxywkj.application.pet.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 二维码工具类
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className QrCodeUtils
 * @date 2019/12/13 15:40
 **/
@Component
public class QrCodeUtils {
    /**
     * 二维码宽度
     */
    private static final int WIDTH = 500;
    /**
     * 二维码高度
     */
    private static final int HEIGHT = 500;

    /**
     * 默认编码格式
     */
    private static final String DEFAULT_ENCODING = "utf-8";

    /**
     * 默认生成的文件格式
     */
    public static final String DEFAULT_FORMAT = "png";

    /**
     * <p>
     * 获取二维码对象（不带logo）
     * </p>
     *
     * @param qrContent 二维码内容
     * @return com.google.zxing.common.BitMatrix
     * @author LiuXiangLin
     * @date 16:03 2019/12/13
     **/
    public BitMatrix createQrCodeBitMatrix(String qrContent) throws WriterException {
        // 二维码的参数
        Map<EncodeHintType, Object> hints = new HashMap<>(5);
        // 编码格式
        hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_ENCODING);
        // 容错级别：L级：约可纠错7%的数据码字,M级：约可纠错15%的数据码字,Q级：约可纠错25%的数据码字,H级：约可纠错30%的数据码字
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        // 外边距大小
        hints.put(EncodeHintType.MARGIN, 1);
        //生成二维码
        return new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
    }

    /**
     * <p>
     * 创建带logo的二维码
     * </p>
     *
     * @param qrContent 二维码内容
     * @param logoFile  图标文件对象
     * @return java.awt.image.BufferedImage
     * @author LiuXiangLin
     * @date 10:16 2019/12/21
     **/
    public BufferedImage createQrCodeWithLogo(String qrContent, File logoFile) {
        BufferedImage combined;
        try {
            // 创建二维码对象
            BufferedImage qrCodeImg = createQrCode(qrContent);
            // 读取图标
            BufferedImage logo = ImageIO.read(logoFile);
            // 计算logo宽高
            int deltaHeight = HEIGHT - logo.getHeight();
            int deltaWidth = WIDTH - logo.getWidth();
            // 重新生成一个IO
            combined = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            // 使用绘图工具
            Graphics2D g = (Graphics2D) combined.getGraphics();
            // 将二维码画上
            g.drawImage(qrCodeImg, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            // 将图标话上去
            g.drawImage(logo, deltaWidth / 2, deltaHeight / 2, null);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return combined;
    }


    /**
     * <p>
     * 创建二维码对象
     * </p>
     *
     * @param qrContent 二维码内容
     * @return java.awt.image.BufferedImage
     * @author LiuXiangLin
     * @date 10:25 2019/12/21
     **/
    private static BufferedImage createQrCode(String qrContent) {
        BitMatrix matrix;
        try {
            // 设置QR二维码参数
            Map<EncodeHintType, Object> hints = new HashMap<>(2);
            // 纠错级别（H为最高级别）
            // L级：约可纠错7%的数据码字
            // M级：约可纠错15%的数据码字
            // Q级：约可纠错25%的数据码字
            // H级：约可纠错30%的数据码字
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 字符集
            hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_ENCODING);
            // 边框，(num * 10)
            hints.put(EncodeHintType.MARGIN, 0);
            // 编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            matrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
