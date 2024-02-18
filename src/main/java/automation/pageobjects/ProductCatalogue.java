package automation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.reusablecomponents.MyUtils;

public class ProductCatalogue extends MyUtils{	
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {	//created a constructor to get the driver from Test class
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//using PageFactory (another way to find web elements)
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	
	//Locators
	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type ");
	By toastMessage = By.cssSelector("#toast-container");
	By animation = By.cssSelector(".ng-animating");

	//action methods
	public  List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream().filter(product->product.findElement(By.cssSelector("b"))
				.getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName) {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);  //page loading
	}
	
}
