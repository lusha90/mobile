package gw.com.cn.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

/**
 * Created by lusha on 2016/11/29.
 */
public class LogUtil {

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    static{
        File currentPath = new File("");
        PropertyConfigurator.configure(currentPath.getAbsolutePath() + "\\src\\main\\resources\\conf\\dzhLog4j.properties");
        logger = Logger.getRootLogger();
    }

}
