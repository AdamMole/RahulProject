package pl.iTechArt.tests.login;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.iTechArt.utility.BaseTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterTests extends BaseTest {

    @Test
    public void registerTestShortPassword() throws IOException {

        Boolean result = launchApplication().clickOnRegisterButton()
                                            .registerForm("Adam","Adamski","mail12@gmail.com", 1234567890, "MALE", "Nic321")
                                            .isAlertVisible();

        Assert.assertTrue(result);
    }

    @Test
    public void registerTestUsedEmail() throws IOException {

        String result = launchApplication().clickOnRegisterButton()
                                            .registerForm("Adam","Adamski","mail12@gmail.com", 1234567890, "MALE", "Nic32111!")
                                            .getAlertMess();

        Assert.assertEquals(result, "User already exisits with this Email Id!");
    }

    @Test
    public void registerTestGoodData() throws IOException {

        var date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HHmmss");
        System.out.println("Login to: " + dateFormat.format(date));

        String result = launchApplication().clickOnRegisterButton()
                .registerForm("Adam","Adamski",dateFormat.format(date)+ "@gmail.com", 1234567890, "MALE", "Nic32111!")
                .getCreatedAccountHeader();

        Assert.assertEquals(result, "Account Created Successfully");
    }

}
