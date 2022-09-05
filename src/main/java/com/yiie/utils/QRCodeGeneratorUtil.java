package com.yiie.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

public class QRCodeGeneratorUtil {
    private static final String CHARSET = "utf-8";

    // 二维码尺寸
    // 10^70
    // 0000000 978643 000
    private static final int QRCODE_SIZE = 300;

    public static BufferedImage createImage(String content) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                    hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * BufferedImage 转换为 base64编码
     *
     * @param bufferedImage
     * @return
     */
    public static String bufferedImageToBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", stream);
        Base64 base = new Base64();
        String base64 = base.encodeToString(stream.toByteArray());
        return "data:image/png;base64," + base64;
    }

    /**
     * base64编码 转换为 BufferedImage
     *
     * @param base64
     * @return
     */
    public static BufferedImage base64ToBufferedImage(String base64) throws IOException {
        Base64 base = new Base64();
        byte[] image = base.decode(base64.replace("data:image/png;base64,", ""));
        InputStream stream = new ByteArrayInputStream(image);
        BufferedImage bufferedImage = ImageIO.read(stream);
        return bufferedImage;
    }
}
