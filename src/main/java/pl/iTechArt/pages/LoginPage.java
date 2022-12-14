package pl.iTechArt.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.iTechArt.utility.BasePage;
@Slf4j
public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[text()='Register here']")
    private WebElement registerHereButton;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "userEmail")
    private WebElement email;

    @FindBy(id = "userMobile")
    private WebElement userMobile;

    @FindBy(css = "[value='Male']")
    private WebElement male;

    @FindBy(css = "[value='Female']")
    private WebElement female;

    @FindBy(id = "userPassword")
    private WebElement userPassword;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPassword;

    @FindBy(css = "[type='checkbox']")
    private WebElement checkbox;

    @FindBy(id = "login")
    private WebElement registerButton;

    @FindBy(css = "div[role='alertdialog']")
    private WebElement alert;

    @FindBy(xpath = "//h1[text()='Account Created Successfully']")
    private WebElement createdAccountHeader;

    @FindBy(id = "login")
    private WebElement loginButton;

    public LoginPage clickOnRegisterButton(){
        registerHereButton.click();
        return this;
    }

    public LoginPage registerForm(String name, String surname, String mail, int phone, String gender, String pass){
        webDriverWait.until(ExpectedConditions.visibilityOf(firstName));
        firstName.sendKeys(name);
        lastName.sendKeys(surname);
        email.sendKeys(mail);
        userMobile.sendKeys(String.valueOf(phone));
        selectGender(gender);
        userPassword.sendKeys(pass);
        confirmPassword.sendKeys(pass);
        checkbox.click();
        registerButton.click();
        return this;
    }

    private void selectGender(String gen){
        if(gen.equalsIgnoreCase("male")){
            male.click();
        } else {
            female.click();
        }
    }

    public LoginPage logInError(String mail, String password) {
        email.sendKeys(mail);
        userPassword.sendKeys(password);
        loginButton.click();
        return this;
    }

    public DashboardPage logIn(String mail, String password) {
        email.sendKeys(mail);
        userPassword.sendKeys(password);
        loginButton.click();
        return new DashboardPage(driver);
    }

    public boolean isAlertVisible() {
        webDriverWait.until(ExpectedConditions.visibilityOf(alert));
        System.out.println(getAlertMess());
        return alert.isDisplayed();
    }

    public String getAlertMess() {
        webDriverWait.until(ExpectedConditions.visibilityOf(alert));
        String alertMess = alert.getText();
        return alertMess;
    }

    public String getCreatedAccountHeader() {
        webDriverWait.until(ExpectedConditions.visibilityOf(createdAccountHeader));
        return createdAccountHeader.getText();
    }

}
