package automation.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import automation.TestComponents.BaseTest;
import automation.pageobjects.CartPage;
import automation.pageobjects.ConfirmationPage;
import automation.pageobjects.LandingPage;
import automation.pageobjects.PaymentPage;
import automation.pageobjects.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImplementation extends BaseTest{
//Git branch changes
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$") //written with regular expression for parameters
	public void logged_in_username_and_password(String username, String password) {
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to the Cart$")
	public void I_add_product_to_the_cart(String productName) {
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_order(String productName) {
		CartPage shoppingCart = productCatalogue.clickOnCart();//Product Added To Cart
		//Validating correct product is added in the cart
		Assert.assertTrue(shoppingCart.isProductAvailableInCart(productName), "Expected product not available in the cart");
		PaymentPage payment = shoppingCart.clickOnCheckout();
		//payment details
		payment.enterCardHolderName("Jack");
		payment.enterCVV("123");
		payment.enterCountry("India");
		confirmationPage = payment.clickOnSubmitOrder();//place order
	}
	
	@Then("{string} message is displayed on Confirmation Page")
	public void message_displayed_ConfirmationPage(String string) {
		Assert.assertTrue(confirmationPage.getConfirmationHeaderMessage().equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("{string} message is displayed")
	public void message_displayed_LoginPage(String string) {
		Assert.assertEquals(landingPage.getToastErrorMessage(), string);
		driver.close();
	}
	
}
