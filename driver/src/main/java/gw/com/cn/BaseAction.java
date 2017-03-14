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
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.ptql.ProcessFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lusha on 2016/11/28.
 */
public class BaseAction {

    public static DZHInfo dzhInfo;

    private static DZHAndroidDriver dzhAndroidDriver;

    public static Adb adb;

    public int width;

    public int height;

    public void initDZHInfo(DZHInfo dzhInfo_) {
        if (dzhInfo == null && dzhInfo_ != null) {
            dzhInfo = dzhInfo_;
        }
    }

    private void createDZHAndroidDriver(String deviceType) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testdataPath", dzhInfo.getTestdataPath());
        if (dzhInfo.isReplaceExistingApp()) {
            capabilities.setCapability("app", dzhInfo.getAppPath());
        }
        List<DeviceInfo> devices = dzhInfo.getDevicesInfo();
        for (DeviceInfo device : devices) {
            if (device.getDeviceType().equals(deviceType)) {
                adb = new Adb(dzhInfo.getSdkPath() + File.separator + "platform-tools" + File.separator, device.getDeviceName());
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
                capabilities.setCapability("updateTip", device.isUpdateTip());
                capabilities.setCapability("keyboard", device.getKeyboard());
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
        if (Boolean.parseBoolean(capabilities.getCapability("updateTip").toString())) {
            this.waitForAdvEndWithUpdateTip();
        } else {
            this.waitForAdvEnd();
        }
    }

    private boolean isShowUpdateTip() {
        boolean tag = true;
        this.createSessionAfterTimeout();
        try {
            this.getDzhAndroidDriver().findElementByName("下次再说");
        } catch (NoSuchElementException e) {
            tag = false;
        }
        return tag;
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

    private void waitForAdvEndWithUpdateTip() {
        int count = 1;
        while (true) {
            try {
                this.getDzhAndroidDriver().findElementByName("下次再说").click();
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
                dzhDigitKeyboard.switchDigitKeyboard();
                dzhDigitKeyboard.tap_search();
                this.sleep(2);
            }
        }
    }

    public void swipeToUp(int during) {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().swipe(width / 2, height * 9 / 10, width / 2, height / 4, during);
    }

    public void swipeToDown(int during) {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().swipe(width / 2, height / 4, width / 2, height * 9 / 10, during);
    }

    public void swipeToLeft(int during) {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().swipe(width * 9 / 10, height / 2, width / 10, height / 2, during);
    }

    public void swipeToRight(int during) {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().swipe(width / 10, height / 2, width * 9 / 10, height / 2, during);
    }

    public File getScreenshotPath(Class testcaseClass) {
        String dzhReportBase = new File(".").getAbsolutePath() + File.separator + "dzhReport";
        String casePath = dzhReportBase + File.separator + "screenshot" + File.separator + testcaseClass.getCanonicalName();
        LogUtil.getLogger().info(casePath);
        File dest = new File(casePath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        return dest;
    }

    public void killScreenRecordProcessAndPullVideo(String storePathForPhone, String computerPath) {
        //this.adb.killProcess(String.valueOf(this.getDzhAndroidDriver().getCapabilities().getCapability("deviceBrand")), "screenrecord");
        //this.adb.killProcess(String.valueOf(this.getDzhAndroidDriver().getCapabilities().getCapability("deviceBrand")), "screenrecord");
        //this.adb.killProcess(String.valueOf(this.getDzhAndroidDriver().getCapabilities().getCapability("deviceBrand")), "screenrecord");
        Sigar sigar = new Sigar();
        Map<String, String> map = this.getCmdlineOfProcessForPC("adb");
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = map.get(key);
            if (value.contains("screenrecord")) {
                try {
                    sigar.kill(key, 6);
                } catch (SigarException e) {
                    e.printStackTrace();
                }
            }
        }
        this.sleep(3);
        this.adb.adbPull(storePathForPhone, computerPath);
    }

    public long[] getProcessIDForPC(String processName) {
        Sigar sigar = new Sigar();
        ProcessFinder find = new ProcessFinder(new Sigar());
        long[] result = new long[0];
        try {
            result = find.find("Exe.Name.re=^.*\\\\" + processName + "(.)*.exe$");
        } catch (SigarException e) {
            e.printStackTrace();
        }
        LogUtil.getLogger().info(processName + " information: " + Arrays.toString(result).toString());
        sigar.close();
        return result;
    }

    public Map<String, String> getCmdlineOfProcessForPC(String processName) {
        Map<String, String> map = new HashedMap();
        String rtn = null;
        Sigar sigar = new Sigar();
        long[] result = this.getProcessIDForPC(processName);
        for (long item : result) {
            try {
                String[] str = sigar.getProcArgs(String.valueOf(item));
                rtn = Arrays.toString(str).replace(",", "");
                map.put(String.valueOf(item), rtn);
            } catch (SigarException e) {
                e.printStackTrace();
            }
        }
        sigar.close();
        return map;
    }

    public void openWifi() {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().openNotifications();
        String brandName = (String) this.getDzhAndroidDriver().getCapabilities().getCapability("deviceBrand");
        if (brandName.equals("google")) {
            AndroidElement androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementByClassName("android.widget.Switch");
            if (!Boolean.parseBoolean(androidElement.getAttribute("checked"))) {
                androidElement.click();
            }
        } else if (brandName.equals("oppo")) {
            try{
                AndroidElement androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementByName("WLAN");
                androidElement.click();
            }catch (NoSuchElementException e){
            }
        } else {

        }
        this.back();
    }

    public void closeWifi() {
        this.createSessionAfterTimeout();
        this.getDzhAndroidDriver().openNotifications();
        String brandName = (String) this.getDzhAndroidDriver().getCapabilities().getCapability("deviceBrand");
        if (brandName.equals("google")) {
            AndroidElement androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementByClassName("android.widget.Switch");
            if (Boolean.parseBoolean(androidElement.getAttribute("checked"))) {
                androidElement.click();
            }
        } else if (brandName.equals("oppo")) {
            try{
                this.getDzhAndroidDriver().findElementByName("WLAN");
            }catch (NoSuchElementException e){
                AndroidElement androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.systemui:id/qstv_2");
                androidElement.click();
            }
        } else {

        }
        this.back();
    }

    public boolean networkIsAvailable() {
        return this.adb.networkIsAvailable();
    }

    public boolean textIsExist(String text){
        this.createSessionAfterTimeout();
        boolean tag = true;
        try{
            this.getDzhAndroidDriver().findElementByName(text);
        }catch (NoSuchElementException e){
            tag = false;
        }
        return tag;
    }

    public boolean idIsExist(String id){
        this.createSessionAfterTimeout();
        boolean tag = true;
        try{
            this.getDzhAndroidDriver().findElementById(id);
        }catch (NoSuchElementException e){
            tag = false;
        }
        return tag;
    }

}

