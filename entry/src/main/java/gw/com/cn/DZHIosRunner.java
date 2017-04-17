package gw.com.cn;

import gw.com.cn.util.FtpUtil;
import gw.com.cn.util.JsonUtil;
import gw.com.cn.util.LogUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.TestNG;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class DZHIosRunner {

    public static final String DZH_LOG = "logs";

    public static final String DZH_REPORT = "dzhReport";

    public static final String DZH_LOGCAT = "logcat";

    public static final String DZH_SCREENSHOT = "screenshot";


    private static DZHIosInfo fillDZHInfo(File jsonFile) {
        JSONObject jSONObject = JsonUtil.readJson(jsonFile);
        DZHIosInfo dzhInfo = new DZHIosInfo();
        dzhInfo.setAddress(jSONObject.has("address") ? (String) jSONObject.get("address") : dzhInfo.getAddress());
        dzhInfo.setFtpPath(jSONObject.has("ftpPath") ? (String) jSONObject.get("ftpPath") : dzhInfo.getFtpPath());
        dzhInfo.setPort(jSONObject.has("port") ? (String) jSONObject.get("port") : dzhInfo.getPort());
        dzhInfo.setSdkPath(jSONObject.has("sdkPath") ? (String) jSONObject.get("sdkPath") : dzhInfo.getSdkPath());
        dzhInfo.setMenuConf(jSONObject.has("menuConf") ? (String) jSONObject.get("menuConf") : dzhInfo.getMenuConf());
        dzhInfo.setTestdataPath(jSONObject.has("testdataPath") ? (String) jSONObject.get("testdataPath") : dzhInfo.getTestdataPath());
        dzhInfo.setDownloadAppFromServer(jSONObject.has("downloadAppFromServer") ? (Boolean) jSONObject.get("downloadAppFromServer") : dzhInfo.isDownloadAppFromServer());
        dzhInfo.setReplaceExistingApp(jSONObject.has("replaceExistingApp") ? (Boolean) jSONObject.get("replaceExistingApp") : dzhInfo.isReplaceExistingApp());
        if (dzhInfo.isReplaceExistingApp() && !dzhInfo.isDownloadAppFromServer()) {
            dzhInfo.setAppPath(jSONObject.has("appPath") ? (String) jSONObject.get("appPath") : dzhInfo.getAppPath());
        }
        JSONArray jSONArray = jSONObject.getJSONArray("devices");
        List<IosDeviceInfo> devicesList = new ArrayList<IosDeviceInfo>();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject deviceJSONObject = jSONArray.getJSONObject(i);
            IosDeviceInfo deviceInfo = new IosDeviceInfo();
            deviceInfo.setDeviceBrand(deviceJSONObject.has("deviceBrand") ? (String) deviceJSONObject.get("deviceBrand") : deviceInfo.getDeviceBrand());
            deviceInfo.setBrandSeries(deviceJSONObject.has("brandSeries") ? (String) deviceJSONObject.get("brandSeries") : deviceInfo.getBrandSeries());
            deviceInfo.setDeviceType(deviceJSONObject.has("deviceType") ? (String) deviceJSONObject.get("deviceType") : deviceInfo.getDeviceType());
            deviceInfo.setPlatformName(deviceJSONObject.has("platformName") ? (String) deviceJSONObject.get("platformName") : deviceInfo.getPlatformName());
            deviceInfo.setPlatformVersion(deviceJSONObject.has("platformVersion") ? (String) deviceJSONObject.get("platformVersion") : deviceInfo.getPlatformVersion());
            deviceInfo.setAutomationName(deviceJSONObject.has("automationName") ? (String) deviceJSONObject.get("automationName") : deviceInfo.getAutomationName());
            deviceInfo.setDeviceName(deviceJSONObject.has("deviceName") ? (String) deviceJSONObject.get("deviceName") : deviceInfo.getDeviceName());
            deviceInfo.setBundleId(deviceJSONObject.has("bundleId") ? (String) deviceJSONObject.get("bundleId") : deviceInfo.getBundleId());
            deviceInfo.setUdid(deviceJSONObject.has("udid") ? (String) deviceJSONObject.get("udid") : deviceInfo.getUdid());
            deviceInfo.setLanguage(deviceJSONObject.has("language") ? (String) deviceJSONObject.get("language") : deviceInfo.getLanguage());
            deviceInfo.setLocale(deviceJSONObject.has("locale") ? (String) deviceJSONObject.get("locale") : deviceInfo.getLocale());
            deviceInfo.setUpdateTip(deviceJSONObject.has("updateTip") ? (Boolean) deviceJSONObject.get("updateTip") : deviceInfo.isUpdateTip());
            deviceInfo.setKeyboard(deviceJSONObject.has("keyboard") ? (String) deviceJSONObject.get("keyboard") : deviceInfo.getKeyboard());
            deviceInfo.setSessionTimeout(deviceJSONObject.has("sessionTimeout") ? (String) deviceJSONObject.get("sessionTimeout") : deviceInfo.getSessionTimeout());
            devicesList.add(deviceInfo);
        }
        dzhInfo.setDevicesInfo(devicesList);

        List<Account> accounts = new ArrayList<Account>();
        JSONArray jSONArrayAccount = jSONObject.getJSONArray("account");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject accountJSONObject = jSONArrayAccount.getJSONObject(i);
            Account account = new Account();
            account.setAccountID(accountJSONObject.has("accountID") ? (String) accountJSONObject.get("accountID") : account.getAccountID());
            account.setAccountPwd(accountJSONObject.has("accountPwd") ? (String) accountJSONObject.get("accountPwd") : account.getAccountPwd());
            accounts.add(account);
        }
        dzhInfo.setAccounts(accounts);
        return dzhInfo;
    }

    private static DZHIosInfo fillDZHInfo(String jsonFilePath) {
        File file = new File(jsonFilePath);
        return fillDZHInfo(file);
    }

    private void loadConfFromClassPath() {
        InputStream inputStream = this.getClass().getResourceAsStream("/caseConf/allCases.xml");
        InputStream inputStreamJson = this.getClass().getResourceAsStream("/conf/master.json");
        String jsonStr = null;
        try {
            jsonStr = IOUtils.toString(inputStreamJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DZHIosInfo loadConf(String jsonFilePath) {
        File currentPath = new File("");
        jsonFilePath = currentPath.getAbsolutePath() + jsonFilePath;
        LogUtil.getLogger().info("loading json : " + jsonFilePath);
        DZHIosInfo dzhInfo = fillDZHInfo(jsonFilePath);
        if (dzhInfo.isDownloadAppFromServer()) {
            LogUtil.getLogger().info("download newest app from ftp server : " + dzhInfo.getFtpPath());
            FtpUtil ftpUtil = new FtpUtil(dzhInfo.getFtpPath());
            try {
                File appFile = ftpUtil.downloadNewestAndroidApp(System.getProperty("java.io.tmpdir"));
                dzhInfo.setAppPath(appFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ftpUtil.closeConnect();
        }
        return dzhInfo;
    }

    public static void runDZHCases(String jsonFilePath, String testngXmlPath) {
        IosBaseAction.dzhInfo = loadConf(jsonFilePath);
        File currentPath = new File("");
        testngXmlPath = currentPath.getAbsolutePath() + testngXmlPath;
        LogUtil.getLogger().info("################################# start of the test #################################");
        TestNG testng = new TestNG();
        testng.setUseDefaultListeners(false);
        List<String> suites = new ArrayList<String>();
        suites.add(testngXmlPath);
        testng.setTestSuites(suites);
        testng.setOutputDirectory(new File(".").getAbsolutePath() + File.separator + DZH_REPORT);
        testng.run();
        LogUtil.getLogger().info("#################################  end of the test  #################################\n");
        System.exit(testng.getStatus());
    }

    public static void runDZHCases(File jsonFile, File testngXmlFile) {
        DZHIosInfo dzhInfo = fillDZHInfo(jsonFile.getAbsolutePath());
        IosBaseAction.dzhInfo = dzhInfo;
        TestNG.main(new String[]{testngXmlFile.getAbsolutePath()});
    }

    private static void cleanLogAndReport() {
        String reportPath = new File(".").getAbsolutePath() + File.separator + DZH_REPORT;
        String logPath = new File(".").getAbsolutePath() + File.separator + DZH_LOG;
        FileUtils.deleteQuietly(new File(reportPath));
        FileUtils.deleteQuietly(new File(logPath));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("args error");
        }
        cleanLogAndReport();
        String jsonFilePath = args[0];
        String testngXmlPath = args[1];
        runDZHCases(jsonFilePath, testngXmlPath);
    }
}
