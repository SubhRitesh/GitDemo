package automation.tests;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import automation.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();  //This will automatically download the chromedriver in the project
		WebDriver driver =new ChromeDriver();
		
		LandingPage landingPage = new LandingPage(driver);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client/");
		
		driver.findElement(By.id("userEmail")).sendKeys("blackjack@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Test!234");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		String itemName = "I Phone2";
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b"))
				.getText().equalsIgnoreCase(itemName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type ")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
//		ystem.out.println(driver.findElement(By.cssSelector("#toast-container")).getText());
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //page loading
		
		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		//Product Added To Cart
		
		//Validating correct product is added in the cart
		List<WebElement> cartProducts = driver.findElements(By.cssSelector("div.cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(itemName));
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector("li.totalRow button")).click();
		
		//personal details
		driver.findElement(By.xpath("//div[contains(text(),'Name on Card ')]/following-sibling::input")).sendKeys("Jack");
		driver.findElement(By.xpath("//div[contains(text(),'CVV Code ')]/following-sibling::input")).sendKeys("123");
		
		
		//payment details
		String country = "India";
		driver.findElement(By.cssSelector("div.payment__shipping input[placeholder='Select Country']")).sendKeys(country);
		List<WebElement> searchOptions = driver.findElements(By.cssSelector("section.ta-results span"));
		searchOptions.stream().filter(searchOption->searchOption.getText().equalsIgnoreCase(country)).forEach(searchOption->searchOption.click());
		//place order
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("div.payment__shipping a"))));
		driver.findElement(By.cssSelector("div.payment__shipping a")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "THANKYOU FOR THE ORDER.");
		
		List<WebElement> orders = driver.findElements(By.cssSelector("tr.ng-star-inserted label"));
		orders.stream().map(order -> order.getText()).forEach(order -> System.out.println(order));
		
		
		
	}

}
