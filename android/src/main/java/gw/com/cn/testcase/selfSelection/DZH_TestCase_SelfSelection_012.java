package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DZH_TestCase_SelfSelection_012 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        LogUtil.getLogger().info("1：删除所有自选股");
        selfSelectionAction.skipAdv();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }

    @Test(description = "自选股成交量排序后删除自选股再查看成交量排序")
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
        selfSelectionAction.switchSortType(SelfSelectionAction.SortType.CHENGJIAOLIANG);
        LogUtil.getLogger().info("6：点击成交量按钮进行降序排序");
        selfSelectionAction.increaseSortOnSelfSelectionView(true);
        selfSelectionAction.checkIncreaseSortOnSelfSelectionView(true);
        LogUtil.getLogger().info("7：长按某个自选股删除它");
        selfSelectionAction.selfStockOperatorOnSelectionView(stocks.get(0), SelfSelectionAction.StockOperator.DEL);
        stocks.remove(0);
        selfSelectionAction.checkIncreaseSortOnSelfSelectionView(true);
        LogUtil.getLogger().info("8：点击成交量按钮进行升序排序");
        selfSelectionAction.increaseSortOnSelfSelectionView(false);
        selfSelectionAction.checkIncreaseSortOnSelfSelectionView(false);
        LogUtil.getLogger().info("9：长按某个自选股删除它");
        selfSelectionAction.selfStockOperatorOnSelectionView(stocks.get(2), SelfSelectionAction.StockOperator.DEL);
        stocks.remove(2);
        selfSelectionAction.checkIncreaseSortOnSelfSelectionView(false);
        LogUtil.getLogger().info("10：点击成交量按钮进行取消排序");
        selfSelectionAction.cancelSortOnSelfSelectionView();
        selfSelectionAction.checkAddOrderOfSelfStocks(stocks);
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
}
