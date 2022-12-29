package pl.iTechArt.tests.end2end;

import org.testng.annotations.Test;
import pl.iTechArt.utility.BaseTest;

import java.io.IOException;

public class CompleteOrderTest extends BaseTest {

    @Test
    public void completeOrderTest() throws IOException {
        launchApplication().logIn("mail12@gmail.com","Test321!")
                .addToCartTwoItems("ZARA COAT 3", "IPHONE 13 PRO")
                //.getListOfProductsAddedToCart();
                .goToCart()
                .goToCheckout();
    }
}
