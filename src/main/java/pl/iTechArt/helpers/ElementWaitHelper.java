package pl.iTechArt.helpers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementWaitHelper {
    private static final Logger log = LoggerFactory.getLogger(ElementWaitHelper.class);
    private static final String ACTION_AJAX_CALL = "Ajax_call";

    public ElementWaitHelper() {
    }

    public static void waitForElementToBePresent(By locatedBy) {
        waitForElementToBePresent(locatedBy, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitSoftlyForElementToBePresent(By locatedBy) {
        waitSoftlyForElementToBePresent(locatedBy, Timeouts.TIMEOUT_3_SECONDS);
    }

    public static boolean waitSoftlyForElementToBePresent(By locatedBy, Duration timeoutDuration) {
        try {
            waitForElementToBePresent(locatedBy, timeoutDuration);
            return true;
        } catch (TimeoutException var3) {
            log.info("Element " + locatedBy + " not found");
            return false;
        }
    }

    public static void waitForElementToBePresent(By locatedBy, Duration timeoutDuration) {
        condition(ExpectedConditions.presenceOfElementLocated(locatedBy), timeoutDuration, "Waiting for element to be present...");
    }

    public static void waitForElementToBeEnabled(WebElement element) {
        waitForElementToBeEnabled(element, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToBeEnabled(WebElement element, Duration timeoutDuration) {
        condition(isElementEnabledCondition(element), timeoutDuration, "Waiting for element to be enabled...");
    }

    public static void waitForUrlContains(String urlText, Duration timeoutDuration) {
        condition(ExpectedConditions.urlContains(urlText), timeoutDuration, "Waiting for url contains " + urlText);
    }

    public static void waitForUrlContains(String urlText) {
        waitForUrlContains(urlText, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForUrlMatches(String regex, Duration timeoutDuration) {
        condition(ExpectedConditions.urlMatches(regex), timeoutDuration, String.format("Waiting for url matches: '%s'", regex));
    }

    public static void waitForUrlMatches(String regex) {
        waitForUrlMatches(regex, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToHaveClass(WebElement element, String expectedClass) {
        waitForElementToHaveClass(element, expectedClass, Timeouts.DEFAULT_TIMEOUT);
    }

    private static void waitForElementToHaveClass(WebElement element, String expectedClass, Duration timeoutDuration) {
        condition(elementHasClassCondition(element, expectedClass), timeoutDuration, String.format("Waiting for element located %s to has class %s", WebElementHelper.getStringBy(element), expectedClass));
    }

    public static void waitForElementToHaveNoClass(WebElement element, String expectedClass) {
        waitForElementToHaveNoClass(element, expectedClass, Timeouts.TIMEOUT_1_MINUTE);
    }

    private static void waitForElementToHaveNoClass(WebElement element, String expectedClass, Duration timeoutDuration) {
        condition(elementHasNoClassCondition(element, expectedClass), timeoutDuration, String.format("Waiting for element located %s to has no class %s", WebElementHelper.getStringBy(element), expectedClass));
    }

    public static void waitForInputToBeNotEmpty(WebElement element, Duration timeoutDuration) {
        condition(isInputNotEmptyCondition(element), timeoutDuration, "Waiting for input element to be not empty...");
    }

    public static void waitForInputToBeNotEmpty(WebElement element) {
        waitForInputToBeNotEmpty(element, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToBeVisible(By element) {
        waitForElementToBeVisible(element, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForNestedElementToBeVisible(WebElement parentElement, By element) {
        waitForNestedElementToBeVisible(parentElement, element, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForNestedElementToBeVisible(WebElement parentElement, By element, Duration timeoutDuration) {
        condition(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, element), timeoutDuration, "Waiting for nested element to be visible...");
    }

    public static void waitForElementToBeVisible(By element, Duration timeoutDuration) {
        condition(ExpectedConditions.visibilityOfElementLocated(element), timeoutDuration, "Waiting for element to be visible...");
    }

    public static void waitForEitherOfElementsToBeVisible(WebElement firstElement, WebElement secondElement) {
        condition(ExpectedConditions.or(new ExpectedCondition[]{ExpectedConditions.visibilityOf(firstElement), ExpectedConditions.visibilityOf(secondElement)}), Timeouts.DEFAULT_TIMEOUT, "Waiting for either element to be steady...");
    }

    public static void waitForAnyOfElementsToBeVisible(WebElement... webElements) {
        ExpectedCondition<?>[] expectedConditions = (ExpectedCondition[])Arrays.stream(webElements).map(ExpectedConditions::visibilityOf).toArray((x$0) -> {
            return new ExpectedCondition[x$0];
        });
        condition(ExpectedConditions.or(expectedConditions), Timeouts.DEFAULT_TIMEOUT, "Waiting for any of the provided elements to be visible");
    }

    public static void waitForAnyElementToSatisfyCondition(By by, Predicate<WebElement> predicate) {
        condition((webDriver) -> {
            return ((WebDriver)Objects.requireNonNull(webDriver)).findElements(by).stream().anyMatch(predicate);
        }, Timeouts.DEFAULT_TIMEOUT, "Waiting for element to satisfy a specific condition");
    }

    public static void waitForElementToBeSelected(WebElement element) {
        condition(ExpectedConditions.elementToBeSelected(element), Timeouts.DEFAULT_TIMEOUT, "Waiting for element to be selected...");
    }

    public static void waitForAllElementsToBeVisible(List<WebElement> elements) {
        condition(ExpectedConditions.visibilityOfAllElements(elements), Timeouts.DEFAULT_TIMEOUT, "Waiting for elements to be visible...");
    }

    public static void waitForElementsToBeInvisible(List<WebElement> elements) {
        waitForElementsToBeInvisible(elements, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementsToBeInvisible(List<WebElement> elements, Duration timeoutDuration) {
        condition(ExpectedConditions.invisibilityOfAllElements(elements), timeoutDuration, "Waiting for elements to be invisible...");
    }

    public static void waitForElementToBeInvisible(WebElement element) {
        waitForElementToBeInvisible(element, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToBeInvisible(WebElement element, Duration timeoutDuration) {
        condition(ExpectedConditions.invisibilityOf(element), timeoutDuration, "Waiting for element to be invisible...");
    }

    public static void waitForElementToBeInvisible(By locator) {
        waitForElementToBeInvisible(locator, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToBeInvisible(By locator, Duration timeoutDuration) {
        condition(ExpectedConditions.invisibilityOfElementLocated(locator), timeoutDuration, "Waiting for element to be invisible...");
    }

    public static void waitForElementToHaveText(WebElement element, String text, Duration timeoutDuration) {
        condition(elementHasText(element, text), timeoutDuration, String.format("Waiting for element to have a text: '%s'", text));
    }

    public static void waitForElementToHaveText(WebElement element, String text) {
        waitForElementToHaveText(element, text, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementContainsText(WebElement element, String text, Duration timeoutDuration) {
        condition(elementContainsText(element, text), timeoutDuration, String.format("Waiting for element contains text: '%s'", text));
    }

    public static void waitForElementContainsText(WebElement element, String text) {
        waitForElementContainsText(element, text, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToHaveText(By locator, String text, Duration timeoutDuration) {
        condition(ExpectedConditions.textToBe(locator, text), timeoutDuration, String.format("Waiting for element to have a text: '%s'", text));
    }

    public static void waitForElementToHaveText(By locator, String text) {
        waitForElementToHaveText(locator, text, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToHaveTextMatchesPattern(By locator, Pattern text, Duration timeoutDuration) {
        condition(ExpectedConditions.textMatches(locator, text), timeoutDuration, String.format("Waiting for element to have text that matches pattern '%s'", text));
    }

    public static void waitForElementToHaveTextMatchesPattern(By locator, Pattern text) {
        waitForElementToHaveTextMatchesPattern(locator, text, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementContainsText(By locator, String text, Duration timeoutDuration) {
        condition(ExpectedConditions.textToBePresentInElementLocated(locator, text), timeoutDuration, String.format("Waiting for element contains text: '%s'", text));
    }

    public static void waitForElementContainsText(By locator, String text) {
        waitForElementContainsText(locator, text, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementWithTextToBeInvisible(By locator, String text) {
        waitForElementWithTextToBeInvisible(locator, text, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementWithTextToBeInvisible(By locator, String text, Duration timeoutDuration) {
        condition(ExpectedConditions.invisibilityOfElementWithText(locator, text), timeoutDuration, "Waiting for element with text to be invisible...");
    }

    public static void waitForElementToBeVisible(WebElement element) {
        waitForElementToBeVisible(element, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToBeVisible(WebElement element, Duration timeoutDuration) {
        condition(ExpectedConditions.visibilityOf(element), timeoutDuration, "Waiting for element to be visible...");
    }

    public static void waitForElementToBeClickable(WebElement element) {
        waitForElementToBeClickable(element, Timeouts.DEFAULT_TIMEOUT);
    }

    public static void waitForElementToBeClickable(WebElement element, Duration timeoutDuration) {
        condition(ExpectedConditions.elementToBeClickable(element), timeoutDuration, "Waiting for element to be clickable...");
    }

    public static void waitForElementToBeNotOverflown(WebElement element) {
        condition(elementToBeNotOverflown(element), Timeouts.DEFAULT_TIMEOUT, "Waiting for element to be not overflown by other elements...");
    }

    public static void waitForSelectBoxToBePopulated(Select select) {
        condition((input) -> {
            return Objects.nonNull(select.getFirstSelectedOption()) && StringUtils.isNotBlank(select.getFirstSelectedOption().getText());
        }, Timeouts.DEFAULT_TIMEOUT, "Waiting for select input to contain a value...");
    }

    public static void waitForElementToBeSteady(WebElement element, Duration timeout, Duration steadinessInterval) {
        condition(isElementSteadyCondition(element), timeout, steadinessInterval, "Waiting for element to be steady...");
    }

    public static void waitForElementToBeSteady(WebElement element) {
        waitForElementToBeSteady(element, Timeouts.DEFAULT_TIMEOUT, Timeouts.TIMEOUT_200_MILLISECONDS);
    }

    public static void waitForNumberOfElementsToBeSteady(WebElement container, By by) {
        waitForNumberOfElementsToBeSteady(container, by, Timeouts.TIMEOUT_200_MILLISECONDS);
    }

    public static void waitForAnyOfElementsToBeSteady(WebElement... elements) {
        Arrays.stream(elements).filter(WebElementHelper::isDisplayed).forEach(ElementWaitHelper::waitForElementToBeSteady);
    }

    public static void waitForAnyOfElementsToBeSteady(Duration timeout, Duration steadinessInterval, WebElement... elements) {
        Arrays.stream(elements).filter(WebElementHelper::isDisplayed).forEach((element) -> {
            waitForElementToBeSteady(element, timeout, steadinessInterval);
        });
    }

    public static void waitForNumberOfElementsToBeSteady(WebElement container, By by, Duration steadinessInterval) {
        condition(isNumberOfElementsSteadyCondition(container, by), Timeouts.DEFAULT_TIMEOUT, steadinessInterval, "Waiting for number of elements to be steady...");
    }

    public static void repeatUntilElementSatisfiesCondition(By by, Predicate<By> predicate, Runnable codeToRepeat) {
        condition(repeatUntilCondition(by, predicate, codeToRepeat), Timeouts.DEFAULT_TIMEOUT, Timeouts.TIMEOUT_1_SECOND, "Repeating code until element satisfies the condition...");
    }

    public static void condition(ExpectedCondition<?> condition, Duration timeoutDuration, String message) {
        condition(condition, timeoutDuration, Timeouts.DEFAULT_POLLING_INTERVAL, message);
    }

    public static void condition(ExpectedCondition<?> condition, Duration timeoutDuration, Duration pollingDuration, String message) {
        log.info("{} {}", message, condition);
        FluentWait<WebDriver> wait = (new FluentWait(WebDriverProvider.getDriver())).withTimeout(timeoutDuration).pollingEvery(pollingDuration).ignoring(JavascriptException.class).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class).withMessage(String.format("%s %s", message, condition));
        wait.until(condition);
    }

    private static ExpectedCondition<Boolean> isElementEnabledCondition(WebElement element) {
        return (input) -> {
            return element.isEnabled();
        };
    }

    public static void waitForAjax(Duration scriptTimeout, String action) {
        String script = "var callback = arguments[arguments.length - 1];var xhr = new XMLHttpRequest();xhr.open('POST', '/" + action + "', true);xhr.onreadystatechange = function() {  if (xhr.readyState == 4) {    callback(xhr.responseText);  }};xhr.send();";
        WebDriverProvider.getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(scriptTimeout.getSeconds()));
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeAsyncScript(script, new Object[0]);
    }

    private static ExpectedCondition<Boolean> elementHasClassCondition(WebElement element, String expectedClass) {
        return (input) -> {
            return WebElementHelper.elementHasClass(element, expectedClass);
        };
    }

    private static ExpectedCondition<Boolean> elementHasNoClassCondition(WebElement element, String expectedClass) {
        return (input) -> {
            return !WebElementHelper.elementHasClass(element, expectedClass);
        };
    }

    private static ExpectedCondition<Boolean> isInputNotEmptyCondition(WebElement element) {
        return (input) -> {
            return element.getAttribute("value").length() != 0;
        };
    }

    private static ExpectedCondition<Boolean> isElementSteadyCondition(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            private Point oldLocation = null;

            public Boolean apply(WebDriver webDriver) {
                if (WebElementHelper.isDisplayed(element)) {
                    Point location = element.getLocation();
                    if (location.equals(this.oldLocation)) {
                        return true;
                    }

                    this.oldLocation = location;
                }

                return false;
            }
        };
    }

    private static ExpectedCondition<Boolean> isNumberOfElementsSteadyCondition(final WebElement element, final By by) {
        return new ExpectedCondition<Boolean>() {
            private Long oldSize = null;

            public Boolean apply(WebDriver webDriver) {
                if (WebElementHelper.isDisplayed(element)) {
                    long newSize = element.findElements(by).stream().filter(WebElement::isDisplayed).count();
                    if (this.oldSize != null && this.oldSize == newSize) {
                        return true;
                    }

                    this.oldSize = newSize;
                }

                return false;
            }
        };
    }

    private static ExpectedCondition<Boolean> repeatUntilCondition(By by, Predicate<By> predicate, Runnable runnable) {
        return (webDriver) -> {
            if (predicate.test(by)) {
                return true;
            } else {
                runnable.run();
                return false;
            }
        };
    }

    private static ExpectedCondition<Boolean> elementHasText(WebElement element, String text) {
        return (input) -> {
            return WebElementHelper.getElementTrimmedText(element).equalsIgnoreCase(text);
        };
    }

    private static ExpectedCondition<Boolean> elementContainsText(WebElement element, String text) {
        return (input) -> {
            return StringUtils.containsIgnoreCase(WebElementHelper.getElementTrimmedText(element), text);
        };
    }

    public static void waitForElementsToBeInvisible(By locator, Duration timeoutDuration) {
        condition(invisibilityOfElementsLocated(locator), timeoutDuration, "Wait for elements to be invisible...");
    }

    private static ExpectedCondition<Boolean> invisibilityOfElementsLocated(By locator) {
        return (input) -> {
            return WebDriverProvider.getDriver().findElements(locator).stream().allMatch(WebElementHelper::isNotDisplayed);
        };
    }

//    public static void waitForElementToHaveEnabledAttribute(WebElement element, Duration timeout) {
//        condition(WebElementHelper.isAttributeEqualTo(element, "disabled", (String)null), timeout, Timeouts.DEFAULT_POLLING_INTERVAL, "Waiting for element to have enabled attribute...");
//    }

    public static void waitForElementTagAppearance(By locator, String expectedTag) {
        condition(elementHasTagCondition(locator, expectedTag), Timeouts.DEFAULT_TIMEOUT, String.format("Waiting for element to contain tag: %s...", expectedTag));
    }

    public static void waitForAtLeastOneElementLocatedByToBePresent(By locator, Duration timeoutDuration) {
        condition(ExpectedConditions.presenceOfAllElementsLocatedBy(locator), timeoutDuration, String.format("Waiting for at least one element located by %s to be present", locator));
    }

    private static ExpectedCondition<Boolean> elementHasTagCondition(By locator, String expectedTag) {
        return (input) -> {
            return WebElementHelper.elementHasTag(locator, expectedTag);
        };
    }

    private static ExpectedCondition<Boolean> elementToBeNotOverflown(WebElement element) {
        return (input) -> {
            return WebElementHelper.isElementNotOverflown(element);
        };
    }

//    public static void waitForElementToHaveAttributeValue(WebElement element, Duration timeout, String attribute, String value) {
//        condition(WebElementHelper.isAttributeEqualTo(element, attribute, value), timeout, Timeouts.DEFAULT_POLLING_INTERVAL, String.format("Waiting for element's attribute: %s - to have value: %s", attribute, value));
//    }

    public static void waitForAjaxCall(Duration scriptTimeout) {
        waitForAjax(scriptTimeout, "Ajax_call");
    }
}
