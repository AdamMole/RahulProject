package pl.iTechArt.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementWait {
    public WebDriver driver;
    public WebDriverWait webDriverWait;// = new WebDriverWait(driver, Duration.ofSeconds(10));

    public ElementWait(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForElementToApear(By element) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitForElementToDissapear(WebElement element) {
        webDriverWait.until(ExpectedConditions.invisibilityOf(element));
    }

}
