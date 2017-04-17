package gw.com.cn.ios;

import gw.com.cn.IosBaseAction;
import gw.com.cn.util.LogUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.ios.IOSElement;

import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class NewsAction extends IosBaseAction {

    public CheckPoint checkPoint;

    public NewsAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhIosDriver());
    }

    public void enterIntoNewsView(){
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementByName("资讯").click();
        this.explicitSleepForText("", Constant.NORMAL_WAIT_TIME);
    }

    public void topOfNews(){
        super.createSessionAfterTimeout();
        this.getDzhIosDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_3").click();
    }

    private int browseFastNews(IOSElement iosElement, boolean isSwipe){
        //List<MobileElement> newsOfList = androidElement.findElementsByClassName("android.widget.RelativeLayout");
        List<MobileElement> newsOfList = (List<MobileElement>) iosElement.findElementsByClassName("Cell");
        for (MobileElement mobileElement : newsOfList) {
            if(mobileElement.isDisplayed()){
                List<MobileElement> texts = (List<MobileElement>)mobileElement.findElementsByClassName("StaticText");
                LogUtil.getLogger().info("浏览[" + texts.get(0).getText() + "]新闻");
                mobileElement.click();
                this.sleep(Constant.SHORT_WAIT_TIME);
                if(isSwipe){
                    IOSElement webViewAndroidElement = (IOSElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/myWeb");
                    IOSElement menuAndroidElement = (IOSElement) this.getDzhIosDriver().findElementById("com.android.dazhihui:id/bottom_menu");
                }
                this.getDzhIosDriver().findElementByClassName("Button").click();
            }

        }
        return  newsOfList.size();
    }

    public void browseFastNews(int numberOfNews){
        super.createSessionAfterTimeout();
        int size = 0;
        IOSElement iosElement = (IOSElement) this.getDzhIosDriver().findElementByClassName("Table");
        while (size < numberOfNews){
            LogUtil.getLogger().info("累计浏览新闻数量：" + size);
            size = size + this.browseFastNews(iosElement, false);
            iosElement.swipe(SwipeElementDirection.UP,4, 1, 2000);
        }

    }

}
