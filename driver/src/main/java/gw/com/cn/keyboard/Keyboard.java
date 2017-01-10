package gw.com.cn.keyboard;

import gw.com.cn.DZHAndroidDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by lusha on 2017/1/9.
 */
public class Keyboard {

    int heightOffset;

    public Keyboard(DZHAndroidDriver dzhAndroidDriver) {
        String deviceBrand = (String) dzhAndroidDriver.getCapabilities().getCapability("deviceBrand");
        String brandSeries = (String) dzhAndroidDriver.getCapabilities().getCapability("brandSeries");
        String imagePath = "/picture" + File.separator + deviceBrand + File.separator +
                brandSeries + File.separator + "keyboard" + File.separator + "keyboardLetter.png";
        try {
            Image image = ImageIO.read(this.getClass().getResource(imagePath));
            int height = image.getHeight(null);
            heightOffset = height / 2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
