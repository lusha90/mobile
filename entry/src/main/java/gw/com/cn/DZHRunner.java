package gw.com.cn;

import gw.com.cn.util.FtpUtil;
import gw.com.cn.util.JsonUtil;
import gw.com.cn.util.LogUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.TestNG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class DZHRunner {

    private static DZHInfo  fillDZHInfo(File jsonFile){
        JSONObject jSONObject = JsonUtil.readJson(jsonFile);
        DZHInfo dzhInfo = new DZHInfo();
        dzhInfo.setAddress(jSONObject.has("address") ? (String) jSONObject.get("address") : dzhInfo.getAddress());
        dzhInfo.setFtpPath(jSONObject.has("ftpPath") ? (String) jSONObject.get("ftpPath") : dzhInfo.getFtpPath());
        dzhInfo.setPort(jSONObject.has("port") ? (String) jSONObject.get("port") : dzhInfo.getPort());
        dzhInfo.setSdkPath(jSONObject.has("sdkPath") ? (String) jSONObject.get("sdkPath") : dzhInfo.getSdkPath());
        dzhInfo.setDownloadAppFromServer(jSONObject.has("downloadAppFromServer") ? (Boolean) jSONObject.get("downloadAppFromServer") : dzhInfo.isDownloadAppFromServer());
        dzhInfo.setReplaceExistingApp(jSONObject.has("replaceExistingApp") ? (Boolean) jSONObject.get("replaceExistingApp") : dzhInfo.isReplaceExistingApp());
        if(dzhInfo.isReplaceExistingApp() && !dzhInfo.isDownloadAppFromServer()){
            dzhInfo.setAppPath(jSONObject.has("appPath") ? (String) jSONObject.get("appPath") : dzhInfo.getAppPath());
        }
        JSONArray jSONArray = jSONObject.getJSONArray("devices");
        List<DeviceInfo> devicesList = new ArrayList<DeviceInfo>();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject deviceJSONObject = jSONArray.getJSONObject(i);
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceType(jSONObject.has("deviceType") ? (String) deviceJSONObject.get("deviceType") : deviceInfo.getDeviceType());
            deviceInfo.setPlatformName(jSONObject.has("platformName") ? (String) deviceJSONObject.get("platformName") : deviceInfo.getPlatformName());
            deviceInfo.setPlatformVersion(jSONObject.has("platformVersion") ? (String) deviceJSONObject.get("platformVersion") : deviceInfo.getPlatformVersion());
            deviceInfo.setAutomationName(jSONObject.has("automationName") ? (String) deviceJSONObject.get("automationName") : deviceInfo.getAutomationName());
            deviceInfo.setDeviceName(jSONObject.has("deviceName") ? (String) deviceJSONObject.get("deviceName") : deviceInfo.getDeviceName());
            deviceInfo.setLanguage(jSONObject.has("language") ? (String) deviceJSONObject.get("language") : deviceInfo.getLanguage());
            deviceInfo.setLocale(jSONObject.has("locale") ? (String) deviceJSONObject.get("locale") : deviceInfo.getLocale());
            devicesList.add(deviceInfo);
        }
        dzhInfo.setDevicesInfo(devicesList);
        return dzhInfo;
    }

    private static DZHInfo fillDZHInfo(String jsonFilePath){
        File file = new File(jsonFilePath);
        return fillDZHInfo(file);
    }

    public static void runDZHCases(String jsonFilePath, String testngXmlPath){
        File currentPath = new File("");
        jsonFilePath = currentPath.getAbsolutePath() + jsonFilePath;
        testngXmlPath = currentPath.getAbsolutePath() + testngXmlPath;
        LogUtil.getLogger().info("loading json : " + jsonFilePath);
        LogUtil.getLogger().info("loading cases : " + testngXmlPath);
        DZHInfo dzhInfo = fillDZHInfo(jsonFilePath);
        if(dzhInfo.isDownloadAppFromServer()){
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
        BaseAction.dzhInfo = dzhInfo;
        LogUtil.getLogger().info("################################# start of the test #################################");
        TestNG testng = TestNG.privateMain(new String[]{testngXmlPath}, null);
        LogUtil.getLogger().info("#################################  end of the test  #################################\n");
        System.exit(testng.getStatus());
    }

    public static void runDZHCases(File jsonFile, File testngXmlFile){
        DZHInfo dzhInfo = fillDZHInfo(jsonFile.getAbsolutePath());
        BaseAction.dzhInfo = dzhInfo;
        TestNG.main(new String[]{testngXmlFile.getAbsolutePath()});
    }

    public static void main(String[] args) {
        if(args.length < 2){
            throw new RuntimeException("args error");
        }
        String jsonFilePath = args[0];
        String testngXmlPath = args[1];
        runDZHCases(jsonFilePath, testngXmlPath);
    }
}
