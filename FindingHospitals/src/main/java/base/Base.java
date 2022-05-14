package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

import utils.ExtentReportManager;

public class Base {
	public static WebDriver driver;
	public static RemoteWebDriver driver1;
	public static Properties prop;
	public static WebDriverWait wait;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	// To call different browsers using Webdriver
	public void invokeBrowser() {
		prop = new Properties();

		try {
			prop.load(new FileInputStream("src/main/java/Config/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// To Open Chrome Browser
		if (prop.getProperty("browserName").matches("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		// To Open Mozilla Browser
		if (prop.getProperty("browserName").matches("mozilla")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		// To Open Edge Browser
		if (prop.getProperty("browserName").matches("edge")) {
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\Drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		// To maximize the Browser Window
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

	}

	// To call different browsers using Selenium Grid
	
//	public void invokeBrowser() throws MalformedURLException {
//		prop = new Properties();
//		String nodeURL = "http://192.168.56.1:4444/wd/hub";
//		
//		try {
//			prop.load(new FileInputStream("src/main/java/Config/config.properties"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// To Open Chrome Browser
//		if (prop.getProperty("browserName").matches("chrome")) {
//			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//			capabilities.setBrowserName("chrome");
//			capabilities.setPlatform(Platform.WIN10);
//			driver1 = new RemoteWebDriver(new URL(nodeURL), capabilities);
//
//		}
//		// To Open Mozilla Browser
//		if (prop.getProperty("browserName").matches("mozilla")) {
//			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//			capabilities.setBrowserName("firefox");
//			capabilities.setPlatform(Platform.WIN10);
//			driver1 = new RemoteWebDriver(new URL(nodeURL), capabilities);
//
//		}
//		// To Open Edge Browser
//		if (prop.getProperty("browserName").matches("edge")) {
//			DesiredCapabilities capabilities = DesiredCapabilities.edge();
//			capabilities.setBrowserName("edge");
//			capabilities.setPlatform(Platform.WIN10);
//			driver1 = new RemoteWebDriver(new URL(nodeURL), capabilities);
//		}
//		
//		// To maximize the Browser Window
//		driver.manage().window().maximize();
//		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//	}

	// To open the Main Page URL
	public void openURL(String websiteURLKey) {
		driver.get(prop.getProperty(websiteURLKey));
	}

	// Finding WebElement using By method
	public WebElement findElement(By by) throws Exception {
		WebElement element = driver.findElement(by);
		return element;
	}

	// To submit Data in Corporate Wellness Page
	public void submitData() throws InterruptedException {

		By button = By.xpath("//*[@id='app']/div/div/header[2]/div[2]/div/form/button");

		WebElement submit = driver.findElement(button);
		submit.click();
		wait = new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	// To show failed test cases in the report
	public void reportFail(String report) {
		logger.log(Status.FAIL, report);
		takeScreenShotOnFailure();
	}

	// To show passed test cases in the report
	public void reportPass(String report) {
		logger.log(Status.PASS, report);
	}

	// To take Screenshots
	public void Screenshot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		Long dateTime = System.currentTimeMillis();
		File destFile = new File(System.getProperty("user.dir") + "/Screenshot/" + dateTime + "_" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	// To take Screenshot of failed test
	public void takeScreenShotOnFailure() {

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File src = takeScreenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/Screenshot/FailedCases/Screenshot.png");
		try {
			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshot/FailedCases/Screenshot.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// To input all data to the report
	public void endReport() {
		report.flush();
	}

	// To close the Browser
	public void closeBrowser() {
		driver.quit();

	}

}
