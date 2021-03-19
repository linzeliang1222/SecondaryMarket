package top.linzeliang.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * @Description: 图片工具类
 * @Author: LinZeLiang
 * @Date: 2021-02-11
 */
public class ImageUtil {
    /**
     * 确保图片文件的二进制格式是jpg（仅仅通过ImageIO.write(img, "jpg", file);不足以保证转换出来的jpg文件显示正常。这段转换代码，可以确保转换后jpg的图片显示正常，而不会出现暗红色( 有一定几率出现)）
     *
     * @param: file
     * @return: BufferedImage
     */
    public static BufferedImage change2jpg(File f) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            // 获取图片高度和宽度
            int width = pg.getWidth();
            int height = pg.getHeight();
            // 代表红绿蓝
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 修改图片文件的大小，将修改后的结果存储到destFile文件中
     *
     * @param: srcFile
     * @param: width
     * @param: height
     * @param: destFile
     */
    public static void resizeImage(File srcFile, int width, int height, File destFile) {
        try {
            Image i = ImageIO.read(srcFile);
            i = resizeImage(i, width, height);
            ImageIO.write((RenderedImage) i, "jpg", destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改图片的大小，返回修改后的BufferedImage对象
     *
     * @param: srcImage
     * @param: width
     * @param: height
     * @return: Image
     */
    public static Image resizeImage(Image srcImage, int width, int height) {
        try {
            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            return buffImg;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
