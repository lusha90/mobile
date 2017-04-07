package gw.com.cn;

import gw.com.cn.util.LogUtil;
import io.appium.java_client.android.AndroidElement;

/**
 * Created by lusha on 2016/11/28.
 */
public class PersonalCenterAction extends BaseAction{

    public CheckPoint checkPoint;

    public PersonalCenterAction(String deviceType) {
        super(deviceType);
        checkPoint = new CheckPoint(this.getDzhAndroidDriver());
    }

    public boolean isLogin(){
        super.createSessionAfterTimeout();
        return this.textIsExist("点击登录");
    }

    public void logout(){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/messageBtn").click();
        if(this.idIsExist("com.android.dazhihui:id/user_img_iv_old")){
            this.getDzhAndroidDriver().findElementByName("设置").click();
            this.getDzhAndroidDriver().findElementByName("退出登录").click();
            this.getDzhAndroidDriver().findElementByName("确定").click();
        }else{
            LogUtil.getLogger().info("账户已经注销");
        }
    }

    public void login(){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/messageBtn").click();
        if(this.textIsExist("点击登录")){
            this.getDzhAndroidDriver().findElementByName("点击登录").click();
            this.explicitSleepForText("大智慧", Constant.NORMAL_WAIT_TIME);
            this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/dzhLogin").click();
            this.explicitSleepForID("com.android.dazhihui:id/confirmBtn", Constant.NORMAL_WAIT_TIME);
            AndroidElement androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/loginNick");
            androidElement.clear();
            String username = accounts.get(0).getAccountID();
            String password = accounts.get(0).getAccountPwd();
            androidElement.sendKeys(username);
            androidElement = (AndroidElement) this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/loginPwd");
            androidElement.clear();
            androidElement.sendKeys(password);
            this.getDzhAndroidDriver().findElementById("com.android.dazhihui:id/confirmBtn").click();
            this.explicitSleepForText("自选", Constant.LONG_WAIT_TIME);
        }else{
            LogUtil.getLogger().info("账户已经登录");
        }
    }

    public void webview(){
        super.createSessionAfterTimeout();
        this.getDzhAndroidDriver().getContextHandles();
        this.getDzhAndroidDriver().context("WEBVIEW_com.android.dazhihui");
        this.getDzhAndroidDriver().getKeyboard().sendKeys();
    }
}