package top.maplefix.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * @author : Maple
 * @description : 二维码生成工具类
 * @date : Created in 2019/11/15 14:17
 * @version : v2.1
 */
@Slf4j
public class QrCodeUtils {
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";
    /**
     * 默认生成的图片类型
     */
    private static final String FORMAT_NAME = "JPG";
    /**
     * 二维码尺寸
     */
    private static final int QRCODE_SIZE = 150;
    /**
     * LOGO宽度
     */
    private static final int WIDTH = 60;
    /**
     * LOGO高度
     */
    private static final int HEIGHT = 60;

    public static void main(String[] args) {
        log.info("开始生成二维码图片...");
        long start = System.currentTimeMillis();
        try {
            String url = "https://www.maplefix.top";
            String path = "F:\\TestFile\\qrcode1.png";
            String path2 = "F:\\TestFile\\qrcode2.png";
            String logoPath = "F:\\TestFile\\qrcode-logo.png";
            QrCodeUtils.encode(url, null, path, true);
            QrCodeUtils.encode(url, logoPath, path2, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("二维码生成完成，用时: " + (end-start) + "ms");
    }

    /**
     * 生成二维码图片流
     * @param content  二维码内容
     * @param logoPath  LOGO图片地址
     * @param needCompress 是否需要压缩
     * @return BufferedImage
     */
    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        QrCodeUtils.insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO图片
     * @param source     二维码文件缓冲流
     * @param logoPath    LOGO图片地址
     * @param needCompress 是否需要压缩
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws Exception {
        File file = new File(logoPath);
        if (!file.exists()) {
            log.error("logo文件：" + logoPath + "不存在！");
            return;
        }
        File f = new File(logoPath);
        Image src = ImageIO.read(f);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码
     * @param content  二维码内容
     * @param logoPath  LOGO文件地址
     * @param destPath 二维码存放地址
     * @param needCompress 是否需要压缩
     */
    public static void encode(String content, String logoPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    /**
     * 生成二维码
     * @param content 二维码内容
     * @param logoPath LOGO文件地址
     * @param needCompress 是否需要压缩
     * @return 二维码缓冲流
     */
    public static BufferedImage encode(String content, String logoPath, boolean needCompress) throws Exception {
        return QrCodeUtils.createImage(content, logoPath, needCompress);
    }

    /**
     * 生成二维码
     * @param content  二维码内容
     * @param logoPath  LOGO图片地址
     * @param output   输出流
     * @param needCompress 是否需要压缩
     */
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码（没有LOGO，不压缩）
     * @param content  二维码内容
     * @param output   输出流
     */
    public static void encode(String content, OutputStream output) throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, null, false);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @param destPath 文件目录
     */
    private static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
}
