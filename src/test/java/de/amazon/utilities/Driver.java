package de.amazon.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Driver {
    private Driver() {
    }
    // InheritableThreadLocal  --> this is like a container, bag, pool.
    // in this pool we can have separate objects for each thread
    // for each thread, in InheritableThreadLocal we can have separate object for that thread
    // driver class will provide separate webdriver object per thread

    public static final String browserStackUserName = ConfigurationReader.get("browserStackUserName");
    public static final String browserStackAutomateKey = ConfigurationReader.get("browserStackAutomateKey");
    public static final String browserStackURL = "https://" + browserStackUserName + ":" + browserStackAutomateKey + "@hub-cloud.browserstack.com/wd/hub";
    public static final String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
    public static final String sauceUserName = ConfigurationReader.get("sauceUserName");
    public static final String sauceAccessKey = ConfigurationReader.get("sauceAccessKey");
    public static final String sauceURL = "https://" + sauceUserName + ":" + sauceAccessKey + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>(); //in order to run parallel test with singleton web driver

    public static WebDriver get() {

        if (driverPool.get() == null) {

            String browserParamFromEnv = System.getProperty("browser");
            String browser = browserParamFromEnv == null ? ConfigurationReader.get("browser") : browserParamFromEnv;
            DesiredCapabilities caps = new DesiredCapabilities();
            ChromeOptions chromeOptions = new ChromeOptions();

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    caps = new DesiredCapabilities();
                    /**
                     *We have disabled the cookies in below ChromeOptions
                     * You need to add this feature to your configuration.properties
                     * Add cookiesEnableDisable=2  (disable the cookies)
                     * Add cookiesEnableDisable =0  (enable the cookies)
                     */
                    chromeOptions = new ChromeOptions();
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    Map<String, Object> profile = new HashMap<String, Object>();
                    Map<String, Object> contentSettings = new HashMap<String, Object>();

                    contentSettings.put("cookies", ConfigurationReader.get("cookiesEnableDisable"));
                    profile.put("managed_default_content_settings", contentSettings);
                    prefs.put("profile", profile);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                    break;
                case "saucelabs_chrome":
                    caps = DesiredCapabilities.chrome();
                    caps.setCapability("platform", "Windows 10");
                    caps.setCapability("version", "latest");
                    caps.setCapability("extendedDebugging", "true");
                    caps.setCapability("capturePerformance", "true");

                    caps.setCapability("name", ConfigurationReader.get("tester")+", "+ConfigurationReader.get("testName"));

                    try {
                        driverPool.set(new RemoteWebDriver(new URL(sauceURL), caps));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;

                case "browserstack_chrome":
                    caps = new DesiredCapabilities();
                    caps.setCapability("os", "Windows");
                    caps.setCapability("os_version", "10");
                    caps.setCapability("browser", "Chrome");
                    caps.setCapability("browser_version", "latest");
                    caps.setCapability("browserstack.local", "false");
                    caps.setCapability("browserstack.networkLogs", "true");
                    caps.setCapability("browserstack.console", "disable");
                    caps.setCapability("browserstack.debug", "true");
                    caps.setCapability("browserstack.seleniumLogs", "true");
                    caps.setCapability("browserstack.sendKeys", "true");
                    caps.setCapability("project", "Amazon");
                    caps.setCapability("build", buildName);
                    caps.setCapability("browserstack.selenium_version", "3.14.0");

                    try {
                        driverPool.set(new RemoteWebDriver(new URL(browserStackURL),caps));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return driverPool.get();
    }
    public static void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }
}