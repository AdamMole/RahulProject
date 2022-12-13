package pl.iTechArt.tests.login;

import org.testng.annotations.Test;
import pl.iTechArt.utility.BaseTest;

import java.io.IOException;

public class registerTests extends BaseTest {

    @Test
    public void registerTest() throws IOException {
        launchApplication().clickOnRegisterButton();
    }

}
