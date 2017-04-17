package gw.com.cn;

import gw.com.cn.keyboard.DzhDigitKeyboard;
import gw.com.cn.keyboard.DzhLetterKeyboard;
import gw.com.cn.util.Adb;
import gw.com.cn.util.LogUtil;
import gw.com.cn.wait.AndroidDriverWait;
import gw.com.cn.wait.IosDriverWait;
import gw.com.cn.wait.IosMobileExpectedCondition;
import gw.com.cn.wait.MobileExpectedCondition;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
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
public class IosBaseAction {

    public static DZHIosInfo dzhInfo;

    private static DZHIosDriver dzhIosDriver;

    public static Adb adb;

    public int width;

    public int height;

    public void initDZHInfo(DZHIosInfo dzhInfo_) {
        if (dzhInfo == null && dzhInfo_ != null) {
            dzhInfo = dzhInfo_;
        }
    }

    private void createDZHIosDriver(String deviceType) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testdataPath", dzhInfo.getTestdataPath());
        if (dzhInfo.isReplaceExistingApp()) {
            capabilities.setCapability("app", dzhInfo.getAppPath());
        }
        List<IosDeviceInfo> devices = dzhInfo.getDevicesInfo();
        for (IosDeviceInfo device : devices) {
            if (device.getDeviceType().equals(deviceType)) {
                adb = new Adb(dzhInfo.getSdkPath() + File.separator + "platform-tools" + File.separator, device.getDeviceName());
                capabilities.setCapability("deviceName", device.getDeviceName());
                capabilities.setCapability("bundleId", device.getBundleId());
                capabilities.setCapability("udid", device.getUdid());
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
            dzhIosDriver = new DZHIosDriver(url, capabilities);
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
            this.getDzhIosDriver().findElementByName("下次再说");
        } catch (NoSuchElementException e) {
            tag = false;
        }
        return tag;
    }

    private void waitForAdvEnd() {
        int count = 1;
        while (true) {
            try {
                this.getDzhIosDriver().findElementByName("自选");
                this.sleep(5);
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
                this.getDzhIosDriver().findElementByName("下次再说").click();
                break;
            } catch (NoSuchElementException e) {
                LogUtil.getLogger().info("第" + count + "次等待开机广告结束");
                count++;
                this.sleep(1);
            }
        }
    }

    public IosBaseAction(String deviceType) {
        if (this.getDzhIosDriver() == null) {
            this.createDZHIosDriver(deviceType);
        }
        width = this.getDzhIosDriver().manage().window().getSize().width;
        height = this.getDzhIosDriver().manage().window().getSize().height;
    }

    public DZHIosDriver getDzhIosDriver() {
        return dzhIosDriver;
    }

    public void setDzhAndroidDriver(DZHIosDriver dzhIosDriver) {
        this.dzhIosDriver = dzhIosDriver;
    }

    public void openDZHApp() {
    }

    public void back() {
        this.createSessionAfterTimeout();
        this.sleep(1);
    }

    public void backToHome() {
        this.createSessionAfterTimeout();
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
        this.getDzhIosDriver().manage().timeouts().implicitlyWait(sleepTime, TimeUnit.SECONDS);
    }

    public void explicitSleepForClassName(final String className, int sleepTime) {
        IOSElement iosElement = new IosDriverWait(this.getDzhIosDriver(), sleepTime)
                .until(new IosMobileExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver iosDriver) {
                        return (IOSElement) iosDriver.findElement(By.className(className));
                    }
                });
    }

    public void explicitSleepForText(final String name, int sleepTime) {
        IOSElement iosElement = new IosDriverWait(this.getDzhIosDriver(), sleepTime)
                .until(new IosMobileExpectedCondition<IOSElement>() {
                    public IOSElement apply(IOSDriver iosDriver) {
                        return (IOSElement) iosDriver.findElement(By.name(name));
                    }
                });
    }

    public void currentActivity() {}

    public void skipAdv() {
        this.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementByName("自选").click();
        this.getDzhIosDriver().findElementByName("自选").click();
        this.getDzhIosDriver().findElementByName("自选").click();
    }

    public int getScreenDensity() {
        return this.adb.getScreenDensity();
    }

    protected void createSessionAfterTimeout() {
        if (this.getDzhIosDriver() != null) {
            try {
                this.getDzhIosDriver().getSessionDetails();
            } catch (Exception e) {
                if (e.getMessage().contains("Command duration or timeout")) {
                    LogUtil.getLogger().info("session : " + this.getDzhIosDriver().getSessionId() + " lose efficacy ");
                    this.createDZHIosDriver("master");
                }
            }
        }
    }

    public void dzhKeyboardTypeContent(String content) {

    }

    public void swipeToUp(int during) {
        this.createSessionAfterTimeout();
        this.getDzhIosDriver().swipe(width / 2, height * 9 / 10, width / 2, height / 4, during);
    }

    public void swipeToDown(int during) {
        this.createSessionAfterTimeout();
        this.getDzhIosDriver().swipe(width / 2, height / 4, width / 2, height * 9 / 10, during);
    }

    public void swipeToLeft(int during) {
        this.createSessionAfterTimeout();
        this.getDzhIosDriver().swipe(width * 9 / 10, height / 2, width / 10, height / 2, during);
    }

    public void swipeToRight(int during) {
        this.createSessionAfterTimeout();
        this.getDzhIosDriver().swipe(width / 10, height / 2, width * 9 / 10, height / 2, during);
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
        //this.adb.killProcess(String.valueOf(this.getDzhIosDriver().getCapabilities().getCapability("deviceBrand")), "screenrecord");
        //this.adb.killProcess(String.valueOf(this.getDzhIosDriver().getCapabilities().getCapability("deviceBrand")), "screenrecord");
        //this.adb.killProcess(String.valueOf(this.getDzhIosDriver().getCapabilities().getCapability("deviceBrand")), "screenrecord");
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

    }

    public void closeWifi() {
        this.createSessionAfterTimeout();

    }

    public boolean networkIsAvailable() {
        return this.adb.networkIsAvailable();
    }

    public boolean textIsExist(String text){
        this.createSessionAfterTimeout();
        boolean tag = true;
        try{
            this.getDzhIosDriver().findElementByName(text);
        }catch (NoSuchElementException e){
            tag = false;
        }
        return tag;
    }

    public boolean idIsExist(String id){
        this.createSessionAfterTimeout();
        boolean tag = true;
        try{
            this.getDzhIosDriver().findElementById(id);
        }catch (NoSuchElementException e){
            tag = false;
        }
        return tag;
    }

}

