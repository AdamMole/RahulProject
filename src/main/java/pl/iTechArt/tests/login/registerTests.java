package pl.iTechArt.tests.login;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.iTechArt.utility.BaseTest;

import java.io.IOException;

public class registerTests extends BaseTest {

    @Test
    public void registerTestShortPassword() throws IOException {
        SoftAssert softly = new SoftAssert();

        Boolean result = launchApplication().clickOnRegisterButton()
                            .registerForm("Adam","Adamski","mail12@gmail.com", 1234567890, "MALE", "Nic321")
                            .isAlertVisible();

        Assert.assertTrue(result);
    }

}
