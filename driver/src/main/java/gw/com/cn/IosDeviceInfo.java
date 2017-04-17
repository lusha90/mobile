package gw.com.cn;

/**
 * Created by lusha on 2016/11/28.
 */
public class IosDeviceInfo extends DZHInfo{

    private String deviceBrand = "apple";

    private String brandSeries = "iphone5se";

    private String deviceType = "master";

    private String platformName = "ios";

    private String platformVersion = "10.1";

    private String automationName = "XCUITest";

    private String deviceName = "彭";

    private String bundleId = "com.gw.dzhiphone622.33";

    private String udid = "5b94d28d6a0d37d89e788dae6b68c1a54966b428";

    private String language = "zh_CN";

    private String locale = "CN";

    private String sessionTimeout = "60";

    private String keyboard = "origin";

    private boolean updateTip = false;

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public boolean isUpdateTip() {
        return updateTip;
    }

    public void setUpdateTip(boolean updateTip) {
        this.updateTip = updateTip;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getBrandSeries() {
        return brandSeries;
    }

    public void setBrandSeries(String brandSeries) {
        this.brandSeries = brandSeries;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getAutomationName() {
        return automationName;
    }

    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
