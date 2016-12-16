import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by lusha on 2016/11/29.
 */
public class Test {

    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "71MBBLE23Z3R");
        capabilities.setCapability("platformVersion", "23");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.android.dazhihui");
        capabilities.setCapability("appActivity", ".Settings");

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
                capabilities);
    }
}
