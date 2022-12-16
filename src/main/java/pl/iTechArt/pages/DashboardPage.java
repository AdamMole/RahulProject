package pl.iTechArt.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.iTechArt.helpers.ElementWait;
import pl.iTechArt.utility.BasePage;

import java.util.*;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    //ElementWait elementWait = new ElementWait(driver);

    @FindBy(css = "[class='logo']")
    private WebElement logo;

    @FindBy(css = ".mb-3")
    private List<WebElement> products;

    @FindBy(css = "h5 b")
    private List<WebElement> productsListNames;

    @FindBy(css = "[class='ng-tns-c31-1 ng-star-inserted']")
    private WebElement spinner;

    @FindBy(css = "[class='btn w-10 rounded']")
    private List<WebElement> addToCartButton;

    private By productListBy = By.cssSelector(".mb-3");
    private By addToCartButtonBy = By.cssSelector("[class='btn w-10 rounded']");

    public boolean isProductVisible() {
        webDriverWait.until(ExpectedConditions.visibilityOf(logo));
        return logo.isDisplayed();
    }

    public List<WebElement> getProductList() {
        //elementWait.waitForElementToApearWait(productListBy);
        waitForElementToApear(productListBy);
        return products;
    }

    public WebElement getProductByName(String productName) {
        WebElement prod = getProductList().stream().filter(p ->
                        p.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName))
                        .findFirst()
                        .orElse(null);
        return prod;
    }

    public DashboardPage addProductToCart(String productName) {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCartButtonBy).click();
        waitForElementToDissapear(spinner);
        return this;
    }

    public DashboardPage addToCartFirstApproach() {
        webDriverWait.until(ExpectedConditions.visibilityOf(logo));
        List<String> itemNeededList = Arrays.asList("ZARA COAT 3","IPHONE 13 PRO");
        for(int i = 0; i < productsListNames.size(); i++) {
            String name = productsListNames.get(i).getText();
            if(itemNeededList.contains(name)){
                //driver.findElements(By.cssSelector("[class='btn w-10 rounded']")).get(i).click();
                addToCartButton.get(i).click();
                webDriverWait.until(ExpectedConditions.invisibilityOf(spinner));
            }
        }
        return this;
    }
    public DashboardPage addToCartSecondApproach() {
        webDriverWait.until(ExpectedConditions.visibilityOf(logo));
        List<String> itemNeededList = Arrays.asList("ZARA COAT 3","IPHONE 13 PRO");
        for(String product : itemNeededList){
            WebElement prod = products.stream().filter(p ->
                            p.findElement(By.cssSelector("h5 b")).getText().equalsIgnoreCase("ZARA COAT 3"))
                    .findFirst()
                    .orElse(null);
            System.out.println(prod.getText());
            prod.findElement(By.cssSelector("[class='btn w-10 rounded']")).click();
            webDriverWait.until(ExpectedConditions.invisibilityOf(spinner));
        }
        return this;
    }

    public DashboardPage addToCartTwoItems(String firstProduct, String secondProduct) {
        waitForElementToApear(logo);
        List<String> itemNeededList = Arrays.asList(firstProduct,secondProduct);
        for(int i = 0; i < productsListNames.size(); i++) {
            String name = productsListNames.get(i).getText();
            if(itemNeededList.contains(name)){
                addToCartButton.get(i).click();
                waitForElementToDissapear(spinner);
            }
        }
        return this;
    }

}
