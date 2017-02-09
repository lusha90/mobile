package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_023 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        LogUtil.getLogger().info("1：null");
        selfSelectionAction.skipAdv();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
    @Test(description = "自选股编辑页面删除所有最新浏览")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：点击编辑按钮，进入编辑自选股页面");
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        LogUtil.getLogger().info("3：点击\"编辑最新浏览\"");
        selfSelectionAction.editLatestBrowseOnEditSelectionView();
        LogUtil.getLogger().info("4：打开自选页面显示最新浏览");
        selfSelectionAction.toggleShowLatestBrowse(true);
        LogUtil.getLogger().info("5：点击搜索图标");
        selfSelectionAction.enterIntoSearchStockViewOnEditSelfSelectionView();
        LogUtil.getLogger().info("6：股票代码输入框输入555");
        selfSelectionAction.typeTextOnSearchStockView("555");
        LogUtil.getLogger().info("7：点击\"神州信息\"，进行自选股浏览");
        selfSelectionAction.enterIntoStockDetailViewOnSearchStockView("神州信息");
        selfSelectionAction.checkPoint.checkTextNotExist("搜股票");
        LogUtil.getLogger().info("8：返回到自选股编辑页面");
        selfSelectionAction.back();
        selfSelectionAction.checkPoint.checkTextExist("神州信息");
        LogUtil.getLogger().info("9：点击删除所有最新浏览");
        selfSelectionAction.deleteSelfSelectionOrStockOnEditSelectionView();
        selfSelectionAction.checkPoint.checkTextNotExist("神州信息");
        LogUtil.getLogger().info("10：返回到自选股页面");
        selfSelectionAction.back();
        selfSelectionAction.checkPoint.checkTextNotExist("神州信息");
    }
    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
}
