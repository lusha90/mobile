package gw.com.cn.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lusha on 2016/12/12.
 * 全图比对
 */
public class ImageUtil {

    public static boolean isSame(String sourceImagePath, String targetImagePath, double percent) {
        return isSame(getImageFromFile(new File(sourceImagePath)), getImageFromFile(new File(targetImagePath)), percent);
    }

    public static boolean isSame(File sourceImageFile, File targetImageFile, double percent) {
        return isSame(getImageFromFile(sourceImageFile), getImageFromFile(targetImageFile), percent);
    }

    public static boolean isSame(BufferedImage sourceImage, BufferedImage targetImage, double percent) {
        if (targetImage.getWidth() != sourceImage.getWidth()) {
            return false;
        }
        if (targetImage.getHeight() != sourceImage.getHeight()) {
            return false;
        }
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int numDiffPixels = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (sourceImage.getRGB(x, y) != targetImage.getRGB(x, y)) {
                    numDiffPixels++;
                }
            }
        }
        double numberPixels = height * width;
        double diffPercent = numDiffPixels / numberPixels;
        LogUtil.getLogger().info("the total of pixels: " + numberPixels);
        LogUtil.getLogger().info("the diff of pixels: " + diffPercent);
        return percent <= 1.0D - diffPercent;
    }

    public static BufferedImage getSubImage(BufferedImage image, int x, int y, int w, int h) {
        return image.getSubimage(x, y, w, h);
    }

    public static boolean saveImageToFile(BufferedImage img, String format, String savePath) {
        File file = new File(savePath);
        boolean result = false;
        try {
            result = ImageIO.write(img, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static BufferedImage getImageFromFile(File f) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            StackTraceElement[] stack = e.getStackTrace();
            for (StackTraceElement stackTraceElement : stack) {
                LogUtil.getLogger().error(stackTraceElement.toString());
            }
        }
        return img;
    }

    public static void main(String[] args) {
        BufferedImage imgA = getImageFromFile(new File("C:\\Users\\Administrator\\Desktop\\A.png"));
        BufferedImage imgB = getImageFromFile(new File("C:\\Users\\Administrator\\Desktop\\B.png"));
        BufferedImage bufferedImageA = getSubImage(imgA, 200, 118, 80, 80);
        BufferedImage bufferedImageB = getSubImage(imgB, 200, 118, 80, 80);
        saveImageToFile(bufferedImageA, "png", "D:\\AT.PNG");
        saveImageToFile(bufferedImageB, "png", "D:\\BT.PNG");
        System.out.println(isSame(bufferedImageA, bufferedImageB, 0.9));
    }
}
