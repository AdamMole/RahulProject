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
        elementWait = new ElementWait(driver);
    }

    private ElementWait elementWait;

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

    @FindBy(xpath = "(//div[@class='py-2 border-bottom ml-3'])[3]//label")
    private List<WebElement> categoriesNames;

    @FindBy(xpath = "(//div[@class='py-2 border-bottom ml-3'])[3]/div/input")
    private List<WebElement> categoriesCheckbox;

    @FindBy(id = "res")
    private WebElement numberOfProducts;

    @FindBy(css = "[routerlink='/dashboard/cart']")
    private WebElement cartButton;

    private static List<String> elements = new ArrayList<>();

    private By productListBy = By.cssSelector(".mb-3");
    private By addToCartButtonBy = By.cssSelector("[class='btn w-10 rounded']");

    public boolean areProductsVisible() {
        webDriverWait.until(ExpectedConditions.visibilityOf(logo));
        return logo.isDisplayed();
    }

    public List<WebElement> getProductList() {
        elementWait.waitForElementToApear(productListBy);
        //waitForElementToApear(productListBy);
        return products;
    }

    public WebElement getProductByName(String productName) {
        WebElement prod = getProductList().stream().filter(p ->
                        p.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName))
                        .findFirst()
                        .orElse(null);
        return prod;
    }

    public boolean isProductVisible(String productName) {
        webDriverWait.until(ExpectedConditions.visibilityOf(logo));
        Boolean result = productsListNames.stream().anyMatch(p -> p.getText().equalsIgnoreCase(productName));
        return result;
    }

    public DashboardPage addProductToCart(String productName) {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCartButtonBy).click();
        waitForElementToDissapear(spinner);
        return this;
    }

    public CartPage goToCart() {
        cartButton.click();
        System.out.println("Getter gotocart: " + getListOfProductsAddedToCart());
        return new CartPage(driver);
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
        setListOfProductsAddedToCart(itemNeededList);
        for(int i = 0; i < productsListNames.size(); i++) {
            String name = productsListNames.get(i).getText();
            if(itemNeededList.contains(name)){
                //elements.add(name);
                addToCartButton.get(i).click();
                waitForElementToDissapear(spinner);
            }
        }
        return this;
    }

    private void setListOfProductsAddedToCart(List<String> elements) {
        this.elements = elements;
    }

    public static List<String> getListOfProductsAddedToCart() {
        System.out.println("Getter: " + elements);
        return elements;
    }

    public String selectCategory(String categoryName) {
        waitForElementToApear(logo);
        String category = "";
        for(int i = 0; i < categoriesNames.size(); i++) {
            if(categoriesNames.get(i).getText().equalsIgnoreCase(categoryName)) {
                category = categoriesNames.get(i).getText();
                categoriesCheckbox.get(i).click();
            }
        }
        return category;
    }

    public Boolean productsSortedByCategory(String categoryName) {
        Boolean result = false;
        if(selectCategory(categoryName).equalsIgnoreCase("electronics")){
            waitForElementToApear(numberOfProducts);
            webDriverWait.until(ExpectedConditions.textToBePresentInElement(numberOfProducts,"1"));
            result = productsListNames.stream().anyMatch(p -> p.getText().equalsIgnoreCase("Iphone 13 pro"));
        } else {
            result = productsListNames.stream().anyMatch(p -> p.getText().equalsIgnoreCase("Zara coat 3"));
        }
        return result;
    }

}
