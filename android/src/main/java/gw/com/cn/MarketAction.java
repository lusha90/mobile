package gw.com.cn;

import gw.com.cn.tesseract.OcrUtil;
import gw.com.cn.util.LogUtil;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;

import java.io.File;
import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class MarketAction extends BaseAction{

    public enum MarkeMainTabItems {
       Stock, ManageMoney, Gold
    }

    public enum MarkeTabItems {
        HuShen, BanKuai, Global, GangMei, WaiHui, More
    }

    public CheckPoint checkPoint;

    public MarketAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhAndroidDriver());
    }

    public void switchMarket(){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_2").click();
        this.explicitSleepForText("股票", Constant.LONG_WAIT_TIME);
    }

    public void switchMainTab(MarkeMainTabItems markeMainTabItems){
        super.createSessionAfterTimeout();
        switch (markeMainTabItems) {
            case Stock:
                this.getDzhAndroidDriver().findElementByName("股票").click();
                this.explicitSleepForText("全球", Constant.LONG_WAIT_TIME);
                return;
            case ManageMoney:
                this.getDzhAndroidDriver().findElementByName("理财").click();
                this.explicitSleepForText("慧理财", Constant.LONG_WAIT_TIME);
                return;
            case Gold:
                this.getDzhAndroidDriver().findElementByName("黄金").click();
                this.explicitSleepForText("首页", Constant.LONG_WAIT_TIME);
                return;
        }
    }

    public void switchSubTab(MarkeTabItems markeTabItems){
        super.createSessionAfterTimeout();
        switch (markeTabItems){
            case HuShen :
                this.getDzhAndroidDriver().findElementByName("沪深").click();
                this.explicitSleepForText("上证指数", Constant.LONG_WAIT_TIME);
                return;
            case BanKuai :
                this.getDzhAndroidDriver().findElementByName("板块").click();
                this.explicitSleepForText("热点", Constant.LONG_WAIT_TIME);
                return;
            case Global :
                this.getDzhAndroidDriver().findElementByName("全球").click();
                this.explicitSleepForText("美国市场", Constant.LONG_WAIT_TIME);
                return;
            case GangMei :
                this.getDzhAndroidDriver().findElementByName("港美").click();
                this.explicitSleepForText("港股", Constant.LONG_WAIT_TIME);
                return;
            case WaiHui :
                this.getDzhAndroidDriver().findElementByName("外汇").click();
                this.explicitSleepForText("人民币外汇", Constant.LONG_WAIT_TIME);
                return;
            case More :
                this.getDzhAndroidDriver().findElementByName("更多").click();
                this.explicitSleepForText("沪深市场", Constant.LONG_WAIT_TIME);
                return;
        }
    }

    public void enterIntoSubItemViewOnMoreView(String subItemName){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementByName(subItemName).click();
        this.explicitSleepForID("android:id/content", Constant.LONG_WAIT_TIME);
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/head_more").click();
        this.sleep(Constant.NORMAL_WAIT_TIME);
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/head_more").click();
        this.sleep(Constant.NORMAL_WAIT_TIME);
    }

    public void expandSubItemOnMoreView(String subItemName){
        super.createSessionAfterTimeout();
        try {
            this.getDzhAndroidDriver().findElementByName(subItemName);
        }catch (NoSuchElementException e){
            this.getDzhAndroidDriver().findElementByClassName("android.webkit.WebView").getRect();
        }
    }

    public int addSelfStockOnOnStocksSummaryView(int number){
        int addNumber = 0;
        int offset = 1;
        super.createSessionAfterTimeout();
        for (int i = 1; i <= 10; i++){
            this.swipeToUp(200);
        }
        for (int i = 1; i <= 11; i++){
            this.swipeToDown(200);
        }
        this.sleep(Constant.NORMAL_WAIT_TIME);
        AndroidElement androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/table_tableLayout");
        Point point = androidElement.getLocation();
        AndroidElement title = (AndroidElement) androidElement.findElementByClassName("android.view.View");
        Point titlePoint = title.getLocation();
        int firstStock = point.getY() + titlePoint.getY() + offset;
        this.getDzhAndroidDriver().tap(1, point.getX() / 2, firstStock, 100);
        for (int i = 1; i <= number; i++){
            try{
                LogUtil.getLogger().info("添加第" + i +"个自选股" );
                this.getDzhAndroidDriver().findElementByName("加自选").click();
                File snapshotFile = this.getDzhAndroidDriver().getScreenshotAs(OutputType.FILE);
                List<String> recognizedContent = OcrUtil.recognizedTextSpiltResult((String)
                        this.getDzhAndroidDriver().getCapabilities().getCapability("testdataPath"), snapshotFile.getAbsolutePath());
                LogUtil.getLogger().info("添加[" + recognizedContent.get(1) +"]为自选股" );
                if(this.isSelfStockOnStockDetailView()){
                    addNumber ++ ;
                    LogUtil.getLogger().info("添加[" + recognizedContent.get(1) +"]自选股成功" );
                }else{
                    LogUtil.getLogger().info("添加[" + recognizedContent.get(1) +"]自选股失败" );
                }
            }catch (NoSuchElementException e){
                LogUtil.getLogger().info("该股票已是自选股");
            }
            this.swipeToLeft(1000);
            this.sleep(3);
        }
        return addNumber;
    }

    private boolean isSelfStockOnStockDetailView(){
        boolean tag = false;
        super.createSessionAfterTimeout();
        try{
            this.getDzhAndroidDriver().findElementByName("加自选");
        }catch (NoSuchElementException e){
            tag = true;
        }
        return tag;
    }

    public void addLatestBrowseOnStocksSummaryView(int number){
    }

}
