package automation.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import automation.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver() throws IOException {
		Properties prop =new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\automation\\resources\\Config.properties");
		prop.load(file);
//		String browserName = prop.getProperty("browser");
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser"): prop.getProperty("browser"); //java terniary operator is used to write the if else condition
		//System.getProperty("browser") will read the value from system variable like maven command to get the browserName
		if(browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless")) {
				options.addArguments("headless");// to run test in headless mode
			}
//			WebDriverManager.chromedriver().setup();  //This will automatically download the chromedriver in the project
			driver =new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900)); // OPTIONAL: to run in fullscreen mode especially for headless mode
		}else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver =new FirefoxDriver();
		}else if(browserName.equalsIgnoreCase("edge")) {
//			WebDriverManager.edgedriver().setup();
			System.setProperty("webdriver.edge.driver", "//edgedriver.exe");
			driver = new EdgeDriver();		
		}
		driver.manage().window().maximize(); // this might not work for headless mode
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		//convert String to Hashmap  using jackson databind dependency
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
		});
		return data; //this will return a List of HashMaps
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir")+ "//reports//" + testCaseName + ".png";
		File file = new File(filePath);
		FileUtils.copyFile(source, file);
		return filePath;
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}
}
