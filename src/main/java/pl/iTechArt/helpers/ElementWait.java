package pl.iTechArt.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementWait {
    WebDriver driver;
    WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public void waitForElementToApear(By element) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitForElementToDissapear(WebElement element) {
        webDriverWait.until(ExpectedConditions.invisibilityOf(element));
    }

}
