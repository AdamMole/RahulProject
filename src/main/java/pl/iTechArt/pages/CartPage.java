package pl.iTechArt.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.iTechArt.utility.BasePage;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    DashboardPage dashboardPage = new DashboardPage(driver);

    @FindBy(css = "li h3")
    private List<WebElement> productList;

    @FindBy(css = "li[class='totalRow'] button[type='button']")
    private WebElement checkoutButton;

    public boolean checkIfProductsInCartAreCorrect() {
        waitForElementToApear(checkoutButton);
        List<String> products = dashboardPage.getListOfProductsAddedToCart();
        System.out.println("Products from getter: " + products);
        //products.stream().forEach(System.out::println);
        List<String> productListText = productList.stream().map(p -> p.getText()).collect(Collectors.toList());
        //productListText.forEach(el -> System.out.println(el));
        //Poniższy zapis do poprawy
        boolean result = true;
        for(String product : products){
            if(productListText.contains(product)) {
                result = true;
            }
        }
        return result;
    }

    private boolean checkSizeOfCart() {
        waitForElementToApear(checkoutButton);
        List<String> products = dashboardPage.getListOfProductsAddedToCart();
        System.out.println("Size from getter: " + products.size());
        List<String> productListText = productList.stream().map(p -> p.getText()).collect(Collectors.toList());
        System.out.println("Size of elements on page: " + productListText.size());
        boolean res = false;
        if(products.size() == productListText.size()) {
            res = true;
        }
        return res;
    }

    public OrderSummary goToCheckout() {
        if(checkSizeOfCart()) {
            checkoutButton.click();
        }
        return new OrderSummary(driver);
    }
}
