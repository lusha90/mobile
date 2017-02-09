package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DZH_TestCase_SelfSelection_011 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        LogUtil.getLogger().info("1：删除所以自选股");
        selfSelectionAction.skipAdv();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
    @Test(description = "单个自选股置顶")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：点击搜索图标");
        selfSelectionAction.enterIntoSearchStockViewOnSelfSelectionView();
        LogUtil.getLogger().info("3：股票代码输入框输入555");
        selfSelectionAction.typeTextOnSearchStockView("555");
        LogUtil.getLogger().info("4：依次按顺序添加5个自选股");
        List<String> stocks = selfSelectionAction.addStocksOnSearchStockView(5);
        LogUtil.getLogger().info("5：返回到自选股页面");
        selfSelectionAction.back();
        selfSelectionAction.back();
        LogUtil.getLogger().info("6：长按海航创新后选择置顶");
        selfSelectionAction.selfStockOperatorOnSelectionView(stocks.get(0), SelfSelectionAction.StockOperator.TOP);
        selfSelectionAction.checkSelfStockTop(stocks.get(0));
        LogUtil.getLogger().info("7：长按海航创新后选择取消置顶");
        selfSelectionAction.selfStockOperatorOnSelectionView(stocks.get(0), SelfSelectionAction.StockOperator.CANCELTOP);
        selfSelectionAction.selfStockOperatorOnSelectionView(stocks.get(1), SelfSelectionAction.StockOperator.TOP);
        selfSelectionAction.checkSelfStockTop(stocks.get(1));
    }
    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
}
