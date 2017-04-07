package gw.com.cn.testcase.selfSelection;

import gw.com.cn.SelfSelectionAction;
import gw.com.cn.testcase.DZHBaseTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DZH_TestCase_SelfSelection_040 extends DZHBaseTestCase {

    private SelfSelectionAction selfSelectionAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
    }

    @Test(description = "自选股同步(单账号2个设备登录添加自选股)")
    public void testStep() {
        super.testStep();
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}
