package gw.com.cn.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lusha on 2016/12/13.
 * 搜索比对
 */
public class ImageContainUtil {

    private BufferedImage biSource;
    private BufferedImage biTarget;
    private int widthSource;
    private int heightSource;
    private int widthTarget;
    private int heightTarget;
    private int[][] sourceRGBData;
    private int[][] targetRGBData;
    private int[][][] resultRGBData;

    public ImageContainUtil(String sourceImagePath, String targetImagePath) {
        try {
            biSource = ImageIO.read(new File(sourceImagePath));
            biTarget = ImageIO.read(new File(targetImagePath));
            widthSource = biSource.getWidth();
            heightSource = biSource.getHeight();
            widthTarget = biTarget.getWidth();
            heightTarget = biTarget.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageContainUtil(BufferedImage sourceImage, BufferedImage targetImage) {
        biSource = sourceImage;
        biTarget = targetImage;
        widthSource = biSource.getWidth();
        heightSource = biSource.getHeight();
        widthTarget = biTarget.getWidth();
        heightTarget = biTarget.getHeight();
    }

    private int[][] getImageGRB(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[][] result = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                result[h][w] = bufferedImage.getRGB(w, h) & 0xFFFFFF;
            }
        }
        return result;
    }

    private boolean isMatchAll(int y, int x) {
        int biggerY = 0;
        int biggerX = 0;
        int xor = 0;
        for (int smallerY = 0; smallerY < heightTarget; smallerY++) {
            biggerY = y + smallerY;
            for (int smallerX = 0; smallerX < widthTarget; smallerX++) {
                biggerX = x + smallerX;
                if (biggerY >= heightSource || biggerX >= widthSource) {
                    return false;
                }
                xor = targetRGBData[smallerY][smallerX] ^ sourceRGBData[biggerY][biggerX];
                if (xor != 0) {
                    return false;
                }
            }
            biggerX = x;
        }
        return true;
    }

    public void findImage() {
        targetRGBData = this.getImageGRB(biTarget);
        sourceRGBData = this.getImageGRB(biSource);
        resultRGBData = new int[heightTarget][widthTarget][2];
        for (int y = 0; y < heightSource - heightTarget; y++) {
            for (int x = 0; x < widthSource - widthTarget; x++) {
                if ((targetRGBData[0][0] ^ sourceRGBData[y][x]) == 0
                        && (targetRGBData[0][widthTarget - 1] ^ sourceRGBData[y][x + widthTarget - 1]) == 0
                        && (targetRGBData[heightTarget - 1][widthTarget - 1] ^ sourceRGBData[y + heightTarget - 1][x + widthTarget - 1]) == 0
                        && (targetRGBData[heightTarget - 1][0] ^ sourceRGBData[y + heightTarget - 1][x]) == 0) {

                    boolean isFinded = isMatchAll(y, x);
                    if (isFinded) {
                        for (int h = 0; h < heightTarget; h++) {
                            for (int w = 0; w < widthTarget; w++) {
                                resultRGBData[h][w][0] = y + h;
                                resultRGBData[h][w][1] = x + w;
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    public boolean saveFindImage(String savePath, String format){
        int startX = this.resultRGBData[0][0][1];
        int startY = this.resultRGBData[0][0][0];
        BufferedImage cutImage = this.biSource.getSubimage(startX, startY, biTarget.getWidth(), biTarget.getHeight());
        File file = new File(savePath);
        boolean result = false;
        try {
            result = ImageIO.write(cutImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void showResult() {
        for (int y = 0; y < heightTarget; y++) {
            for (int x = 0; x < widthTarget; x++) {
                //LogUtil.getLogger().info("(" + this.resultRGBData[y][x][0] + ", " + this.resultRGBData[y][x][1] + ")");
                System.out.println("(" + this.resultRGBData[y][x][1] + ", " + this.resultRGBData[y][x][0] + ")");
            }
        }
    }

    public boolean isFind() {
        boolean tag = false;
//        if (this.resultRGBData[0][0][0] != 0 && this.resultRGBData[0][0][1] != 0
//                && this.resultRGBData[heightTarget - 1][widthTarget - 1][0] != 0
//                && this.resultRGBData[heightTarget - 1][widthTarget - 1][1] != 0) {
//            tag = true;
//        }
        if (this.resultRGBData[heightTarget - 1][widthTarget - 1][0] != 0
                && this.resultRGBData[heightTarget - 1][widthTarget - 1][1] != 0) {
            tag = true;
        }
        return tag;
    }

    public static void main(String[] args) {
        ImageContainUtil iu = new ImageContainUtil("E:\\test\\a.png",
                "E:\\test\\b.png");
        iu.findImage();
        iu.showResult();
        iu.saveFindImage("E:\\cut.png", "png");
        System.out.println(iu.isFind());
    }
}
