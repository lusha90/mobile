package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_013 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        LogUtil.getLogger().info("1：null");
        selfSelectionAction.skipAdv();
    }
    @Test(description = "自选股页面换肤")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：换肤");
        selfSelectionAction.switchSkin(false);
        LogUtil.getLogger().info("3：再次换肤");
        selfSelectionAction.switchSkin(true);
    }
    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}
