package gw.com.cn.testcase.selfSelection;

import gw.com.cn.MarketAction;
import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_027 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;
    private MarketAction marketAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        marketAction = new MarketAction("master");
        LogUtil.getLogger().info("1：null");
        selfSelectionAction.skipAdv();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
    @Test(description = "大数量最新浏览(100个自选股)")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入市场页面");
        marketAction.switchMarket();
        marketAction.switchMainTab(MarketAction.MarkeMainTabItems.Stock);
        marketAction.switchSubTab(MarketAction.MarkeTabItems.More);
        marketAction.enterIntoSubItemViewOnMoreView("沪深A股");
        marketAction.addLatestBrowseOnStocksSummaryView(100);
        LogUtil.getLogger().info("2：点击编辑按钮，进入编辑自选股页面");
    }
    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
}
