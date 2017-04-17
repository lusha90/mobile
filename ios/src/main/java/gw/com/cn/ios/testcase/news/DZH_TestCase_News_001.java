package gw.com.cn.ios.testcase.news;

import gw.com.cn.ios.NewsAction;
import gw.com.cn.ios.testcase.DZHBaseTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by lusha on 2016/11/28.
 */
public class DZH_TestCase_News_001 extends DZHBaseTestCase {

    private NewsAction newsAction;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        newsAction = new NewsAction("master");
    }

    @Test(description = "浏览大数量的新闻")
    public void testStep() {
        super.testStep();
        newsAction.enterIntoNewsView();
        newsAction.browseFastNews(1000);
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}