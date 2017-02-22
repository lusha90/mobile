package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_020 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        LogUtil.getLogger().info("1：null");
        selfSelectionAction.skipAdv();
    }
    @Test(description = "自选股页面其他功能验证")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：点击上证");
        selfSelectionAction.enterIntoOtherView(SelfSelectionAction.SelfTitle.SHANGZHENG);
        selfSelectionAction.checkPoint.checkTextNotExist("大智慧");
        LogUtil.getLogger().info("3：返回到自选股页面");
        selfSelectionAction.back();
        LogUtil.getLogger().info("4：点击创业");
        selfSelectionAction.enterIntoOtherView(SelfSelectionAction.SelfTitle.CHUANGYE);
        selfSelectionAction.checkPoint.checkTextNotExist("大智慧");
        LogUtil.getLogger().info("5：返回到自选股页面");
        selfSelectionAction.back();
        LogUtil.getLogger().info("6：点击新闻");
        selfSelectionAction.enterIntoOtherView(SelfSelectionAction.SelfTitle.NEWS);
        selfSelectionAction.checkPoint.checkTextNotExist("大智慧");
        LogUtil.getLogger().info("7：返回到自选股页面");
        selfSelectionAction.back();
        LogUtil.getLogger().info("8：点击资金");
        selfSelectionAction.enterIntoOtherView(SelfSelectionAction.SelfTitle.MONEY);
        selfSelectionAction.checkPoint.checkTextNotExist("大智慧");
        LogUtil.getLogger().info("9：返回到自选股页面");
        selfSelectionAction.back();
    }
    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}
