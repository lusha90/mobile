package gw.com.cn;

import gw.com.cn.util.Adb;
import gw.com.cn.util.LogUtil;
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.util.List;

public class IosCustomListener extends TestListenerAdapter{

    private void init(){
        List<IosDeviceInfo> devices = IosBaseAction.dzhInfo.getDevicesInfo();
        for ( IosDeviceInfo device: devices) {
            if(device.getDeviceType().equals("master") && !device.getPlatformName().equalsIgnoreCase("ios")){
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
