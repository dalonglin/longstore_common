package com.longstore.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 参考https://github.com/kenglxn/QRGen
 */
public class QRCodeUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(QRCodeUtil.class);

    public static byte[] code_2d(String content, int width, int height) {
        try {
            ErrorCorrectionLevel level = ErrorCorrectionLevel.H;
            //生成二维码中的设置
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); //编码
            hints.put(EncodeHintType.ERROR_CORRECTION, level); //容错率
            hints.put(EncodeHintType.MARGIN, 3);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints); //生成bitMatrix
            BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(bi, "jpg", out);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {

                    }
                }
            }
            return out.toByteArray();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
