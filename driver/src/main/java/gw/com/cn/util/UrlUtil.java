package gw.com.cn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by lusha on 2017/2/6.
 */
public class UrlUtil {

    private static String getConfOfAppMenu(String urlAddress) {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String data;
            while ((data = br.readLine()) != null) {
                result.append(data);
            }
            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
