package pl.iTechArt.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.iTechArt.utility.BasePage;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "products")
    private WebElement products;

    public boolean isProductVisible() {
        webDriverWait.until(ExpectedConditions.visibilityOf(products));
        return products.isDisplayed();
    }
}
