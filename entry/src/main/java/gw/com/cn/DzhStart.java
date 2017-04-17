package gw.com.cn;

import org.junit.Test;

/**
 * Created by lusha on 2016/11/30.
 */
public class DzhStart {

    @Test
    public void test(){
        DZHRunner.runDZHCases("/src/main/resources/conf/master.json", "/src/main/resources/caseConf/allCases.xml");
    }

    @Test
    public void iostest(){
        DZHIosRunner.runDZHCases("/src/main/resources/conf/ios.json", "/src/main/resources/caseConf/iosallCases.xml");
    }

    @Test
    public void testOne(){
        DZHRunner.runDZHCases("/src/main/resources/conf/oppo.json", "/src/main/resources/caseConf/allCases.xml");
    }

    @Test
    public void testTwo(){
        DZHRunner.runDZHCases("/src/main/resources/conf/master.json", "/src/main/resources/caseConf/allCases.xml");
    }

    @Test
    public void testThree(){
        DZHRunner.runDZHCases("/src/main/resources/conf/master.json", "/src/main/resources/caseConf/allCases.xml");
    }

}
