package gw.com.cn.testcase.news;

import gw.com.cn.testcase.DZHBaseTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Administrator on 2016/12/16.
 */
public class DZH_TestCase_News_005 extends DZHBaseTestCase {

    @BeforeMethod
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testStep() {
        super.testStep();
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
        Assert.assertEquals(1,2);
    }
}
