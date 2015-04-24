package driver;

import fileReaders.JsonRead;
import fileReaders.PropertiesReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import objects.MVPD;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.remote.MobileCapabilityType;

public abstract class AndroidLaunch {
	
	
	public static void androidAppthwackServer() throws Throwable 
	{
		//JsonRead.readJsonFromFile("/Users/deang/Documents/workspace/AppThwackPilotProject/resources/read.json");
		// Properties properties = PropertiesReader.getPropertiesFile("Galaxy III");
		// Each phone can have its own property file for the capabilities set up
		Properties properties = PropertiesReader.getPropertiesFile("capabilities");
		String dn = properties.getProperty("deviceName");
		String pv = properties.getProperty("platformVersion");
		
		/*
		 * To get appId attribute you need to upload the .apk or .ipa onto appthwack using curl command
		 * curl -X POST -u "APPTHWACK ACCOUNT API KEY:" http://appthwack.com/api/file/ -F name=fileName.ipa -F save=true 
		 * -F file=@/Users/user name/Desktop/fileName.ipa
		 */
		String appId = properties.getProperty("app");
		String key = properties.getProperty("apiKey");
		String pid = properties.getProperty("project");
		
		setJsonRead();
		System.out.println(appId + " " + key + " " + pid + " " + dn + " " + pv);
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", dn); // name of the device as listed on the appthwack api
		capabilities.setCapability("platformVersion", pv); // os of the device
		capabilities.setCapability("automationName","appium"); 
		capabilities.setCapability("app", appId); // change this for every apk or ipa that you upload to appthwack
		capabilities.setCapability("apiKey", key); // this is a unique key tied to a specific user
		capabilities.setCapability("project", pid); // project key is shown in the project setting in your account
		driver = new AndroidDriver(new URL("https://appthwack.com/wd/hub"), capabilities);
		// AppthWack 
		// capabilities.setCapability("deviceName", "Apple iPhone 5"); // name of the device as listed on the appthwack api
		// capabilities.setCapability("platformVersion", "7.0"); // os of the device
		// capabilities.setCapability("automationName", "appium"); 
		// capabilities.setCapability("app", "206621"); // change this for every apk or ipa that you upload to appthwack
		// capabilities.setCapability("apiKey", "dbd3ee8964e351efc4017617921094461944a118"); // this is a unique key tied to a specific user
		// capabilities.setCapability("project", "40241"); // project key is shown in the project setting in your account
		
	}
	@BeforeClass
	public static void androidAppiumServer() throws Throwable 
	{
		File calsspathRoot = new File(System.getProperty("user.dir")); 
		File appDir = new File(calsspathRoot, "Application");
		File app = new File(appDir, "FSGOAndroid.apk");
		/*String xmlPath = System.getProperty("user.dir") + "resources" + "read.json";
		JsonRead.readJsonFromFile(xmlPath);*/
		
		/*
		 * Was having issues here with the Samsung Galaxy III because the software is below
		 * level 17. Appium log says to use Selendroid however the Selendroid driver does not 
		 * have built in methods like tap.
		 */
		Properties properties = PropertiesReader.getPropertiesFile("capabilities");
		String pn = properties.getProperty("platformName");
		String pv = properties.getProperty("platformVersion");
		String dv = properties.getProperty("deviceName");
		//String ap = properties.getProperty("appPackage");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, pn);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, pv);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, dv);
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());//file id
		//drive = new SelendroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		System.out.println("After");
		setJsonRead();
		Thread.sleep(50000);
	}

	
	public static void tearDown() throws Exception 
	{ 	
		driver.quit();
	}
	
	public WebElement waitForElementResourceId(String rId,int waitTime) {
		
		wait = new WebDriverWait(driver, waitTime);
		WebElement element = wait
				.until(ExpectedConditions.elementToBeClickable(By.id(rId)));
		return element;
	}
	
	public WebElement waitForElementName(String name,int waitTime) {
		wait = new WebDriverWait(driver, waitTime);
		WebElement element = wait
				.until(ExpectedConditions.elementToBeClickable(By
						.name(name)));
		return element;
	}
	
	public WebElement waitForElementXpath(String xpath,int waitTime) {
		System.out.println("xpath");
		wait = new WebDriverWait(driver, waitTime);
		WebElement element = wait
				.until(ExpectedConditions.elementToBeClickable(By
						.name(xpath)));
		return element;
	}
	
	public static void tap(WebElement element) {
		driver.tap(1, element, 1);
	}

	public static void click(WebElement element) {
		element.click();
	}
	
	public void takescreenshots() throws InterruptedException {
		
		driver = (AndroidDriver) new Augmenter().augment(driver);
		Thread.sleep(9000);
		// Get the screenshot
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		System.out.println("Screenshot completed");
		try{
			File calsspathRoot = new File(System.getProperty("user.dir")); 
			//workspace space is set in application folder
			File testScreenShot = new File(calsspathRoot + "screenShots", "preRollAds");
			// Copy the file to screenshot folder
			FileUtils.copyFile(scrFile, testScreenShot);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setJsonRead(){
		jsonReader = new JsonRead(filePath);
		jsonReader.readJsonFromFile();
		
	}
	public static AppiumDriver driver;
	public static WebDriverWait wait;
	public static ArrayList<MVPD>mvpd = new ArrayList<MVPD>();
	private static JsonRead jsonReader ;
	private static String filePath = "/Users/ramyar/Documents/workspace/New/AndroidMVPD/resources/read.json";
}
