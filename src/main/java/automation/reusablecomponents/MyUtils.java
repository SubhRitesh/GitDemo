package automation.reusablecomponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automation.pageobjects.CartPage;
import automation.pageobjects.OrderPage;

public class MyUtils {
	WebDriver driver;
	public MyUtils(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(css="button[routerlink*='cart']")
	WebElement cart;
	
	@FindBy(css="button[routerlink*='myorders']")
	WebElement myOrder;

	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	public void waitForWebElementToAppear(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	public void waitForWebElementToAppear(List<WebElement> ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElements(ele));
	}
	public void waitForElementToDisappear(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	public void waitForElementToBeClickable(WebElement ele) {
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	public CartPage clickOnCart() {
		waitForElementToBeClickable(cart);
		cart.click();
		CartPage shoppingCart =new CartPage(driver);
		return shoppingCart;
	}
	public OrderPage goToOrderPage() {
		myOrder.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
}
