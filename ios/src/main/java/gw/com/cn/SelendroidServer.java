package gw.com.cn;

import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

/**
 * Created by lusha on 2016/12/28.
 */
public class SelendroidServer {

    public static void main(String[] args) {
        SelendroidConfiguration config = new SelendroidConfiguration();
        config.addSupportedApp("D:\\dzh.apk");
        SelendroidLauncher selendroidServer = new SelendroidLauncher(config);
        selendroidServer.launchSelendroid();
    }
}
