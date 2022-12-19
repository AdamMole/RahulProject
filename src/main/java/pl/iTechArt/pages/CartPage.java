package pl.iTechArt.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.iTechArt.utility.BasePage;

import java.util.List;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    DashboardPage dashboardPage = new DashboardPage(driver);

    @FindBy(css = "li h3")
    private List<WebElement> productList;

    @FindBy(css = "li[class='totalRow'] button[type='button']")
    private WebElement checkoutButton;

    public CartPage checkIfProductsInCartAreCorrect() {
        waitForElementToApear(checkoutButton);
        List<String> products = dashboardPage.getListOfProductsAddedToCart();
        System.out.println("Products from getter: " + products);
        products.stream().forEach(System.out::println);
        productList.stream().forEach(System.out::println);

        return this;
    }
}
