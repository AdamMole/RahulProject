package pl.iTechArt.tests.checkout;

import org.testng.annotations.Test;
import pl.iTechArt.utility.BaseTest;

import java.io.IOException;

public class AddNewProductsToCartTests extends BaseTest {

    @Test
    public void addNewProductsToCart() throws IOException {
        launchApplication().logIn("mail12@gmail.com","Test321!")
                .addToCartSecondApproach();
    }

}
