package org.uncommons.reportng;

import org.testng.ITestResult;

/**
 * Created by lusha on 2016/12/15.
 */
public class DzhTestResultComparator extends  TestResultComparator{

    public int compare(ITestResult result1, ITestResult result2)
    {
        int result2Time = 0;
        if(result1.getStartMillis() < result2.getStartMillis()){
            result2Time = -1;
        }else{
            result2Time = 1;
        }
        return result2Time;
    }

}
