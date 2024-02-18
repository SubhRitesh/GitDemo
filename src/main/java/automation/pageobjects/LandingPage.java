package automation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.reusablecomponents.MyUtils;

public class LandingPage extends MyUtils{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {	//created a constructor to get the driver from Test class
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
//	WebElement userEmail = driver.findElement(By.id("userEmail"));
	
	//using PageFactory (another way to find web elements)
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='toast-error']")
	WebElement errorMessage;
//	By toastErrorMessage = By.cssSelector("div.toast-error");
	
	//action methods
	public ProductCatalogue loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
	public String getToastErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
}
