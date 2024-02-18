package automation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import automation.reusablecomponents.MyUtils;

public class CartPage extends MyUtils{
	WebDriver driver;
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="div.cartSection h3")
	List<WebElement> cartProducts;

	@FindBy(css="li.totalRow button")
	WebElement checkout;
	
	//Validating correct product is added in the cart
	public boolean isProductAvailableInCart(String productName) {
		Boolean flag = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		return flag;
	}
	

	public PaymentPage clickOnCheckout() {
		checkout.click();
		PaymentPage payment =new PaymentPage(driver);
		return payment;
	}
	
}
