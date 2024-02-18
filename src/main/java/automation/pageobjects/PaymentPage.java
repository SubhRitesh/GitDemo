package automation.pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.reusablecomponents.MyUtils;

public class PaymentPage extends MyUtils{
	WebDriver driver;
	
	public PaymentPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//div[contains(text(),'Name on Card ')]/following-sibling::input")
	private WebElement cardHolderName;
	
	@FindBy(xpath="//div[contains(text(),'CVV Code ')]/following-sibling::input")
	private WebElement cvv;
	
	@FindBy(css="div.payment__shipping input[placeholder='Select Country']")
	private WebElement country;
	
	@FindBy(css="section.ta-results span")
	private List<WebElement> searchOptions;
	
	@FindBy(css="div.payment__shipping a")
	private WebElement submitOrder;
	
	//personal details
	public void enterCardHolderName(String name) {
		cardHolderName.sendKeys(name);
	}
	
	public void enterCVV(String pin) {
		cvv.sendKeys(pin);
	}
	
	//payment details
	public void enterCountry(String countryName) {
		country.sendKeys(countryName);
		searchOptions.stream().filter(option->option.getText().equalsIgnoreCase(countryName)).forEach(option->option.click());
	}

	public ConfirmationPage clickOnSubmitOrder() {
		waitForElementToBeClickable(submitOrder);
		submitOrder.click();
//		ConfirmationPage confirmationPage =new ConfirmationPage(driver);
		return new ConfirmationPage(driver);
	}

}
