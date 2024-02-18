package automation.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.TestComponents.BaseTest;
import automation.TestComponents.Retry;
import automation.pageobjects.ConfirmationPage;
import automation.pageobjects.LandingPage;
import automation.pageobjects.OrderPage;
import automation.pageobjects.PaymentPage;
import automation.pageobjects.ProductCatalogue;
import automation.pageobjects.CartPage;

public class SubmitOrderTest extends BaseTest{	
	String itemName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = "Purchase", retryAnalyzer = Retry.class)
	public void submitOrder(HashMap<String, String> testData) throws IOException {

		//		LandingPage landingPage = launchApplication();
		ProductCatalogue productCatalogue = landingPage.loginApplication(testData.get("email"), testData.get("password"));		
		productCatalogue.addProductToCart(testData.get("product"));
		CartPage shoppingCart = productCatalogue.clickOnCart();//Product Added To Cart
		//Validating correct product is added in the cart
		Assert.assertTrue(shoppingCart.isProductAvailableInCart(testData.get("product")), "Expected product not available in the cart");
		PaymentPage payment = shoppingCart.clickOnCheckout();
		//payment details
		payment.enterCardHolderName("Jack");
		payment.enterCVV("123");
		payment.enterCountry("India");
		ConfirmationPage confirmationPage = payment.clickOnSubmitOrder();//place order
		Assert.assertTrue(confirmationPage.getConfirmationHeaderMessage().equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistory() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("blackjack@gmail.com", "Test!234");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(itemName));
	}

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\automation\\data\\purchase.json");

		return new Object[][] {{data.get(0)}, {data.get(1)}};

	}

}
