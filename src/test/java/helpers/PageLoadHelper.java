package helpers;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PageLoadHelper {
    public static void waitForPageToLoad() {
        waitForPageToLoad(Timeouts.TIMEOUT_10_SECONDS);
    }

    public static void waitForPageToLoad(Duration timeout) {
        ExpectedCondition<Boolean> expectedCondition = (webDriver) -> {
            return ((JavascriptExecutor)Objects.requireNonNull(webDriver)).executeScript("return document.readyState", new Object[0]).toString().equals("complete");
        };
        ElementWaitHelper.condition(expectedCondition, timeout, "Wait for page to load...");
    }

    public static void waitForPageToBeReady(WebElement element) {
        waitForPageToBeReady(WebElementHelper.getBy(element), Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForPageToBeReady(WebElement element, Duration timeout) {
        waitForPageToBeReady(WebElementHelper.getBy(element), timeout);
    }

    public static void waitForPageToBeReady(By locatedBy, Duration timeout) {
        waitForPageToLoad(timeout);
        ElementWaitHelper.waitForElementToBePresent(locatedBy, timeout);
        ElementWaitHelper.waitForElementToBeVisible(locatedBy, timeout);
    }

    public static void waitForPageToBeReady(List<WebElement> elementList) {
        waitForPageToBeReady(elementList, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForPageToBeReady(List<WebElement> elementList, Duration timeout) {
        waitForPageToLoad(timeout);
        ElementWaitHelper.waitForAtLeastOneElementLocatedByToBePresent(WebElementHelper.getBy(elementList), timeout);
        ElementWaitHelper.waitForAllElementsToBeVisible(elementList);
    }

    public static void waitForPageToBeReady(By locatedBy) {
        waitForPageToBeReady(locatedBy, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForUrlChange(String oldUrl) {
        waitForUrlChange(oldUrl, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForUrlChange(String oldUrl, Duration timeout) {
        ElementWaitHelper.condition(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)), timeout, String.format("Waiting for URL to change from: '%s'", oldUrl));
    }

    private PageLoadHelper() {
    }
}

