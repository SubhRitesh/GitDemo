package automation.tests;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import automation.TestComponents.BaseTest;
import automation.TestComponents.Retry;
import automation.pageobjects.ProductCatalogue;
import automation.pageobjects.CartPage;

public class ErrorValidationTest extends BaseTest{	
	@Test
	public void loginErrorValidation() throws IOException {		
		landingPage.loginApplication("blackjack@gmail.com", "invalidPassword");			
		Assert.assertEquals(landingPage.getToastErrorMessage(), "Incorrect email or password.");
	}

	@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void productErrorValidation() throws IOException {
		String itemName = "ZARA COAT 3";		
		ProductCatalogue productCatalogue = landingPage.loginApplication("blackjack@gmail.com", "Test!234");		
		productCatalogue.addProductToCart(itemName);
		CartPage shoppingCart = productCatalogue.clickOnCart();//Product Added To Cart
		//Validating correct product is added in the cart
		Assert.assertTrue(shoppingCart.isProductAvailableInCart("ZARA COAT 3"), "Expected product not available in the cart");
	}
}
