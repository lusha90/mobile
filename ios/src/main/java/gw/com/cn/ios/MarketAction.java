package gw.com.cn.ios;
import gw.com.cn.IosBaseAction;
import gw.com.cn.tesseract.OcrUtil;
import gw.com.cn.util.LogUtil;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class MarketAction extends IosBaseAction {

    public enum MarkeMainTabItems {
        Stock, ManageMoney, Gold
    }

    public enum MarkeTabItems {
        HuShen, BanKuai, Global, GangMei, WaiHui, More
    }

    public CheckPoint checkPoint;

    public MarketAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhIosDriver());
    }

    public void switchMarket() {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_2").click();
        this.explicitSleepForText("股票", Constant.LONG_WAIT_TIME);
    }

    public void switchMainTab(MarkeMainTabItems markeMainTabItems) {
        super.createSessionAfterTimeout();
        switch (markeMainTabItems) {
            case Stock:
                this.getDzhIosDriver().findElementByName("股票").click();
                this.explicitSleepForText("全球", Constant.LONG_WAIT_TIME);
                return;
            case ManageMoney:
                this.getDzhIosDriver().findElementByName("理财").click();
                this.explicitSleepForText("慧理财", Constant.LONG_WAIT_TIME);
                return;
            case Gold:
                this.getDzhIosDriver().findElementByName("黄金").click();
                this.explicitSleepForText("首页", Constant.LONG_WAIT_TIME);
                return;
        }
    }

    public void switchSubTab(MarkeTabItems markeTabItems) {
        super.createSessionAfterTimeout();
        switch (markeTabItems) {
            case HuShen:
                this.getDzhIosDriver().findElementByName("沪深").click();
                this.explicitSleepForText("上证指数", Constant.LONG_WAIT_TIME);
                return;
            case BanKuai:
                this.getDzhIosDriver().findElementByName("板块").click();
                this.explicitSleepForText("热点", Constant.LONG_WAIT_TIME);
                return;
            case Global:
                this.getDzhIosDriver().findElementByName("全球").click();
                this.explicitSleepForText("美国市场", Constant.LONG_WAIT_TIME);
                return;
            case GangMei:
                this.getDzhIosDriver().findElementByName("港美").click();
                this.explicitSleepForText("港股", Constant.LONG_WAIT_TIME);
                return;
            case WaiHui:
                this.getDzhIosDriver().findElementByName("外汇").click();
                this.explicitSleepForText("人民币外汇", Constant.LONG_WAIT_TIME);
                return;
            case More:
                this.getDzhIosDriver().findElementByName("更多").click();
                this.explicitSleepForText("沪深市场", Constant.LONG_WAIT_TIME);
                return;
        }
    }

    public void enterIntoSubItemViewOnMoreView(String subItemName) {
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementByName(subItemName).click();
        this.explicitSleepForText("android:id/content", Constant.LONG_WAIT_TIME);
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/head_more").click();
        this.sleep(Constant.NORMAL_WAIT_TIME);
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/head_more").click();
        this.sleep(Constant.NORMAL_WAIT_TIME);
    }

    public void expandSubItemOnMoreView(String subItemName) {
        super.createSessionAfterTimeout();
        try {
            this.getDzhIosDriver().findElementByName(subItemName);
        } catch (NoSuchElementException e) {
            this.getDzhIosDriver().findElementByClassName("android.webkit.WebView").getRect();
        }
    }

    public void enterIntoStockDetailView(){
        int offset = 1;
        super.createSessionAfterTimeout();
        for (int i = 1; i <= 10; i++) {
            this.swipeToUp(200);
        }
        for (int i = 1; i <= 11; i++) {
            this.swipeToDown(200);
        }
        this.sleep(Constant.NORMAL_WAIT_TIME);
        AndroidElement androidElement = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/table_tableLayout");
        Point point = androidElement.getLocation();
        AndroidElement title = (AndroidElement) androidElement.findElementByClassName("android.view.View");
        Point titlePoint = title.getLocation();
        int firstStock = point.getY() + titlePoint.getY() + offset;
        this.getDzhIosDriver().tap(1, point.getX() / 2, firstStock, 100);
        this.explicitSleepForText("分时", Constant.LONG_WAIT_TIME);
    }

    public int addSelfStockOnOnStockDetailView(Class testcaseClass, int number) {
        super.createSessionAfterTimeout();
        final String videoName = "/sdcard/test.mp4";
        LogUtil.getLogger().info("开始录制视频...");
        File caseScreenshotPath = this.getScreenshotPath(testcaseClass);
        int addNumber = 0;
        for (int i = 1; i <= number; i++) {
            try {
                LogUtil.getLogger().info("添加第" + i + "个自选股");
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        IosBaseAction.adb.screenRecord(videoName, 180);
                    }
                }).start();
                this.sleep(2);
                this.getDzhIosDriver().findElementByName("加自选").click();
                File snapshotFile = this.getDzhIosDriver().getScreenshotAs(OutputType.FILE);
                AndroidElement stockName = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/stockchart_info");
                Point stockPoint = stockName.getLocation();
                Dimension stockDimension = stockName.getSize();
                Rectangle rectangle = new Rectangle(stockPoint.getX(), stockPoint.getY(), stockDimension.getWidth(), stockDimension.getHeight());
                List<String> recognizedContent = OcrUtil.recognizedTextSplitResult((String)
                        this.getDzhIosDriver().getCapabilities().getCapability("testdataPath"), snapshotFile.getAbsolutePath(), rectangle);
                LogUtil.getLogger().info("添加[" + recognizedContent.get(0) + "]为自选股");
                if (this.isSelfStockOnStockDetailView()) {
                    addNumber++;
                    LogUtil.getLogger().info("添加[" + recognizedContent.get(0) + "]自选股成功");
                } else {
                    LogUtil.getLogger().info("添加[" + recognizedContent.get(0) + "]自选股失败");
                    FileUtils.copyFileToDirectory(snapshotFile, caseScreenshotPath);
                    snapshotFile.renameTo(new File(caseScreenshotPath.getAbsolutePath() + File.separator + recognizedContent.get(0) + ".png"));
                }
            } catch (NoSuchElementException e) {
                LogUtil.getLogger().info("该股票已是自选股");
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.swipeToLeft(1000);
            this.sleep(3);
            this.killScreenRecordProcessAndPullVideo(videoName, caseScreenshotPath.getAbsolutePath());
            new File(caseScreenshotPath.getAbsolutePath() + File.separator + "test.mp4").renameTo(
                    new File(caseScreenshotPath.getAbsolutePath() + File.separator + testcaseClass.getSimpleName() + "_" + i + ".mp4"));
        }
        LogUtil.getLogger().info("结束录制视频...");
        return addNumber;
    }

    private boolean isSelfStockOnStockDetailView() {
        boolean tag = false;
        super.createSessionAfterTimeout();
        try {
            this.getDzhIosDriver().findElementByName("加自选");
        } catch (NoSuchElementException e) {
            tag = true;
        }
        return tag;
    }

    public int addLatestBrowseOnStockDetailView(int number) {
        super.createSessionAfterTimeout();
        List<String> stocks = new ArrayList<String>();
        int i = 1;
        for (i = 1; i <= number - 1; i++) {
            LogUtil.getLogger().info("浏览第" + i + "个股票");
            File snapshotFile = this.getDzhIosDriver().getScreenshotAs(OutputType.FILE);
            AndroidElement stockName = (AndroidElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/stockchart_info");
            Point stockPoint = stockName.getLocation();
            Dimension stockDimension = stockName.getSize();
            Rectangle rectangle = new Rectangle(stockPoint.getX(), stockPoint.getY(), stockDimension.getWidth(), stockDimension.getHeight());
            List<String> recognizedContent = OcrUtil.recognizedTextSplitResult((String)
                            this.getDzhIosDriver().getCapabilities().getCapability("testdataPath"), snapshotFile.getAbsolutePath(), rectangle);
            stocks.add(recognizedContent.get(0));
            LogUtil.getLogger().info("浏览[" + recognizedContent.get(0) + "]股票");
            if (i > 1) {
                if (recognizedContent.get(0).equals(stocks.get(i - 2))) {
                    LogUtil.getLogger().info("已经浏览到底部,再无股票");
                    break;
                }
            }
            this.swipeToLeft(1000);
            this.sleep(3);
        }
        return i;
    }

    public enum SortBy {
        ZuiXin, ZhangFu, ChengJiaoLiang, ChengJiaoE, ZhenFu, ZhangSu, HuanShou, LiangBi, WeiBi, ShiYing, ShiJing
    }

    public void sortStockOnStockTypeView(String sortBy){
        super.createSessionAfterTimeout();
        AndroidElement androidElement = (AndroidElement) this.getDzhIosDriver().findElementByClassName("android.view.View");
        Point stockPoint = androidElement.getLocation();
        Dimension stockDimension = androidElement.getSize();
        Rectangle rectangle = new Rectangle(stockPoint.getX(), stockPoint.getY(), stockDimension.getWidth(), stockDimension.getHeight());
        File snapshotFile = this.getDzhIosDriver().getScreenshotAs(OutputType.FILE);
        List<String> recognizedContent = OcrUtil.recognizedTextSplitResult((String)
                this.getDzhIosDriver().getCapabilities().getCapability("testdataPath"), snapshotFile.getAbsolutePath(), rectangle);

    }

}
