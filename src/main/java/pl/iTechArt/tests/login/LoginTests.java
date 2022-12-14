package pl.iTechArt.tests.login;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.iTechArt.utility.BaseTest;

import java.io.IOException;

public class LoginTests extends BaseTest {

    @Test
    public void loginErrorData() throws IOException {

        Boolean result = launchApplication().logInError("mail12@gmail.com","Test321")
                            .isAlertVisible();

        Assert.assertTrue(result);
    }

    @Test
    public void loginCorrectData() throws IOException {

        Boolean result = launchApplication().logIn("mail12@gmail.com","Test321!")
                            .isProductVisible();

        Assert.assertTrue(result);
    }
}
