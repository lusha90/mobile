package gw.com.cn;

import gw.com.cn.keyboard.DzhDigitKeyboard;
import gw.com.cn.keyboard.DzhLetterKeyboard;
import gw.com.cn.util.Adb;
import gw.com.cn.util.LogUtil;
import gw.com.cn.wait.AndroidDriverWait;
import gw.com.cn.wait.MobileExpectedCondition;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lusha on 2016/11/28.
 */
public class BaseAction {

    public static DZHInfo dzhInfo;

    private static DZHAndroidDriver dzhAndroidDriver;

    private Adb adb;

    public int width;

    public int height;

    public void initDZHInfo(DZHInfo dzhInfo_) {
        if (dzhInfo == null && dzhInfo_ != null) {
            dzhInfo = dzhInfo_;
        }
    }

    private void createDZHAndroidDriver(String deviceType) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (dzhInfo.isReplaceExistingApp()) {
            capabilities.setCapability("app", dzhInfo.getAppPath());
            capabilities.setCapability("testdataPath", dzhInfo.getTestdataPath());
        }
        List<DeviceInfo> devices = dzhInfo.getDevicesInfo();
        for (DeviceInfo device : devices) {
            if (device.getDeviceType().equals(deviceType)) {
                adb = new Adb(dzhInfo.getSdkPath(), device.getDeviceName());
                capabilities.setCapability("deviceName", device.getDeviceName());
                capabilities.setCapability("deviceBrand", device.getDeviceBrand());
                capabilities.setCapability("brandSeries", device.getBrandSeries());
                capabilities.setCapability("platformName", device.getPlatformName());
                capabilities.setCapability("platformVersion", device.getPlatformVersion());
                capabilities.setCapability("automationName", device.getAutomationName());
                capabilities.setCapability("language", device.getLanguage());
                capabilities.setCapability("locale", device.getLocale());
                capabilities.setCapability("appPackage", "com.android.dazhihui");
                capabilities.setCapability("appActivity", "com.android.dazhihui.dzh.dzh");
                capabilities.setCapability("newCommandTimeout", device.getSessionTimeout());
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
        this.waitForAdvEnd();
    }

    private void waitForAdvEnd() {
        int count = 1;
        while (true) {
            try {
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_1");
                break;
            } catch (NoSuchElementException e) {
                LogUtil.getLogger().info("第" + count + "次等待开机广告结束");
                count++;
                this.sleep(1);
            }
        }
    }

    public BaseAction(String deviceType) {
        if (this.getDzhAndroidDriver() == null) {
            this.createDZHAndroidDriver(deviceType);
        }
        width = this.getDzhAndroidDriver().manage().window().getSize().width;
        height = this.getDzhAndroidDriver().manage().window().getSize().height;
    }

    public DZHAndroidDriver getDzhAndroidDriver() {
        return dzhAndroidDriver;
    }

    public void setDzhAndroidDriver(DZHAndroidDriver dzhAndroidDriver) {
        this.dzhAndroidDriver = dzhAndroidDriver;
    }

    public void openDZHApp() {
        dzhAndroidDriver.startActivity("com.android.dazhihui", "com.android.dazhihui.dzh.dzh");
    }

    public void back() {
        this.createSessionAfterTimeout();
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.BACK);
        this.sleep(1);
    }

    public void backToHome() {
        this.createSessionAfterTimeout();
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.BACK);
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.BACK);
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.BACK);
        dzhAndroidDriver.pressKeyCode(AndroidKeyCode.HOME);
    }

    public void killDZHAppProcess() {
        return;
    }

    public void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void implicitlySleep(int sleepTime) {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().manage().timeouts().implicitlyWait(sleepTime, TimeUnit.SECONDS);
    }

    public void explicitSleepForID(final String id, int sleepTime) {
        AndroidElement androidElement = new AndroidDriverWait(this.getDzhAndroidDriver(), sleepTime)
                .until(new MobileExpectedCondition<AndroidElement>() {
                    public AndroidElement apply(AndroidDriver androidDriver) {
                        return (AndroidElement) androidDriver.findElement(By.id(id));
                    }
                });
    }

    public void explicitSleepForText(final String name, int sleepTime) {
        AndroidElement androidElement = new AndroidDriverWait(this.getDzhAndroidDriver(), sleepTime)
                .until(new MobileExpectedCondition<AndroidElement>() {
                    public AndroidElement apply(AndroidDriver androidDriver) {
                        return (AndroidElement) androidDriver.findElement(By.name(name));
                    }
                });
    }

    public void currentActivity() {
        this.getDzhAndroidDriver().currentActivity();
    }

    public void skipAdv() {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementByName("自选").click();
        this.getDzhAndroidDriver().findElementByName("自选").click();
        this.getDzhAndroidDriver().findElementByName("自选").click();
    }

    public int getScreenDensity() {
        return this.adb.getScreenDensity();
    }

    protected void createSessionAfterTimeout() {
        if (this.getDzhAndroidDriver() != null) {
            try {
                this.getDzhAndroidDriver().getSessionDetails();
            } catch (Exception e) {
                if (e.getMessage().contains("Command duration or timeout")) {
                    LogUtil.getLogger().info("session : " + this.getDzhAndroidDriver().getSessionId() + " lose efficacy ");
                    this.createDZHAndroidDriver("master");
                }
            }
        }
    }

    public void dzhKeyboardTypeContent(String content) {
        DzhDigitKeyboard dzhDigitKeyboard = new DzhDigitKeyboard(this.getDzhAndroidDriver());
        DzhLetterKeyboard dzhLetterKeyboard = new DzhLetterKeyboard(this.getDzhAndroidDriver());
        for (char symbol : content.toCharArray()) {
            try {
                if (CharUtils.isAsciiNumeric(symbol)) {
                    dzhDigitKeyboard.switchDigitKeyboard();
                    MethodUtils.invokeExactMethod(dzhDigitKeyboard, "tap_" + symbol);
                } else if (CharUtils.isAsciiAlpha(symbol)) {
                    dzhLetterKeyboard.switchLetterKeyboard();
                    MethodUtils.invokeExactMethod(dzhLetterKeyboard, "tap_" + symbol);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        int retry = 10;
        for (int i = 1; i < retry; i++) {
            try {
                LogUtil.getLogger().info("第" + i + "次等待搜索列表下出现股票");
                this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/searchListStockCode");
                break;
            } catch (NoSuchElementException e) {
                this.sleep(1);
            }
        }
    }

    public void swipeToUp(int during) {
        this.getDzhAndroidDriver().swipe(width / 2, height * 9 / 10, width / 2, height / 4, during);
    }

    public void swipeToDown(int during) {
        this.getDzhAndroidDriver().swipe(width / 2, height / 4, width / 2, height * 9 / 10, during);
    }

    public void swipeToLeft(int during) {
        this.getDzhAndroidDriver().swipe(width * 9 / 10, height / 2, width / 10, height / 2, during);
    }

    public void swipeToRight(int during) {
        this.getDzhAndroidDriver().swipe(width / 10, height / 2, width * 9 / 10, height / 2, during);
    }

}

