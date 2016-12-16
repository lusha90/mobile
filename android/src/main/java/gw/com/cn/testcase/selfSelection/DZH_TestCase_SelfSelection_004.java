package gw.com.cn.testcase.selfSelection;

import gw.com.cn.testcase.DZHBaseTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by lusha on 2016/12/13.
 */
public class DZH_TestCase_SelfSelection_004 extends DZHBaseTestCase {

    @BeforeMethod
    public void setUp() {
        super.setUp();
    }

    @Test(description = "设置界面打开同步自选股")
    public void testStep() {
        super.testStep();
        Assert.assertEquals(1,2);
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}
