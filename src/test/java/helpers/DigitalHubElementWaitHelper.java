package helpers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static helpers.PageLoadHelper.waitForPageToBeReady;
import static helpers.WebElementHelper.*;


import java.time.Duration;

import static java.util.Arrays.stream;
import static org.openqa.selenium.By.*;

@Slf4j
public class DigitalHubElementWaitHelper extends ElementWaitHelper{
    private static final By SPINNER_IN_PROGRESS_LOCATOR = cssSelector(".spinner-in-progress");
    private static final By PROGRESS_OVERLAY_LOCATOR = className("progress-overlay");
    private static final By CIRCLE_SPINNER_LOCATOR = className("ge-spinner__render");
    private static final By SMALL_CIRCLE_SPINNER_LOCATOR = cssSelector(".far.fa-spinner");
    private static final By SMALL_NOTCH_CIRCLE_SPINNER_LOCATOR = className("fa-circle-notch");
    private static final By CUI_SPINNER_LOCATOR = xpath("//div[contains(@class, 'cui-spin')]");

    public static void waitForProgressBarControllerDisappear() {
        log.info("Waiting for progress bar with overlay to disappear...");
        waitForElementsToBeInvisible(getWebElements(PROGRESS_OVERLAY_LOCATOR));
    }

    public static void waitForProgressBarControllerDisappear(Duration timeoutDuration) {
        log.info("Waiting for progress bar with overlay to disappear...");
        waitForElementsToBeInvisible(getWebElements(PROGRESS_OVERLAY_LOCATOR), timeoutDuration);
    }

//    public static void waitForAngularToBeReady() {
//        getAngularDriverContext().waitForAngularRequestsToFinish();
//    }

    public static void waitForAllSpinnersToDisappear(Duration timeoutDuration) {
        log.info("Waiting for spinners-in-progress disappear...");
        waitForElementsToBeInvisible(SPINNER_IN_PROGRESS_LOCATOR, timeoutDuration);
        waitForElementsToBeInvisible(CIRCLE_SPINNER_LOCATOR, timeoutDuration);
        waitForElementsToBeInvisible(SMALL_CIRCLE_SPINNER_LOCATOR, timeoutDuration);
        waitForElementsToBeInvisible(SMALL_NOTCH_CIRCLE_SPINNER_LOCATOR, timeoutDuration);
        waitForElementsToBeInvisible(CUI_SPINNER_LOCATOR, timeoutDuration);
    }

    public static void waitForAllSpinnersToDisappear() {
        waitForAllSpinnersToDisappear(Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForCuiSpinnerToDisappear() {
        waitSoftlyForElementToBePresent(CUI_SPINNER_LOCATOR);
        waitForElementToBeInvisible(CUI_SPINNER_LOCATOR);
    }

    public static void waitForElementAndClick(WebElement element) {
        waitForElementToBeClickable(element);
        scrollToElementAndClick(element);
    }

    public static void waitSoftlyForAnyOfElementsToBePresent(WebElement... elements) {
        stream(elements).forEach(element -> waitSoftlyForElementToBePresent(getBy(element)));
    }

    public static void waitForPageContainerToLoadAndScrollToTheTop(WebElement pageContainer) {
        var timeout = Timeouts.TIMEOUT_1_MINUTE;
        waitForAllSpinnersToDisappear(timeout);
        waitForPageToBeReady(pageContainer, timeout);
        scrollToTheTop();
    }
}

