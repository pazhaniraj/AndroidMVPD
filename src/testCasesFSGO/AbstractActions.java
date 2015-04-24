package testCasesFSGO;

import java.util.List;
import objects.Accounts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import driver.AndroidLaunch;

public class AbstractActions extends AndroidLaunch {
	
	private String SETTINGS_BUTTON  = "com.foxsports.videogo:id/settingsButton";
	private String SIGN_IN_FROM_SETTINGS_MENU = "com.foxsports.videogo:id/settings_sign_out";
	private String CLICK_THE_MORE_PROVIDERS_OPTION ="com.foxsports.videogo:id/more_provider";
	private String ON_NOW ="com.foxsports.videogo:id/liveNowBanner";
	private String OK ="android:id/button1";
	private String USERNAME = "Userid";
	private String PASSWORD ="Password";
	private String SIGN_IN_BUTTON ="Sign In";


	/*private void pressButton(String name) {
		while (!(ele_.isDisplayed())) {
			driver.tap(1, 290, 210, 1);
			tap(waitForElementName(name,15));	
			break;
		}
	}*/
	
	public void loginAccountSettings (String accountName) {
		
		/*
		 * Resource ID's still need to be found for all the elements on Android
		 */
		
		// setting button
		tap(waitForElementResourceId(SETTINGS_BUTTON,60));
		
		// sign in
		tap (waitForElementResourceId(SIGN_IN_FROM_SETTINGS_MENU,60));
		
		//sleep is to wait for the screen to load before swiping still need to 
		//implement a better way of waiting for this besides a sleep
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Still working on trying to make these x and y attributes dynamic ideas:
		 * 1. Have these values hard coded into the same property files as the capabilities 
		 *    file then read in from there
		 * 2. At the start of the program find the element which is at the lowest level of the 
		 *    XML tree (The background image of the app) and get the dimensions for that element
		 */
		
		// The swipe here is not necessary for larger phones so how can this action be made dynamic?
		//driver.swipe(225,500,225,250,3000);
		driver.swipe(290, 420, 290, 234, 3000);
		
		//more providers
		tap(waitForElementResourceId(CLICK_THE_MORE_PROVIDERS_OPTION,60));
	
		// Scroll to works when invisible elements are present in the DOM
		// I noticed that elements with y coordinates are considered visible 
		// When using this scroll to method. This was for the IPhone not sure 
		// If it will work the same on Android if it does not then the same swiping
		// technique used for the videos should be used 
	
		driver.scrollTo(accountName);
		tap(waitForElementName(accountName,60));
	}
	
	public boolean login(Accounts account) {
		
		/*
		 * Resource ID's still need to be found for all the elements on Android
		 */
		
		//username
		WebElement element = waitForElementXpath(USERNAME,60);
		click(element);
		element.sendKeys(account.getUsername());
		
		//password
		element = waitForElementXpath(PASSWORD,60);
		click(element);
		element.sendKeys(account.getPassword());
		
		//done
		//tap(waitForElementResourceId("CLICK DONE ON KEYBOARD",60));
		driver.navigate().back();
		//click Sign-In	
		tap(waitForElementName(SIGN_IN_BUTTON,60));
		
		try{
			  Thread.sleep(1000);
			  //driver.swipe(225,500,225,250,3000);
			  driver.swipe(290, 420, 290, 234, 3000);
			  waitForElementResourceId("CHECK TO SEE IF THE SIGN IN BUTTON IS STILL AVAILABLE",15).isEnabled();
		} catch (Exception e) {
			return true;
		}
		
		return false;	
	}
	
	/*
    Same code as above but different approach in vid is the length of a list that contains elements that have 
    The value ONNOW in them this means the amount of playable videos and the code manually scrolls through that 
    Many times since each video container is 186 pixels from top to bottom swipe down that far then click in the middle 
    Of that container to play the video. Was considering this approach for Android because unlike IOS resourceIds for 
    videos were not being displayed in the DOM before scrolling. So the idea is to scroll and play videos up until a element 
    appears that has a name share which is one of the options that pops up when a video is not on now. If that element is not 
    found throw the elementNotFound exception.
    
    problems occur with random ads that are placed in between videos like banners need to find a way to detect these ads
    
    public static void playVideo(int vid) throws NoSuchElementException, TimeoutException {
	
	for(int i = 0; i <= vid; i++)
	{
		// If we are on the first video we do not want to swipe at all
		if(i != 0)
			driver.swipe(290, 420, 290, 234, 3000);
	}
	driver.tap(1, 195, 290, 1);
	// Swipe to get more providers
}
*/
	
	public void playVideos() throws InterruptedException {
		/* Check to see if the resourceId layout is the same as xPath in structure if it is
		 * then use same logic and change xPaths to resourceIds otherwise change
		 */
		Thread.sleep(10000);
		while (driver.findElementById(ON_NOW).isEnabled()) {
			tap(driver.findElementById(ON_NOW));
			driver.swipe(290, 420, 290, 234, 1000);
		}
		
	}
	
	public void logout(){
		tap(waitForElementResourceId(SETTINGS_BUTTON,60));
		tap(waitForElementResourceId(SIGN_IN_FROM_SETTINGS_MENU,60));
		tap(waitForElementResourceId(OK,60));
	}
	
	
	
	
	// When video is playing and the control options are available then this element should be seen
	//private WebElement ele_ = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAStaticText[1]"));
}
