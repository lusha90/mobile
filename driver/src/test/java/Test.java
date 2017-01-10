import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lusha on 2016/11/29.
 */
public class Test {

    public static void main(String[] args) throws MalformedURLException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("deviceName", "71MBBLE23Z3R");
//        capabilities.setCapability("platformVersion", "23");
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("appPackage", "com.android.dazhihui");
//        capabilities.setCapability("appActivity", ".Settings");
//
//        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
//                capabilities);
//        Properties pro = System.getProperties();
//        Map<String,String> map = System.getenv();
        List<Double> unSort = new ArrayList<Double>();
        unSort.add(0.00);
        unSort.add(14.20);
        unSort.add(2.56);
        unSort.add(18.98);
        unSort.add(-3.33);
        System.out.println(unSort);
        Collections.reverse(unSort);
        System.out.println(unSort);
        Collections.sort(unSort);
        System.out.println(unSort);
        Collections.reverse(unSort);
        System.out.println(unSort);
    }
}
