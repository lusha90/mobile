package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_036 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        selfSelectionAction = new SelfSelectionAction("master");
        LogUtil.getLogger().info("1：删除所有自选股");
        selfSelectionAction.skipAdv();
    }

    @Test(description = "编辑自选页面切换编辑自选股和编辑最新浏览删除自选股和股票")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：点击编辑按钮，进入编辑自选股页面");
        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
        LogUtil.getLogger().info("3：点击回收图标");
        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
        LogUtil.getLogger().info("4：点击\"编辑最新浏览\"");
        selfSelectionAction.editLatestBrowseOnEditSelectionView();
        LogUtil.getLogger().info("5：点击回收图标");
        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
        LogUtil.getLogger().info("6：点击\"编辑自选股\"");
        selfSelectionAction.editSelfSelectionOnEditSelectionView();
        LogUtil.getLogger().info("7：点击回收图标");
        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.back();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }
}
