package gw.com.cn;

import org.junit.Test;

/**
 * Created by lusha on 2016/11/30.
 */
public class DzhStart {

    @Test
    public void test(){
        DZHRunner.runDZHCases("\\conf\\master.json", "\\caseConf\\allCases.xml");
    }

    @Test
    public void testOne(){
        DZHRunner.runDZHCases("\\conf\\master.json", "\\caseConf\\allCases.xml");
    }

    @Test
    public void testTwo(){
        DZHRunner.runDZHCases("\\conf\\master.json", "\\caseConf\\allCases.xml");
    }

    @Test
    public void testThree(){
        DZHRunner.runDZHCases("\\conf\\master.json", "\\caseConf\\allCases.xml");
    }

}
