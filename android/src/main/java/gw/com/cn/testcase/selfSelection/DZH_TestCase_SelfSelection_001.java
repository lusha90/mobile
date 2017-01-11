package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_001 extends DZHBaseTestCase {

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

   @Test(description = "自选股编辑页面删除单个自选股")
    public void testStep() {
       super.testStep();
       LogUtil.getLogger().info("1：进入自选股页面");
       LogUtil.getLogger().info("2：点击搜索图标");
       selfSelectionAction.enterIntoSearchStockViewOnSelfSelectionView();
       LogUtil.getLogger().info("3：股票代码输入框输入555");
       selfSelectionAction.typeTextOnSearchStockView("555");
       LogUtil.getLogger().info("4：依次按顺序添加5个自选股");
       selfSelectionAction.addStocksOnSearchStockView(5);
       LogUtil.getLogger().info("5：返回到自选股页面");
       selfSelectionAction.back();
       selfSelectionAction.back();
       LogUtil.getLogger().info("6：点击编辑按钮进入自选股编辑页面");
       selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
       LogUtil.getLogger().info("7：删除一个自选股");
       String deleteSelfStock = selfSelectionAction.deleteSelfSelectionOrStockOnEditSelectionView();
       selfSelectionAction.checkNoExistSpecialSelfStockOnEditSelectionViewOrSelectionView(deleteSelfStock);
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
