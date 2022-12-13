package pl.iTechArt.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.iTechArt.utility.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[text()='Register here']")
    private WebElement registerHereButton;

    public void clickOnRegisterButton(){
        registerHereButton.click();
    }
}
