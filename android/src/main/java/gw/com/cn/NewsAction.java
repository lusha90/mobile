package gw.com.cn;

import gw.com.cn.util.LogUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidElement;

import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class NewsAction extends BaseAction{

    public CheckPoint checkPoint;

    public NewsAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhAndroidDriver());
    }

    public void enterIntoNewsView(){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_3").click();
    }

    public void topOfNews(){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/bottom_menu_button_3").click();
    }

    private int browseFastNews(AndroidElement androidElement, boolean isSwipe){
        //List<MobileElement> newsOfList = androidElement.findElementsByClassName("android.widget.RelativeLayout");
        List<MobileElement> newsOfList = (List<MobileElement>) androidElement.findElementsById("com.android.dazhihui:id/news_title");
        for (MobileElement mobileElement : newsOfList) {
            LogUtil.getLogger().info("浏览[" + mobileElement.getText() + "]新闻");
            mobileElement.click();
            this.sleep(Constant.SHORT_WAIT_TIME);
            if(isSwipe){
                AndroidElement webViewAndroidElement = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/myWeb");
                AndroidElement menuAndroidElement = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/bottom_menu");
            }
            this.back();
        }
        return  newsOfList.size();
    }

    public void browseFastNews(int numberOfNews){
        super.createSessionAfterTimeout();
        int size = 0;
        AndroidElement androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/listView");
        while (size < numberOfNews){
            size = size + this.browseFastNews(androidElement, false);
            androidElement.swipe(SwipeElementDirection.UP,40, 1, 2000);
        }

    }


}
