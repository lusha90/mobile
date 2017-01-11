package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DZH_TestCase_SelfSelection_003 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        selfSelectionAction.skipAdv();
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
        selfSelectionAction.back();
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        selfSelectionAction.editLatestBrowseOnEditSelectionView();
        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
        selfSelectionAction.back();
    }

    @Test(description = "自选股编辑")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：点击搜索图标");
        selfSelectionAction.enterIntoSearchStockViewOnSelfSelectionView();
        LogUtil.getLogger().info("3：股票代码输入框输入555");
        selfSelectionAction.typeTextOnSearchStockView("555");
        LogUtil.getLogger().info("4：添加1个自选股");
        List<String> selfStock = selfSelectionAction.addStocksOnSearchStockView(1);
        LogUtil.getLogger().info("5：返回到自选股页面");
        selfSelectionAction.checkPoint.checkTextExist(selfStock.get(0));
        selfSelectionAction.back();
        selfSelectionAction.back();
        LogUtil.getLogger().info("6：点击编辑按钮进入自选股编辑页面");
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        selfSelectionAction.checkPoint.checkTextExist(selfStock.get(0));
        LogUtil.getLogger().info("7：点击搜索图标");
        selfSelectionAction.enterIntoSearchStockViewOnEditSelfSelectionView();
        selfSelectionAction.typeTextOnSearchStockView("555");
        selfSelectionAction.checkStockIsSelfOnSearchStockView(selfStock.get(0));
        LogUtil.getLogger().info("8：删除该自选股");
        selfSelectionAction.back();
        selfSelectionAction.back();
        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
        selfSelectionAction.checkNoExistSelfStockOnEditSelectionView();
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.back();
        selfSelectionAction.back();
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
        selfSelectionAction.back();
    }
}
