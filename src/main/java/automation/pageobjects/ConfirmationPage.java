package automation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.reusablecomponents.MyUtils;

public class ConfirmationPage extends MyUtils{
	private WebDriver driver;
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h1")
	private WebElement confirmationHeader;
	
	public String getConfirmationHeaderMessage() {
		return confirmationHeader.getText();
	}
	

//	List<WebElement> orders = driver.findElements(By.cssSelector("tr.ng-star-inserted label"));
//	orders.stream().map(order -> order.getText()).forEach(order -> System.out.println(order));

}
