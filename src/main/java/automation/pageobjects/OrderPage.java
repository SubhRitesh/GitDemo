package automation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import automation.reusablecomponents.MyUtils;

public class OrderPage extends MyUtils{
	WebDriver driver;
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tbody tr td:nth-child(3)")
	List<WebElement> orderNames;

	@FindBy(css="li.totalRow button")
	WebElement checkout;
	
	//Validating correct product is added in the cart
	public boolean verifyOrderDisplay(String productName) {
		Boolean flag = orderNames.stream().anyMatch(orderName->orderName.getText().equalsIgnoreCase(productName));
		return flag;
	}
	

	public PaymentPage clickOnCheckout() {
		checkout.click();
		PaymentPage payment =new PaymentPage(driver);
		return payment;
	}
	
}
