package gw.com.cn;

import gw.com.cn.util.LogUtil;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class BaseAction {

    public static DZHInfo dzhInfo;

    private static DZHAndroidDriver dzhAndroidDriver;

    public void initDZHInfo(DZHInfo dzhInfo_){
        if(dzhInfo == null && dzhInfo_ != null){
            dzhInfo = dzhInfo_;
        }
    }

    private void createDZHAndroidDriver(String deviceType){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if(dzhInfo.isReplaceExistingApp()){
            capabilities.setCapability("app", dzhInfo.getAppPath());
        }
        List<DeviceInfo> devices = dzhInfo.getDevicesInfo();
        for ( DeviceInfo device: devices) {
            if(device.getDeviceType().equals(deviceType)){
                capabilities.setCapability("deviceName", device.getDeviceName());
                capabilities.setCapability("platformName", device.getPlatformName());
                capabilities.setCapability("platformVersion", device.getPlatformVersion());
                capabilities.setCapability("automationName", device.getAutomationName());
                capabilities.setCapability("language", device.getLanguage());
                capabilities.setCapability("locale", device.getLocale());
                capabilities.setCapability("appPackage", "com.android.dazhihui");
                capabilities.setCapability("appActivity", "com.android.dazhihui.dzh.dzh");
                break;
            }
        }
        try {
            URL url = new URL(dzhInfo.getAddress() + ":" + dzhInfo.getPort() + "/wd/hub");
            LogUtil.getLogger().info("restful address : " + url.toString());
            dzhAndroidDriver = new DZHAndroidDriver(url, capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public BaseAction(String deviceType) {
        if(this.getDzhAndroidDriver() == null){
            this.createDZHAndroidDriver(deviceType);
        }
    }

    public DZHAndroidDriver getDzhAndroidDriver() {
        return dzhAndroidDriver;
    }

    public void setDzhAndroidDriver(DZHAndroidDriver dzhAndroidDriver) {
        this.dzhAndroidDriver = dzhAndroidDriver;
    }

    public void openDZHApp(){
        dzhAndroidDriver.startActivity("com.android.dazhihui", "com.android.dazhihui.dzh.dzh");
    }

    public void backToHome(){
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.BACK);
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.BACK);
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.BACK);
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.HOME);
    }

    public void killDZHAppProcess(){
        return;
    }

    public void sleep(int sleepTime){
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void currentActivity(){
        this.getDzhAndroidDriver().currentActivity();
    }

    public void skipAdv(){
        this.getDzhAndroidDriver().findElementByName("自选").click();
        this.sleep(2);
        this.getDzhAndroidDriver().findElementByName("自选").click();
        this.sleep(2);
        this.getDzhAndroidDriver().findElementByName("自选").click();
        this.sleep(2);
    }

}
