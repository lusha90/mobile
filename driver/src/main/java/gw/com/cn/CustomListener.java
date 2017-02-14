package gw.com.cn;

import gw.com.cn.util.Adb;
import gw.com.cn.util.LogUtil;
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.util.List;

public class CustomListener extends TestListenerAdapter{

    private Adb adb = null;

    private void init(){
        List<DeviceInfo> devices = BaseAction.dzhInfo.getDevicesInfo();
        for ( DeviceInfo device: devices) {
            if(device.getDeviceType().equals("master")){
                adb = new Adb(BaseAction.dzhInfo.getSdkPath() + File.separator + "platform-tools" + File.separator, device.getDeviceName());
                break;
            }
        }
    }

    private void getScreenshootAndLogcat(ITestResult tr){
        String dzhReportBase = new File(".").getAbsolutePath() + File.separator + "dzhReport";
        String storeFailedCasesScreenshot = dzhReportBase + File.separator + "screenshot";
        String storeFailedCasesLogcat = dzhReportBase + File.separator + "logcat";
        String fullClassPath = tr.getInstanceName();
        String classMethod = tr.getName();
//            String str = storeFailedCasesScreenshot + File.separator + "screenshot" + File.separator +
//                    fullClassPath.replace(".", File.separator)  + File.separator + classMethod;
        String screenshotStr = storeFailedCasesScreenshot + File.separator + fullClassPath + "." + classMethod;
        String logcatStr = storeFailedCasesLogcat + File.separator + fullClassPath + "." + classMethod;
        File screenshotFile = new File(screenshotStr);
        screenshotFile.mkdirs();
        String savePath = adb.adbScreenshot("/sdcard/fail.png");
        adb.adbPull(savePath, screenshotFile.getAbsolutePath());
        File logcatFile = new File(logcatStr);
        logcatFile.mkdirs();
        adb.adbGetLogcat(logcatFile.getAbsolutePath() + File.separator + "logcat.txt");
    }

    @Override
    public void onStart(ITestContext testContext) {
        this.init();
        LogUtil.getLogger().info("start " + testContext.getName() + " test");
        super.onStart(testContext);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        LogUtil.getLogger().info("end " + testContext.getName() + " test");
        super.onFinish(testContext);
    }

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
        adb.adbClearLogcat();
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        LogUtil.getLogger().error(tr.getThrowable().toString());
        StackTraceElement[] stack = tr.getThrowable().getStackTrace();
        for (StackTraceElement stackTraceElement : stack) {
            LogUtil.getLogger().error(stackTraceElement.toString());
        }
        if(adb != null){
            this.getScreenshootAndLogcat(tr);
        }
        super.onTestFailure(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
    }

    /**
     * @param itr
     * @see IConfigurationListener#onConfigurationFailure(ITestResult)
     */
    @Override
    public void onConfigurationFailure(ITestResult itr) {
        LogUtil.getLogger().error(itr.getThrowable().toString());
        StackTraceElement[] stack = itr.getThrowable().getStackTrace();
        for (StackTraceElement stackTraceElement : stack) {
            LogUtil.getLogger().error(stackTraceElement.toString());
        }
        if(adb != null){
            this.getScreenshootAndLogcat(itr);
        }
        super.onConfigurationFailure(itr);
    }

    /**
     * @param itr
     * @see IConfigurationListener#onConfigurationSkip(ITestResult)
     */
    @Override
    public void onConfigurationSkip(ITestResult itr) {
        super.onConfigurationSkip(itr);
    }

    /**
     * @param itr
     * @see IConfigurationListener#onConfigurationSuccess(ITestResult)
     */
    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        super.onConfigurationSuccess(itr);
    }
}
