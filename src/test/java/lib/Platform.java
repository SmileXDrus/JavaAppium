package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            PLATFORM_MOBILE_WEB = "mobile_web",
            APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    //Singleton
    private static Platform instance;
    private Platform() {} //private constructor, cannot return new object
    public static Platform getInstance() {
        if(instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver() throws Exception
    {
        if (this.isAndroid()) {
            URL URL = new URL(APPIUM_URL);
            return new AndroidDriver<>(URL, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            URL URL = new URL(APPIUM_URL);
            return new IOSDriver<>(URL, this.getIOSDesiredCapabilities());
        } else if (this.isMW()) {
            return new ChromeDriver(this.getMWChromeOptions());
        }
        else {
            throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
        }
    }

    private boolean isPlatform(String my_platform)
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    public String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // для андройд любое, для ios соответствовало устройству
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("AutomationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app ", "/Users/user/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE (2nd generation)");
        capabilities.setCapability("AutomationName", "Appium");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("app ", "/Users/user/IdeaProjects/JavaAppiumAutomation/apks/Wikipedia.app");
        return capabilities;
    }

    private ChromeOptions getMWChromeOptions() {
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent",
                "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) " +
                        "AppleWebKit/535.19 (KHTML, like Gecko) " +
                        "Chrome/18.0.1025.166 " +
                        "Mobile Safari/535.19");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=360,640");
        return chromeOptions;
    }


    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMW()
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

}
