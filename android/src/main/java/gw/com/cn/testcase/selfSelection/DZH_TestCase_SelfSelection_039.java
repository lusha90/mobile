package gw.com.cn.testcase.selfSelection;

import gw.com.cn.PersonalCenterAction;
import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import gw.com.cn.util.LogUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_039 extends DZHBaseTestCase {

    private PersonalCenterAction personalCenterAction;
    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        personalCenterAction = new PersonalCenterAction("master");
        selfSelectionAction = new SelfSelectionAction("master");
        LogUtil.getLogger().info("1：登录账户");
        selfSelectionAction.skipAdv();
        personalCenterAction.login();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
    }

    @Test(description = "自选股同步（登录与退出）")
    public void testStep() {
        super.testStep();
        LogUtil.getLogger().info("1：进入自选股页面");
        LogUtil.getLogger().info("2：点击搜索图标");
        selfSelectionAction.enterIntoSearchStockViewOnSelfSelectionView();
        LogUtil.getLogger().info("3：股票代码输入框输入600555");
        selfSelectionAction.typeTextOnSearchStockView("600555");
        String stockName = selfSelectionAction.addSelfStockOnOnStockDetailView();
        selfSelectionAction.back();
        LogUtil.getLogger().info("4：股票代码输入框输入000555");
        selfSelectionAction.enterIntoSearchStockViewOnSelfSelectionView();
        selfSelectionAction.typeTextOnSearchStockView("000555");
        String secondStockName = selfSelectionAction.addSelfStockOnOnStockDetailView();
        LogUtil.getLogger().info("5：返回到自选股页面");
        selfSelectionAction.back();
        LogUtil.getLogger().info("6：退出登录");
        personalCenterAction.logout();
        LogUtil.getLogger().info("7：删除海航创新和神州信息自选股");
        selfSelectionAction.selfStockOperatorOnSelectionView(stockName, SelfSelectionAction.StockOperator.DEL);
        selfSelectionAction.selfStockOperatorOnSelectionView(secondStockName, SelfSelectionAction.StockOperator.DEL);
        LogUtil.getLogger().info("8：登录账户，返回到自选股页面");
        personalCenterAction.login();
        selfSelectionAction.checkPoint.checkTextExist("海航创新");
        selfSelectionAction.checkPoint.checkTextExist("神州信息");
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
        selfSelectionAction.deleteAllSelfStockAndLatestBrowse();
        personalCenterAction.logout();
    }
}
