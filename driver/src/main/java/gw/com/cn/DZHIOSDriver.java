package gw.com.cn;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

/**
 * Created by lusha on 2016/11/28.
 */
public class DZHIOSDriver extends IOSDriver {
    /**
     * @param executor     is an instance of {@link HttpCommandExecutor}
     *                     or class that extends it. Default commands or another vendor-specific
     *                     commands may be specified there.
     * @param capabilities take a look
     *                     at {@link Capabilities}
     */
    public DZHIOSDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    /**
     * @param remoteAddress       is the address
     *                            of remotely/locally started Appium server
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHIOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    /**
     * @param remoteAddress       is the address
     *                            of remotely/locally started Appium server
     * @param httpClientFactory   take a look
     *                            at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
    }

    /**
     * @param service             take a look
     *                            at {@link AppiumDriverLocalService}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHIOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
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
    public DZHIOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, desiredCapabilities);
    }

    /**
     * @param builder             take a look
     *                            at {@link AppiumServiceBuilder}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHIOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
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
    public DZHIOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, desiredCapabilities);
    }

    /**
     * @param httpClientFactory   take a look
     *                            at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHIOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, desiredCapabilities);
    }

    /**
     * @param desiredCapabilities take a look
     *                            at {@link Capabilities}
     */
    public DZHIOSDriver(Capabilities desiredCapabilities) {
        super(desiredCapabilities);
    }
}
