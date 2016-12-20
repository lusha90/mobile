package gw.com.cn.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.net.URL;

/**
 * Created by lusha on 2016/11/29.
 */

public class LogUtil {

    public static Logger getLogger(){
        DzhLogUtil dzhLogUtil = new DzhLogUtil();
        return dzhLogUtil.getLogger(true);
    }

}

class DzhLogUtil {

    private Logger logger;

    private String logConfigPath;

    public String getLogConfigPath() {
        return logConfigPath;
    }

    public void setLogConfigPath(String logConfigPath) {
        this.logConfigPath = logConfigPath;
    }

    public Logger getLogger(boolean isFromCP){
        if(logger == null){
            if (isFromCP){
                initLoggerFromClassPath(this.getClass().getResource("/conf/dzhLog4j.properties"));
            }else{
                initLogger(logConfigPath);
            }
        }
        return logger;
    }

    public void initLoggerFromClassPath(URL configURL){
        PropertyConfigurator.configure(configURL);
        logger = Logger.getRootLogger();
    }

    public void initLogger(String logConfigPath){
        File currentPath = new File(".");
        //PropertyConfigurator.configure(currentPath.getAbsolutePath() + "\\src\\main\\resources\\conf\\dzhLog4j.properties");
        PropertyConfigurator.configure(currentPath.getAbsolutePath() + logConfigPath);
        logger = Logger.getRootLogger();
    }

}
