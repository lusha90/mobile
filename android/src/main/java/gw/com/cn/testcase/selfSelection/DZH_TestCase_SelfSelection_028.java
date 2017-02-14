package gw.com.cn.testcase.selfSelection;

import gw.com.cn.MarketAction;
import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_028 extends DZHBaseTestCase {

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
    @Test(description = "大数量自选股和最新浏览(自选股和最新浏览各100个)")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入市场页面，添加100个自选股");
        marketAction.switchMarket();
        marketAction.switchMainTab(MarketAction.MarkeMainTabItems.Stock);
        marketAction.switchSubTab(MarketAction.MarkeTabItems.More);
        marketAction.enterIntoSubItemViewOnMoreView("沪深A股");
        marketAction.enterIntoStockDetailView();
        int expect = marketAction.addSelfStockOnOnStockDetailView(this.getClass(), 100);
        LogUtil.getLogger().info("2：点击编辑按钮，进入编辑自选股页面");
        marketAction.back();
        marketAction.back();
        selfSelectionAction.enterIntoSelfStockView();
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        selfSelectionAction.editLatestBrowseOnEditSelectionView();
        selfSelectionAction.toggleShowLatestBrowse(false);
        selfSelectionAction.back();
        int actual = selfSelectionAction.getAllSelfSelectionStock().size();
        Assert.assertEquals(actual, expect ,"添加自选股数量一致");
        LogUtil.getLogger().info("3：进入市场页面，添加100个股票");
        marketAction.switchMarket();
        marketAction.switchMainTab(MarketAction.MarkeMainTabItems.Stock);
        marketAction.switchSubTab(MarketAction.MarkeTabItems.More);
        marketAction.enterIntoSubItemViewOnMoreView("沪深A股");
        marketAction.enterIntoStockDetailView();
        int count = marketAction.addLatestBrowseOnStockDetailView(100);
        LogUtil.getLogger().info("4：点击编辑按钮，进入编辑自选股页面");
        marketAction.back();
        marketAction.back();
        selfSelectionAction.enterIntoSelfStockView();
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        selfSelectionAction.editLatestBrowseOnEditSelectionView();
        selfSelectionAction.toggleShowLatestBrowse(true);
        actual = selfSelectionAction.getAllLatestBrowseOnEditLatestBrowseView().size();
        Assert.assertEquals(actual, count ,"最新浏览股票数量正确");
        selfSelectionAction.back();
    }
    @AfterMethod
    public void tearDown() {
        super.tearDown();
//        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
}
