package helpers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class WebElementHelper {
    private static final Logger log = LoggerFactory.getLogger(WebElementHelper.class);
    private static final String BUTTON_WITH_LABEL_LOCATOR = "//button[contains(.,'%s')]";
    private static final String FIELD_NAME_PROXY_ORIGIN = "h";
    private static final String FIELD_NAME_LOCATOR = "locator";
    private static final String FIELD_NAME_BY = "by";
    private static final Integer MAX_RETRY_ATTEMPTS = 3;
    private static final String LABEL_WITH_COLON_PATTERN = ".*: ";

    public WebElementHelper() {
    }

    public static void clearAndType(WebElement inputWebElement, String stringToFill) {
        scrollToElement(inputWebElement);
        inputWebElement.sendKeys(new CharSequence[]{Keys.CONTROL, Keys.chord(new CharSequence[]{"a"})});
        inputWebElement.sendKeys(new CharSequence[]{Keys.BACK_SPACE});
        inputWebElement.sendKeys(new CharSequence[]{stringToFill});
    }

    public static void clickOnElement(WebElement element, boolean scrollToElement) {
        if (scrollToElement) {
            scrollToElement(element);
        }

        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("arguments[0].click();", new Object[]{element});
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("arguments[0].scrollIntoView({inline: \"start\"});  window.scrollBy(0, -window.innerHeight/8);", new Object[]{element});
    }

    public static void scrollElementToPageCenter(WebElement element) {
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("arguments[0].scrollIntoView({behavior: \"auto\", block: \"center\"});", new Object[]{element});
    }

    public static void scrollToElementIntoView(WebElement element) {
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("arguments[0].scrollIntoView();", new Object[]{element});
    }

    public static void scrollToTheTop() {
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("window.scrollTo(0, 0)", new Object[0]);
    }

    public static void scrollToTheBottom() {
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)", new Object[0]);
    }

    public static void scrollWithinElementToTheBottom(WebElement element) {
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight)", new Object[]{element});
    }

    public static WebElement getWebElement(By locator) {
        return WebDriverProvider.getDriver().findElement(locator);
    }

    public static List<WebElement> getWebElements(By locator) {
        return WebDriverProvider.getDriver().findElements(locator);
    }

    public static WebElement getElementOrNull(By locator) {
        try {
            return getWebElement(locator);
        } catch (NoSuchElementException var2) {
            log.info("Element {} was not found! NULL is returned.", locator);
            return null;
        }
    }

    public static WebElement getChildElementOrNull(WebElement parentElement, By locator) {
        if (null == parentElement) {
            log.info("Parent element is null, searching of child is impossible! NULL is returned.");
            return null;
        } else {
            try {
                return parentElement.findElement(locator);
            } catch (NoSuchElementException var3) {
                log.info("Element {} was not found in parentElement! NULL is returned.", locator);
                return null;
            }
        }
    }

    public static boolean elementHasClass(WebElement element, String expectedClass) {
        return element.getAttribute("class").contains(expectedClass);
    }

    public static int getNumberOfWebElements(By locator) {
        return getWebElements(locator).size();
    }

    public static int getNumberOfWebElements(WebElement parentElement, By locator) {
        return parentElement.findElements(locator).size();
    }

    public static WebElement getParentElement(WebElement element) {
        return (WebElement)((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("return arguments[0].parentNode;", new Object[]{element});
    }

    public static boolean elementHasTag(WebElement webElement, String expectedTag) {
        return webElement.getTagName().equals(expectedTag);
    }

    public static boolean elementHasTag(By locator, String expectedTag) {
        return elementHasTag(getWebElement(locator), expectedTag);
    }

    public static String getElementText(By by) {
        return getWebElement(by).getText();
    }

    public static String getElementTrimmedTextWithoutLabel(WebElement element) {
        return getElementTrimmedText(element).replaceFirst(".*: ", "");
    }

    public static List<String> getTextsFromListOfWebElements(List<WebElement> webElementsList) {
        return (List)webElementsList.stream().map(WebElementHelper::getElementTrimmedText).collect(Collectors.toList());
    }

    public static String getElementTextWithRemovedGarbage(WebElement element, String patternForRemoving) {
        return getElementTrimmedText(element).replaceFirst(patternForRemoving, "").trim();
    }

    public static String getElementTrimmedText(WebElement element) {
        return element.getText().trim();
    }

//    public static int getIntFromElementText(WebElement element) {
//        String text = getElementTrimmedText(element);
//        return TextHelper.getNumberFromString(text);
//    }
//
//    static ExpectedCondition<Boolean> isAttributeEqualTo(WebElement element, String attrName, String attrValue) {
//        return (input) -> {
//            return TextHelper.areNullableStringsEqual(element.getAttribute(attrName), attrValue);
//        };
//    }

    public static By getButtonByLabel(String buttonLabel) {
        return By.xpath(String.format("//button[contains(.,'%s')]", buttonLabel));
    }

    public static void clearByCharAndType(WebElement inputWebElement, String text) {
        scrollToElement(inputWebElement);
        clearByChar(inputWebElement);
        inputWebElement.sendKeys(new CharSequence[]{text});
    }

    private static void clearByChar(WebElement inputElement) {
        int fieldValueLength = inputElement.getAttribute("value").length();
        inputElement.sendKeys(new CharSequence[]{Keys.END});

        while(fieldValueLength > 0) {
            inputElement.sendKeys(new CharSequence[]{Keys.BACK_SPACE});
            --fieldValueLength;
        }

    }

    public static boolean isElementEnabled(WebElement element) {
        String classAttribute = element.getAttribute("class");
        return null != classAttribute && !classAttribute.contains("disabled");
    }

    public static Select getSelectByLocator(By locator) {
        return new Select(getWebElement(locator));
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (StaleElementReferenceException | NoSuchElementException var2) {
            log.info("Exception caused by isDisplayed {}", var2.getMessage());
            return false;
        }
    }

    public static boolean isNotDisplayed(WebElement element) {
        return !isDisplayed(element);
    }

    public static boolean isNotDisplayed(By by) {
        return !isDisplayed(by);
    }

    public static boolean isDisplayed(WebElement parentElement, By locator) {
        try {
            return parentElement.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | NullPointerException var3) {
            log.info("Exception caused by isDisplayed {}", var3.getMessage());
            return false;
        }
    }

    public static boolean isDisplayed(By locator) {
        try {
            return WebDriverProvider.getDriver().findElement(locator).isDisplayed();
        } catch (NoSuchElementException | NullPointerException var2) {
            return false;
        }
    }

    public static boolean isEachDisplayed(WebElement... webElements) {
        return Arrays.stream(webElements).allMatch(WebElementHelper::isDisplayed);
    }

    public static boolean isAnyDisplayed(WebElement... webElements) {
        return Arrays.stream(webElements).anyMatch(WebElementHelper::isDisplayed);
    }

    public static boolean isEachDisplayed(WebElement parentElement, By... locators) {
        return Arrays.stream(locators).allMatch((locator) -> {
            return isDisplayed(parentElement, locator);
        });
    }

    public static boolean isEachDisplayed(List<WebElement> webElements) {
        return webElements.stream().allMatch(WebElementHelper::isDisplayed);
    }

    public static boolean isVisible(WebElement element) {
        if (!isDisplayed(element)) {
            return false;
        } else {
            return !Objects.equals(element.getAttribute("offsetHeight"), "0") && !Objects.equals(element.getAttribute("offsetWidth"), "0");
        }
    }

    public static boolean isVisible(By locator) {
        try {
            return isVisible(getWebElement(locator));
        } catch (NoSuchElementException var2) {
            return false;
        }
    }

    public static boolean isPresent(By locator) {
        return !WebDriverProvider.getDriver().findElements(locator).isEmpty();
    }

    public static boolean isClickable(WebElement element) {
        try {
            return element.isDisplayed() && element.isEnabled();
        } catch (NoSuchElementException var2) {
            return false;
        }
    }

    public static boolean isClickable(WebElement parentElement, By locator) {
        return isClickable(parentElement.findElement(locator));
    }

    public static boolean isClickable(By locator) {
        return isClickable(WebDriverProvider.getDriver().findElement(locator));
    }

    public static By getBy(Object elementObject) {
        By extractedBy = extractBy(elementObject);

        try {
            return extractedBy;
        } catch (NoSuchElementException var3) {
            log.info("Element {} was not found! NULL is returned.", extractedBy);
            return null;
        }
    }

    static String getStringBy(Object elementObject) {
        By extractedBy = extractBy(elementObject);
        return null != extractedBy ? extractedBy.toString() : "UNKNOWN \"BY\"";
    }

    private static By extractBy(Object elementObject) {
        By extractedBy = null;

        try {
            Object proxyOrigin = FieldUtils.readField(elementObject, "h", true);
            Object locator = FieldUtils.readField(proxyOrigin, "locator", true);
            Object findBy = FieldUtils.readField(locator, "by", true);
            if (findBy != null) {
                extractedBy = (By)findBy;
            }
        } catch (IllegalArgumentException | IllegalAccessException var5) {
            log.info("Exception occurred while reading field value, returning custom invalid By");
        }

        return extractedBy;
    }

    public static boolean elementHasAttribute(WebElement element, String attribute) {
        return !StringUtils.isBlank(element.getAttribute(attribute));
    }

    public static void retryWaitForElementsToBeEnabled(List<WebElement> webElements) {
        retryWaitForElementsToBeEnabled(webElements, MAX_RETRY_ATTEMPTS);
    }

    public static void retryWaitForElementsToBeEnabled(List<WebElement> webElements, int maxRetry) {
        for(int attempts = 0; attempts < maxRetry; ++attempts) {
            try {
                webElements.forEach(ElementWaitHelper::waitForElementToBeEnabled);
            } catch (StaleElementReferenceException var4) {
                log.info("Stale Element Reference Exception");
            }
        }

    }

    public static void selectCheckboxByClickingLabel(WebElement checkbox) {
        if (!checkbox.isSelected()) {
            getParentElement(checkbox).click();
        }

    }

    public static void moveToElement(WebElement element) {
        (new Actions(WebDriverProvider.getDriver())).moveToElement(element).perform();
    }

    public static void clickOutsideElement() {
        getWebElement(By.tagName("html")).click();
    }

    public static boolean isElementNotOverflown(WebElement element) {
        String script = "const element = arguments[0];const boundingRect = element.getBoundingClientRect();const elementX = boundingRect.left + boundingRect.width/2;const elementY = boundingRect.top + boundingRect.height/2;if (document.elementFromPoint(elementX, elementY) == element) {   return true;}return false;";
        return (Boolean)((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript(script, new Object[]{element});
    }

    public static boolean elementHasClassAttributeValue(WebElement element, String expectedValue) {
        return element.getAttribute("class").contains(expectedValue);
    }

    public static boolean elementHasClassAttributeValueMatchingRegex(WebElement element, String regex) {
        return element.getAttribute("class").matches(regex);
    }

    private static boolean hasEnoughTimePassedSince(long timeSinceDropdownUpdate, Duration secondsToWait) {
        return timeSinceDropdownUpdate > System.currentTimeMillis() - secondsToWait.toMillis();
    }

    public static String getColorFromElement(WebElement element) {
        return getColorFromStringAsHex(getCssValue(element, "color"));
    }

    public static String getBackgroundColorFromElement(WebElement element) {
        return getColorFromStringAsHex(getCssValue(element, "background-color"));
    }

    public static String getFontSizeFromElement(WebElement element) {
        return getCssValue(element, "font-size");
    }

    private static String getCssValue(WebElement element, String cssProperty) {
        return element.getCssValue(cssProperty);
    }

    private static String getColorFromStringAsHex(String value) {
        return Color.fromString(value).asHex();
    }

    public static void scrollToElementAndClick(WebElement element) {
        scrollToElementIntoView(element);
        element.click();
    }

    public static String getElementAttribute(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    public static void scrollWithinElementToTheTop(WebElement element) {
        ((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("arguments[0].scrollTo(0, 0)", new Object[]{element});
    }

    public static String getTextFromElement(WebElement element) {
        return String.valueOf(((JavascriptExecutor)WebDriverProvider.getDriver()).executeScript("return arguments[0].innerText", new Object[]{element}));
    }
}

