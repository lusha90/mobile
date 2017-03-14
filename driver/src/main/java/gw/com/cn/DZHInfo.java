package gw.com.cn;

import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class DZHInfo {

    private String address = "http://127.0.0.1";

    private String ftpPath = "ftp://10.15.54.198/";

    private String port = "4723";

    private String sdkPath = "D:\\Program\\sdk";

    private boolean downloadAppFromServer = false;

    private boolean replaceExistingApp = false;

    private String appPath;

    private String menuConf = "http://mnews.gw.com.cn/wap/data/mobileconfig/sc850.json";

    private String testdataPath = "D:\\Program Files\\Tesseract-OCR\\tessdata";

    private List<DeviceInfo> devicesInfo;

    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getTestdataPath() {
        return testdataPath;
    }

    public void setTestdataPath(String testdataPath) {
        this.testdataPath = testdataPath;
    }

    public String getMenuConf() {
        return menuConf;
    }

    public void setMenuConf(String menuConf) {
        this.menuConf = menuConf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSdkPath() {
        return sdkPath;
    }

    public void setSdkPath(String sdkPath) {
        this.sdkPath = sdkPath;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public List<DeviceInfo> getDevicesInfo() {
        return devicesInfo;
    }

    public void setDevicesInfo(List<DeviceInfo> devicesInfo) {
        this.devicesInfo = devicesInfo;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public boolean isDownloadAppFromServer() {
        return downloadAppFromServer;
    }

    public void setDownloadAppFromServer(boolean downloadAppFromServer) {
        this.downloadAppFromServer = downloadAppFromServer;
    }

    public boolean isReplaceExistingApp() {
        return replaceExistingApp;
    }

    public void setReplaceExistingApp(boolean replaceExistingApp) {
        this.replaceExistingApp = replaceExistingApp;
    }
}
