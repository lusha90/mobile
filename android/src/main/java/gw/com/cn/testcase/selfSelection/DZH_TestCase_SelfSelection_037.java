package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_037 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        selfSelectionAction.skipAdv();
    }

    @Test(description = "自选股页面切换到菜单栏")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：切换到菜单栏");
        selfSelectionAction.switchMenuView(true);
        selfSelectionAction.checkPoint.checkTextNotExist("大智慧");
        LogUtil.getLogger().info("3：切换到自选股首页");
        selfSelectionAction.switchMenuView(false);
        selfSelectionAction.checkPoint.checkTextExist("大智慧");
        LogUtil.getLogger().info("4：切换到菜单栏");
        selfSelectionAction.switchMenuView(true);
        selfSelectionAction.checkPoint.checkTextNotExist("大智慧");
        LogUtil.getLogger().info("5：切换到自选股首页");
        selfSelectionAction.switchMenuView(false);
        selfSelectionAction.checkPoint.checkTextExist("大智慧");
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.back();
    }
}
