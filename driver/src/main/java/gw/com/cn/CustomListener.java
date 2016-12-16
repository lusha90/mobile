package gw.com.cn;

import gw.com.cn.util.LogUtil;
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomListener extends TestListenerAdapter{
    @Override
    public void onStart(ITestContext testContext) {
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
