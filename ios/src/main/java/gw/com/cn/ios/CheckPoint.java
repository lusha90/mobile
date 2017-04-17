package gw.com.cn.ios;

import gw.com.cn.DZHIosDriver;
import gw.com.cn.util.LogUtil;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

/**
 * Created by lusha on 2017/1/9.
 */
public class CheckPoint extends Assert{

    private DZHIosDriver dzhiosDriver;

    public CheckPoint(DZHIosDriver dzhAndroidDriver) {
        this.dzhiosDriver = dzhAndroidDriver;
    }

    public void checkTextExist(String text){
        this.dzhiosDriver.findElementsByName(text);
        LogUtil.getLogger().info(" <<<<<<<<<< 检查当前页面存在[" + text + "]关键字 >>>>>>>>>>>");
    }

    public void checkTextNotExist(String text){
        boolean tag = false;
        try {
            this.dzhiosDriver.findElementByName(text);
        }catch (NoSuchElementException e){
            tag = true;
        }
        LogUtil.getLogger().info(" <<<<<<<<<< 检查当前页面不存在[" + text + "]关键字 >>>>>>>>>>>");
        Assert.assertEquals(true, tag, " <<<<<<<<<< 检查当前页面不存在[" + text + "]关键字 >>>>>>>>>>>");
    }

    public void checkIDExist(String id){
        this.dzhiosDriver.findElementById(id);
        LogUtil.getLogger().info(" <<<<<<<<<< 检查当前页面存在[" + id + "]标识 >>>>>>>>>>>");
    }

    public void checkIDNotExist(String id){
        boolean tag = false;
        try {
            this.dzhiosDriver.findElementById(id);
        }catch (NoSuchElementException e){
            tag = true;
        }
        LogUtil.getLogger().info(" <<<<<<<<<< 检查当前页面不存在[" + id + "]标识 >>>>>>>>>>>");
        Assert.assertEquals(true, tag, " <<<<<<<<<< 检查当前页面不存在[" + id + "]标识 >>>>>>>>>>>");
    }


}
