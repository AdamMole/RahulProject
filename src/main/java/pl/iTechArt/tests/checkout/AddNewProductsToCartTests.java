package pl.iTechArt.tests.checkout;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.iTechArt.utility.BaseTest;

import java.io.IOException;

public class AddNewProductsToCartTests extends BaseTest {

    @Test
    public void checkIfProductIsOnList() throws IOException {
        Boolean result = launchApplication().logIn("mail12@gmail.com","Test321!")
                                            .isProductVisible("Adidas original");

        Assert.assertTrue(result);
    }

    @Test
    public void checkIfProductsAreSortedByCategory() throws IOException, InterruptedException {
        Boolean result = launchApplication().logIn("mail12@gmail.com", "Test321!")
                                            .productsSortedByCategory("fashion");

        Assert.assertTrue(result);
    }

    @Test
    public void addNewProductsToCart() throws IOException {
        Boolean result = launchApplication().logIn("mail12@gmail.com","Test321!")
                            .addToCartTwoItems("ZARA COAT 3", "IPHONE 13 PRO")
                            //.getListOfProductsAddedToCart();
                            .goToCart()
                            .checkIfProductsInCartAreCorrect();

        Assert.assertTrue(result);
    }

}
