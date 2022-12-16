package pl.iTechArt.helpers;

import com.github.dockerjava.api.model.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
    private static final Dimension DEFAULT_SCREEN_RESOLUTION = new Dimension(1366, 768);

    public WebDriverFactory() {
    }

    public WebDriver createDriver(Config config) {
        WebDriverManager webDriverManager = WebDriverManager.getInstance(config.getBrowser().getDriverType());
        if (config.isRemote()) {
            webDriverManager.remoteAddress(RemoteDriverCapabilities.REMOTE_URL);
        }

        WebDriver driver = webDriverManager.capabilities(config.getCapabilities()).create();
        driver.manage().window().setSize(DEFAULT_SCREEN_RESOLUTION);
        driver.manage().window().maximize();
        return driver;
    }
}
