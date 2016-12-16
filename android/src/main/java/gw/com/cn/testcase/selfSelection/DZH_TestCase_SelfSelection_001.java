package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_001 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
//        selfSelectionAction = new SelfSelectionAction("master");
//        selfSelectionAction.sleep(6);
//        selfSelectionAction.skipAdv();
//        selfSelectionAction.enterIntoEditSelectionViewOnSelfSelectionView();
//        selfSelectionAction.deleteAllSelfSelectionOrLatestBrowseOnEditSelectionView(true);
    }

   @Test(description = "设置界面打开同步自选股")
    public void testStep() {
        super.testStep();
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}
