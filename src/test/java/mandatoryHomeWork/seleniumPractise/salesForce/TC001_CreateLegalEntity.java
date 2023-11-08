package mandatoryHomeWork.seleniumPractise.salesForce;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class TC001_CreateLegalEntity {
	

	WebDriver driver;
	String expectedLegalEntityName="Salesforce Automation by ";
	
	
	
	@Test
	public void findDriverName() throws MalformedURLException {
		
		ChromeOptions browserOptions = new ChromeOptions();	
		browserOptions.addArguments("--disable-notifications");	
		browserOptions.setPlatformName("Windows 10");
		browserOptions.setBrowserVersion("120.0");
		
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username", "mgovindarajm");
		ltOptions.put("accessKey", "yeV6p04AELr6QQSovXpJNArKXHKv7qWZzrGXoNWcAmOuDoi6hQ");
		ltOptions.put("visual", true);
		ltOptions.put("video", true);
		ltOptions.put("build", "SeleniumSaleForceDemo");
		ltOptions.put("project", "EntityCreationInSalesForce");
		ltOptions.put("name", "EntityCreation");
		ltOptions.put("selenium_version", "4.0.0");
		ltOptions.put("w3c", true);
		browserOptions.setCapability("LT:Options", ltOptions);

		driver=new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), browserOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.id("username")).sendKeys("mgovindarajm@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Qwerty@5016");
		driver.findElement(By.id("Login")).click();
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		
		WebElement element=driver.findElement(By.xpath("//p[text()='Legal Entities']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);

		driver.findElement(By.xpath("//span[text()='Legal Entities']//following::one-app-nav-bar-item-dropdown//div/one-app-nav-bar-menu-button/a")).click();
		WebElement newLegalEntityElement=driver.findElement(By.xpath("//span[text()='New Legal Entity']"));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", newLegalEntityElement);
		
		Faker faker = new Faker();
		String name=faker.name().username();
		String entityName=expectedLegalEntityName.concat(name);

		driver.findElement(By.xpath("//label[text()='Name']//following::input[1]")).sendKeys(entityName);
		driver.findElement(By.xpath("//button[text()='Save']")).click();
		String legalEntityName=driver.findElement(By.xpath("//div[text()='Legal Entity']//following::slot[1]/lightning-formatted-text")).getText();
		Assert.assertEquals(legalEntityName, entityName);
		driver.quit();
	}

}