package gw.com.cn.ios.testcase;

import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by lusha on 2016/11/28.
 */
public class DZHBaseTestCase {

    @BeforeTest
    public void setUp(){
        LogUtil.getLogger().info("start execute case : " + this.getClass().getSimpleName());
        LogUtil.getLogger().info(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test
    public void testStep(){
        LogUtil.getLogger().info(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @AfterTest
    public void tearDown() {
        LogUtil.getLogger().info(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogUtil.getLogger().info("end execute case : " + this.getClass().getSimpleName());
        System.out.println("");
    }

}
