package gw.com.cn;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;

/**
 * Created by lusha on 2016/12/28.
 */
public class SelendroidClient {

    public static void main(String[] args) throws Exception {
        SelendroidCapabilities capa = new SelendroidCapabilities("com.android.dazhihui:8.52");
        SelendroidDriver driver = new SelendroidDriver(capa);
        driver.findElementByName("test");
    }

}
