package gw.com.cn;

import gw.com.cn.util.ImageUtil;
import io.appium.java_client.TouchShortcuts;
import io.appium.java_client.android.*;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by lusha on 2016/11/28.
 */
public class DZHAndroidDriver extends AndroidDriver {
    /**
     * @param executor     is an instance of {@link HttpCommandExecutor}
     *                     or class that extends it. Default commands or another vendor-specific
     *                     commands may be specified there.
     * @param capabilities take a look
     *                     at {@link Capabilities}
     */
    public DZHAndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    /**
     * @param remoteAddress       is the address of remotely/locally
     *                            started Appium server
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
        File file = new File(".");
    }

    /**
     * @param remoteAddress       is the address of remotely/locally
     *                            started Appium server
     * @param httpClientFactory   take a look
     *                            at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
    }

    /**
     * @param service             take a look
     *                            at {@link AppiumDriverLocalService}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, desiredCapabilities);
    }

    /**
     * @param service             take a look
     *                            at {@link AppiumDriverLocalService}
     * @param httpClientFactory   take a look
     *                            at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, desiredCapabilities);
    }

    /**
     * @param builder             take a look
     *                            at {@link AppiumServiceBuilder}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, desiredCapabilities);
    }

    /**
     * @param builder             take a look
     *                            at {@link AppiumServiceBuilder}
     * @param httpClientFactory   take a look
     *                            at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, desiredCapabilities);
    }

    /**
     * @param httpClientFactory   take a look
     *                            at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, desiredCapabilities);
    }

    /**
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHAndroidDriver(Capabilities desiredCapabilities) {
        super(desiredCapabilities);
    }

    /**
     * @param startx
     * @param starty
     * @param endx
     * @param endy
     * @param duration
     * @see TouchShortcuts#swipe(int, int, int, int, int)
     */
    @Override
    public void swipe(int startx, int starty, int endx, int endy, int duration) {
        super.swipe(startx, starty, endx, endy, duration);
    }

    /**
     * Send a key event to the device.
     *
     * @param key code for the key pressed on the device.
     */
    @Override
    public void pressKeyCode(int key) {
        super.pressKeyCode(key);
    }

    /**
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer).
     */
    @Override
    public void pressKeyCode(int key, Integer metastate) {
        super.pressKeyCode(key, metastate);
    }

    /**
     * Send a long key event to the device.
     *
     * @param key code for the long key pressed on the device.
     */
    @Override
    public void longPressKeyCode(int key) {
        super.longPressKeyCode(key);
    }

    /**
     * @param key       code for the long key pressed on the Android device.
     * @param metastate metastate for the long key press.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer)
     */
    @Override
    public void longPressKeyCode(int key, Integer metastate) {
        super.longPressKeyCode(key, metastate);
    }

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public Connection getConnection() {
        return super.getConnection();
    }

    @Override
    public void pushFile(String remotePath, byte[] base64Data) {
        super.pushFile(remotePath, base64Data);
    }

    @Override
    public void pushFile(String remotePath, File file) throws IOException {
        super.pushFile(remotePath, file);
    }

    @Override
    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity, String intentAction, String intentCategory, String intentFlags, String optionalIntentArguments, boolean stopApp) throws IllegalArgumentException {
        super.startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity, intentAction, intentCategory, intentFlags, optionalIntentArguments, stopApp);
    }

    @Override
    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity, boolean stopApp) throws IllegalArgumentException {
        super.startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity, stopApp);
    }

    @Override
    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity) throws IllegalArgumentException {
        super.startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity);
    }

    @Override
    public void startActivity(String appPackage, String appActivity) throws IllegalArgumentException {
        super.startActivity(appPackage, appActivity);
    }

    @Override
    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity, String intentAction, String intentCategory, String intentFlags, String intentOptionalArgs) throws IllegalArgumentException {
        super.startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity, intentAction, intentCategory, intentFlags, intentOptionalArgs);
    }

    /**
     * Get test-coverage data.
     *
     * @param intent intent to broadcast.
     * @param path   path to .ec file.
     */
    @Override
    public void endTestCoverage(String intent, String path) {
        super.endTestCoverage(intent, path);
    }

    /**
     * Get the current activity being run on the mobile device.
     *
     * @return a current activity being run on the mobile device.
     */
    @Override
    public String currentActivity() {
        return super.currentActivity();
    }

    /**
     * Open the notification shade, on Android devices.
     */
    @Override
    public void openNotifications() {
        super.openNotifications();
    }

    /**
     * Check if the device is locked.
     *
     * @return true if device is locked. False otherwise
     */
    @Override
    public boolean isLocked() {
        return super.isLocked();
    }

    @Override
    public void toggleLocationServices() {
        super.toggleLocationServices();
    }

    /**
     * Set the `ignoreUnimportantViews` setting. *Android-only method*.
     * Sets whether Android devices should use `setCompressedLayoutHeirarchy()`
     * which ignores all views which are marked IMPORTANT_FOR_ACCESSIBILITY_NO
     * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
     * by the system), in an attempt to make things less confusing or faster.
     *
     * @param compress ignores unimportant views if true, doesn't ignore otherwise.
     */
    @Override
    public void ignoreUnimportantViews(Boolean compress) {
        super.ignoreUnimportantViews(compress);
    }

    /**
     * @param using
     * @throws WebDriverException This method is not
     *                            applicable with browser/webview UI.
     */
    @Override
    public WebElement findElementByAndroidUIAutomator(String using) {
        return super.findElementByAndroidUIAutomator(using);
    }

    /**
     * @param using
     * @throws WebDriverException This method is not
     *                            applicable with browser/webview UI.
     */
    @Override
    public List findElementsByAndroidUIAutomator(String using) {
        return super.findElementsByAndroidUIAutomator(using);
    }

    /**
     * This method locks a device.
     */
    @Override
    public void lockDevice() {
        super.lockDevice();
    }

    /**
     * This method unlocks a device.
     */
    @Override
    public void unlockDevice() {
        super.unlockDevice();
    }

    // sourceImagePath /picture/settings/test.png
    /**
     * This method  compare two images
     */
    public boolean pictureCompare(String sourceImagePath, int x, int y, int w, int h, double percent, boolean isSaveSubImage) {
        boolean result = false;
        try {
            File snapshotFile = this.getScreenshotAs(OutputType.FILE);
            BufferedImage sourceImage = ImageIO.read(this.getClass().getResource(sourceImagePath));
            BufferedImage targetImage = ImageIO.read(snapshotFile);
            BufferedImage subSourceImage = ImageUtil.getSubImage(sourceImage, x, y, w, h);
            BufferedImage subTargetImage = ImageUtil.getSubImage(targetImage, x, y, w, h);
            if (isSaveSubImage) {
                ImageUtil.saveImageToFile(subSourceImage, "png", System.getProperty("java.io.tmpdir") + "source.png");
                ImageUtil.saveImageToFile(subTargetImage, "png", System.getProperty("java.io.tmpdir") + "target.png");
            }
            result = ImageUtil.isSame(subSourceImage, subTargetImage, percent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    public void loadPicture(){
        try {
            Image image = ImageIO.read(this.getClass().getResource("/picture/settings/test.png"));
            int width = image.getWidth(null);
            System.out.println("***************width: " + width);
            int height = image.getHeight(null);
            System.out.println("***************height: " + height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
