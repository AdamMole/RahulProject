package helpers;

import com.github.dockerjava.api.model.Config;
import org.openqa.selenium.WebDriver;

public class WebDriverProvider {
    private static final InheritableThreadLocal<WebDriver> WEB_DRIVER_THREAD_HOLDER = new InheritableThreadLocal();

    public WebDriverProvider() {
    }

    public static void tearDown() {
        getDriver().quit();
        WEB_DRIVER_THREAD_HOLDER.remove();
    }

    public static WebDriver getDriver() {
        return (WebDriver)WEB_DRIVER_THREAD_HOLDER.get();
    }

    private static void setWebDriver(WebDriver driver) {
        WEB_DRIVER_THREAD_HOLDER.set(driver);
    }

//    public static void setWebDriver(Config config) {
//        WebDriver driver = (new WebDriverFactory()).createDriver(config);
//        setWebDriver(driver);
//    }
}
