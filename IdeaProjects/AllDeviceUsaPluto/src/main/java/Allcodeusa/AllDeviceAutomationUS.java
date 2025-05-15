package Allcodeusa;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import static Allcodeusa.externalDeviceVideo.StopCamera;
import static Allcodeusa.externalDeviceVideo.launchCamera;

@Listeners({AllureTestNg.class})
public class AllDeviceAutomationUS extends appiumService{
	
	public static WebDriver driver;
	public static WebDriverWait wait,FireTVWait;
    public Workbook workbook;
    public Sheet sheet;
    public Actions action;
    public int rowIndex=0;
    public File resultFile=new File("test_results.xlsx");
    public static ExtentReports extent;
    public static ExtentTest test;
    public static UiAutomator2Options options;
    public static UiAutomator2Options options1;
    public static AndroidDriver mobileDriver,AndroidTVDriver;
    public static AppiumDriverLocalService appiumServiceBuilder;
	public static String rokuIp;
	public static AppiumDriver rokuDriver;
	public static String appId;
	
	public static Process process;
	
    
    @BeforeClass
	public void setup() throws IOException, AWTException, URISyntaxException{
		startRecording();
		ExcelSheetCreation();
		options=new UiAutomator2Options();
		options.setAppPackage("tv.pluto.android");
		options.setAppActivity("tv.pluto.android.EntryPoint");
	}
	
    @Step("Test cases execution")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority=2,retryAnalyzer=RetryAnalyzer.class)
	public void USRegionWebAutomation() throws IOException, URISyntaxException, InterruptedException {
		appLaunchTest();
		liveCategorySwitchTest();
		LiveChannelChange();
		VodCategoryChange();
		VODPlayback();
		Search_For_Web();
		signInTest();
		}
	
    @Step("Test cases execution")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority=1,retryAnalyzer=RetryAnalyzer.class)
	public void USRegionMobileAutomation() throws URISyntaxException, IOException, InterruptedException {
		Mobile_App_Launch();
		Mobile_Catagory_Change_In_Live_Tv();
		Mobile_Live_To_Movie_Play_Back();
		Mobile_On_Demand_Category_Change();
		Mobile_On_Demand_Movie_Channel_Change();
		Mobile_Search();
		Mobile_Sign_In();
	}
	
    @Step("Test cases execution")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority=5,retryAnalyzer=RetryAnalyzer.class)
	public void USRegionFireTVApplication() throws URISyntaxException, InterruptedException, IOException {
		FireTv_ApplicationLaunch();
		FireTV_Category_Change_In_LiveTv();
		FireTV_Movie_Play_In_LiveTv();
		FireTV_On_Demand_Category_Change();
		FireTV_On_Demand_Movie_Play();
		FireTV_Search();
		FireTV_SignIn();
	}
	
    @Step("Test cases execution")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority=3,retryAnalyzer=RetryAnalyzer.class)
	public void USRokuDevice() throws URISyntaxException, IOException, InterruptedException {
		Roku_App_Launch_US();
        category_Change_Roku_US();
        channel_Change_Roku_US();
        category_Change_On_Demand_US();
        channel_Change_On_Demand_US();
        search_Roku_US();
        signIn_US_Roku();
	}
	
    @Step("Test cases execution")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority=4,retryAnalyzer=RetryAnalyzer.class)
	public void USAndroidTVDevice() throws URISyntaxException, IOException, InterruptedException {
		AndroidTVAppLaunch();
		AndroidTV_Category_Change_In_LiveTv();
		AndroidTV_Movie_Play_In_LiveTv();
		AndroidTV_Category_Change_On_Demand();
		AndroidTV_OnDemand_Movie_Change();
		AndroidTV_Search();
		AndroidTV_Sign_In();
	}
	
	@Step("App Launch Test")
    @Severity(SeverityLevel.CRITICAL)
	public void appLaunchTest() throws IOException, InterruptedException, URISyntaxException {
		startAppiumServiceUsing4723();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		wait=new WebDriverWait(driver,Duration.ofSeconds(30));
//		screenRecorder.start();
		System.out.println("Recording started...");
		launchCamera();
		long startTime = System.currentTimeMillis();
		driver.get("https://pluto.tv/");
		StopCamera();
		long endTime = System.currentTimeMillis();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ChannelInfo-Equalizer']")));
		long duration = endTime - startTime;
//	    screenRecorder.stop();
	    System.out.println("App launch time: " + duration + " milliseconds");
	    extent = new ExtentReports();
	    test = extent.createTest("Test Name");
	    test.pass("App launched successfully.");
        test.info("App launch time: " + duration + " milliseconds");
//	    File videoFile = screenRecorder.getCreatedMovieFiles().get(0);
	    writeToExcel("Web - Application Launch", duration, "Passed", filePath);
	}
	
	@Step("Live Category Switch Test")
    @Severity(SeverityLevel.CRITICAL)
	public void liveCategorySwitchTest() throws IOException, InterruptedException, URISyntaxException {
//		screenRecorder.start();
		popupHandling();
		driver.findElement(By.xpath("//span[text()='Movies']")).click();
		Actions actions = new Actions(driver);
		WebElement newCategory=driver.findElement(By.xpath("//span[text()='Classic TV']"));
		actions.sendKeys(newCategory,Keys.DOWN).build().perform();
		popupHandling();
		launchCamera();
		long liveCategorySwitchInTime = System.currentTimeMillis();
		driver.findElement(By.xpath("//span[text()='Comedy']")).click();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='featured desktop']")));
		StopCamera();
		long liveCategorySwitchOutTime = System.currentTimeMillis();
		long duration = liveCategorySwitchOutTime - liveCategorySwitchInTime;
//      screenRecorder.stop();
        System.out.println("Live Category Switch Duration: " + duration + " milliseconds");
        test = extent.createTest("Test Name");
	    test.pass("Live category changed successfully.");
        test.info("Live category changed time: " + duration + " milliseconds");
//	    File videoFile = screenRecorder.getCreatedMovieFiles().get(0);
	    writeToExcel("Web - Category change in Live TV", duration, "Passed",filePath);
	}
	
	 @Step("Live Channel Change")
	 @Severity(SeverityLevel.CRITICAL)
	 public void LiveChannelChange() throws InterruptedException, IOException, URISyntaxException {
//		 screenRecorder.start();
		 action=new Actions(driver);
		 popupHandling();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@aria-label='Sitcom Legends']")));
		 WebElement channelImage=driver.findElement(By.xpath("//div[@aria-label='Sitcom Legends']"));
		 action.moveToElement(channelImage).perform();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@aria-label='Sitcom Legends']")));
		 popupHandling2();
		 driver.findElement(By.xpath("//div[@aria-label='Sitcom Legends']")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Watch Live Channel']")));
		 popupHandling2();
		 launchCamera();
		 long liveChannelChangeInTime = System.currentTimeMillis();
		 driver.findElement(By.xpath("//span[text()='Watch Live Channel']")).click();
		 driver.findElement(By.xpath("//div[contains(@class,'eventLayerOverlay')]")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'video-player-time-start-mark-atc')]")));
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(@class,'video-player-time-start-mark-atc')]")));
		 long liveChannelChangeOutTime = System.currentTimeMillis();
		 StopCamera();
//		 screenRecorder.stop();
		 popupHandling();
		 long duration = liveChannelChangeOutTime - liveChannelChangeInTime;
	     System.out.println("Live Channel Change Duration: " + duration + " milliseconds");
	     test.pass("Live Channel Change completed successfully.");
	     test.info("Live Channel Change: " + duration + " milliseconds");
	     writeToExcel("Web - Channel change in Live TV", duration, "Passed",filePath);
	 }

	@Step("VodCategoryChange")
	 @Severity(SeverityLevel.CRITICAL)
	 public void VodCategoryChange() throws IOException, InterruptedException, URISyntaxException {
//		 screenRecorder.start();
		 popupHandling();
		 driver.findElement(By.linkText("On Demand")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Featured']")));
		 WebElement moviesTab=driver.findElement(By.xpath("//span[text()='Comedy']"));
		 action=new Actions(driver);
		 action.scrollToElement(moviesTab).build().perform();
		 launchCamera();
		 long vodCategorySwitchInTime = System.currentTimeMillis();
		 driver.findElement(By.xpath("//span[text()='Streaming Tech']")).click();
		 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@type='button']")));
		 long vodCategorySwitchOutTime = System.currentTimeMillis();
		 StopCamera();
	     long duration = vodCategorySwitchOutTime - vodCategorySwitchInTime;
//	     screenRecorder.stop();
	     System.out.println("VOD Category Switch Duration: " + duration + " milliseconds");
	     test = extent.createTest("Test Name");
	     test.pass("VOD Category Switch launched successfully.");
	     test.info("VOD Category Switch: " + duration + " milliseconds");
//	     File videoFile = screenRecorder.getCreatedMovieFiles().get(0);
	     writeToExcel("Web - On Demand Category Switch", duration, "Passed",filePath);
		 
	 }
	
	 @Step("VODPlayback")
	 @Severity(SeverityLevel.CRITICAL)
	 public void VODPlayback() throws IOException, InterruptedException, URISyntaxException {
		 driver.findElement(By.xpath("//span[text()='Details']")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Watch Now']")));
//		 screenRecorder.start();
		 launchCamera();
		 driver.findElement(By.xpath("//span[text()='Watch Now']")).click();
		 long VODPlaybackInTime = System.currentTimeMillis();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'filledRail')]")));
		 long VODPlaybackOutTime = System.currentTimeMillis();
		 StopCamera();
	     long duration = VODPlaybackOutTime - VODPlaybackInTime;
//	     screenRecorder.stop();
	     System.out.println("VODPlayback Duration: " + duration + " milliseconds");
	     test.pass("VODPlayback launched successfully.");
	     test.info("VODPlayback: " + duration + " milliseconds");
//	     File videoFile = screenRecorder.getCreatedMovieFiles().get(0);
	     writeToExcel("Web - On Demand Movie Playback", duration, "Passed",filePath);
	 }
	 
	 @Step("Search")
	 @Severity(SeverityLevel.CRITICAL)
	 public void Search_For_Web() throws IOException, InterruptedException, URISyntaxException {
		 action=new Actions(driver);
		 Thread.sleep(2000);
		 action.sendKeys(Keys.ESCAPE).perform();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Search")));
		 driver.findElement(By.linkText("Search")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='search']")));
		 launchCamera();
		 long launchInTime = System.currentTimeMillis();
//		 screenRecorder.start();
		 driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Gunsmoke");
		 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'searchResultItemImage')]")));
		 StopCamera();
		 long launchOutTime = System.currentTimeMillis();
		 long duration = launchOutTime - launchInTime;
//	     screenRecorder.stop();
	     System.out.println("Search Duration: " + duration + " milliseconds");
	     test.pass("Search completed successfully.");
	     test.info("Search time: " + duration+ " milliseconds");
//	     File videoFile = screenRecorder.getCreatedMovieFiles().get(0);
	     writeToExcel("Web - Search", duration, "Passed",filePath);
	 }
	 
	 	@Step("Sign In Test")
	    @Severity(SeverityLevel.CRITICAL)
	    public void signInTest() throws InterruptedException, IOException, URISyntaxException {
//	        screenRecorder.start();
	        driver.findElement(By.linkText("Sign In")).click();
	        driver.findElement(By.id("emailAddress")).sendKeys("kkk@k.tv");
	        driver.findElement(By.xpath("//button[@type='submit']")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Please enter a password']")));
	        driver.findElement(By.xpath("//input[@placeholder='Please enter a password']")).sendKeys("aaaaaa");
	        launchCamera();
	        driver.findElement(By.xpath("//button[@type='submit']")).click();
	        long signInInTime = System.currentTimeMillis();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@aria-label,'Account, signed in')]")));
	        long signInOutTime = System.currentTimeMillis();
	        StopCamera();
	        long duration = signInOutTime - signInInTime;
//	        screenRecorder.stop();
	        System.out.println("Sign-in Duration: " + duration + " milliseconds");
	        test.pass("App launched and signed in successfully.");
	        test.info("Sign-in time: " + duration + " milliseconds");
//	        File videoFile = screenRecorder.getCreatedMovieFiles().get(0);
	        writeToExcel("Web - Sign In", duration, "Passed",filePath);
	        test.pass("Sign In completed successfully.");
		    test.info("Sign In time: " + duration+ " milliseconds");
		    driver.quit();
		    stopAppiumServiceUsing4723();
	 }
	 	
	 //WebCode completed
	 
	@Step("app launch")
	@Severity(SeverityLevel.CRITICAL)
	public void Mobile_App_Launch() throws URISyntaxException, IOException, InterruptedException {
		startAppiumServiceUsing4723();
		options.setUdid("RZCW308LA0W");
		mobileDriver=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		launchCamera();
		wait=new WebDriverWait(mobileDriver, Duration.ofSeconds(30));
//		mobileDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		long startTimeOnceAppClicked=System.currentTimeMillis();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[2]/android.view.View[2]")));
		long endTimeOnceNotnowPageLoaded=System.currentTimeMillis();
		StopCamera();
		mobileDriver.findElement(AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[2]/android.view.View[2]")).click();
		handlingPermission();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"tv.pluto.android:id/lib_player_ui_metadata_title_expanded\"]")));
//		androidVideoStoping(mobileDriver);
		long durationForAppLaunch=endTimeOnceNotnowPageLoaded-startTimeOnceAppClicked;
		writeToExcel("Android Mobile - Application Launch", durationForAppLaunch, "Passed", filePath);
		System.out.println("TimeTaken for app launch "+durationForAppLaunch+" milliseconds");
		extent = new ExtentReports();
		test = extent.createTest("Test Name");
	    test.pass("Time taken for app launched successfully.");
        test.info("Android Mobile - App Launch " + durationForAppLaunch + " milliseconds");
	}
	
	@Step("Live Category Switch Test")
    @Severity(SeverityLevel.CRITICAL)
	public void Mobile_Catagory_Change_In_Live_Tv() throws URISyntaxException, IOException, InterruptedException {
		launchCamera();
//		mobileDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		long startedTimeBeforeClickingAnotherCatogory=System.currentTimeMillis();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Movies\"]")));
		mobileDriver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Movies\"]")).click();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("(//android.view.View[@resource-id=\"tv.pluto.android:id/feature_mobileguide_v2_episode_background_shape\"])")));
		long endTimeAfterCategoryChange=System.currentTimeMillis();
//		androidVideoStoping(mobileDriver);
		StopCamera();
		long durationForCategoryChange=endTimeAfterCategoryChange-startedTimeBeforeClickingAnotherCatogory;
	    writeToExcel("Android Mobile - Category change in Live TV", durationForCategoryChange, "Passed",filePath);
		System.out.println("TimeTaken for catagory change in live tv is "+durationForCategoryChange+" milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("Live Tv category change completed successfully.");
        test.info("Live Tv category change time: " + durationForCategoryChange + " milliseconds");
	}
	
	@Step("Live to movie play back")
	 @Severity(SeverityLevel.CRITICAL)
	public void Mobile_Live_To_Movie_Play_Back() throws URISyntaxException, IOException, InterruptedException {
		launchCamera();
//		mobileDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		mobileDriver.findElement(AppiumBy.xpath("(//android.view.View[@resource-id=\"tv.pluto.android:id/feature_mobileguide_v2_episode_background_shape\"])[5]")).click();
		long startTimeAfterClickingNewMovie=System.currentTimeMillis();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/lib_player_ui_shutter_featured_artwork\"]")));
		long TimeTakenForMoviePlay=System.currentTimeMillis();
//		androidVideoStoping(mobileDriver);
		StopCamera();
		long durationForMoviePlay=TimeTakenForMoviePlay-startTimeAfterClickingNewMovie;
	    writeToExcel("Android Mobile - Channel change in Live TV", durationForMoviePlay, "Passed",filePath);
		System.out.println("Time taken for movie playing is : "+durationForMoviePlay+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("Live TV channel change completed successfully.");
        test.info("Live TV channel change: " + durationForMoviePlay + " milliseconds");
	}
	
	@Step("VodCategoryChange")
	@Severity(SeverityLevel.CRITICAL)
	public void Mobile_On_Demand_Category_Change() throws URISyntaxException, IOException, InterruptedException {
		mobileDriver.findElement(AppiumBy.accessibilityId("On Demand")).click();
		launchCamera();
//		mobileDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		long startedTimeBeforeClickingAnotherCatogoryInOnDemand=System.currentTimeMillis();
		mobileDriver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Action\"]")).click();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/ondemand_card_image\"]")));
		long endTimeAfterCategoryChangeInOndemand=System.currentTimeMillis();
		long durationForCategoryChangeInOnDemand=endTimeAfterCategoryChangeInOndemand-startedTimeBeforeClickingAnotherCatogoryInOnDemand;
//		androidVideoStoping(mobileDriver);
		StopCamera();
	    writeToExcel("Android Mobile - On Demand Category Switch", durationForCategoryChangeInOnDemand, "Passed",filePath);
		System.out.println("TimeTaken for catagory change in OnDemand tv is "+durationForCategoryChangeInOnDemand+" milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("On demand category changed successfully");
        test.info("TimeTaken for catagory change in OnDemand : " + durationForCategoryChangeInOnDemand + " milliseconds");
	}
	
	@Step("VODPlayback")
	@Severity(SeverityLevel.CRITICAL)
	public void Mobile_On_Demand_Movie_Channel_Change() throws URISyntaxException, IOException, InterruptedException {
		PointerInput finger=new PointerInput(Kind.TOUCH,"finger");
		Sequence swipe=new Sequence(finger,1).addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 130, 339)) // Start point
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), 172, 958)) // End point
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        mobileDriver.perform(Arrays.asList(swipe));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/hero_carousel_view\"]/android.view.ViewGroup/android.view.View[1]")));
		mobileDriver.findElement(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/hero_carousel_view\"]/android.view.ViewGroup/android.view.View[1]")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"PlayNowButton\"]")));
		launchCamera();
//		mobileDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		mobileDriver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"PlayNowButton\"]")).click();
		long startTimeAfterClickingNewMovieOnDemand=System.currentTimeMillis();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@resource-id=\"tv.pluto.android:id/lib_player_ui_controls_background_dimming_background\"]")));
		long TimeTakenForMoviePlayOnDemand=System.currentTimeMillis();
		long durationForMoviePlayForOnDemandMovie=TimeTakenForMoviePlayOnDemand-startTimeAfterClickingNewMovieOnDemand;
//		androidVideoStoping(mobileDriver);
		StopCamera();
	    writeToExcel("Android Mobile - On Demand Movie Playback", durationForMoviePlayForOnDemandMovie, "Passed",filePath);
		System.out.println("Time taken for movie playing is : "+durationForMoviePlayForOnDemandMovie+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("On demand channel change completed successfully.");
        test.info("Time taken for movie playing is: " + durationForMoviePlayForOnDemandMovie + " milliseconds");
	}
	
	@Step("Search")
	@Severity(SeverityLevel.CRITICAL)
	public void Mobile_Search() throws URISyntaxException, IOException, InterruptedException {
		 mobileDriver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"tv.pluto.android:id/lib_avia_surfaceview\"]")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("Back")));
		 mobileDriver.findElement(AppiumBy.accessibilityId("Back")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/navigation_bar_item_icon_view\"])[4]")));
		 mobileDriver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/navigation_bar_item_icon_view\"])[4]")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/edit_text_search_input\"]")));
		 launchCamera();
//		 mobileDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		 long searchingTime=System.currentTimeMillis();
		 mobileDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/edit_text_search_input\"]")).sendKeys("Forest");
		 try {
		 if(mobileDriver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"tv.pluto.android:id/text_view_description\"]")).isDisplayed()) {
			 mobileDriver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@resource-id=\"tv.pluto.android:id/text_input_end_icon\"]")).click();
			 mobileDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/edit_text_search_input\"]")).sendKeys("Forest");
		 }else {
			 System.out.println("Search completed successfully");
		 }
		 }catch (Exception e) {
			System.out.println("Try catch not worked properly");
		}
		 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/image_preview\"])")));
		 long searchingCompletedTime=System.currentTimeMillis();
		 long durationForSearch=searchingCompletedTime-searchingTime;
//		 androidVideoStoping(mobileDriver);
		 StopCamera();
		 writeToExcel("Android Mobile - Search", durationForSearch, "Passed",filePath);
		 System.out.println("Time taken for search is : "+durationForSearch+"milliseconds");
		 test = extent.createTest("Test Name");
		 test.pass("Mobile search completed successfully.");
	     test.info("Mobile search time: " + durationForSearch + " milliseconds");
	}
	
	@Step("Sign In Test")
    @Severity(SeverityLevel.CRITICAL)
	public void Mobile_Sign_In() throws URISyntaxException, IOException, InterruptedException{
		mobileDriver.findElement(AppiumBy.accessibilityId("Back")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/navigation_bar_item_icon_view\"])[1]")));
		mobileDriver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/navigation_bar_item_icon_view\"])[1]")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("Sign In, Button")));
		mobileDriver.findElement(AppiumBy.accessibilityId("Sign In, Button")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/library_uikit_mobile_emailEditText\"]")));
		mobileDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/library_uikit_mobile_emailEditText\"]")).sendKeys("sss@s.tv");
		mobileDriver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"PillButton\"]/android.widget.Button")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.view.View[@resource-id=\"SignInTextButtonForgotPassword\"]/android.widget.Button")));
		mobileDriver.findElement(AppiumBy.id("tv.pluto.android:id/library_uikit_mobile_emailEditText")).sendKeys("KLSP@family01");
		mobileDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		launchCamera();
//		mobileDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		mobileDriver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"PillButton\"]/android.widget.Button")).click();
		long startTimeAfterClickingSignin=System.currentTimeMillis();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"tv.pluto.android:id/snackbar_text\"]")));
		long TimeTakenForElementsPresent=System.currentTimeMillis();
		long durationForSignin=TimeTakenForElementsPresent-startTimeAfterClickingSignin;
//		androidVideoStoping(mobileDriver);
		StopCamera();
	    writeToExcel("Android Mobile - Sign In", durationForSignin, "Passed",filePath);
		System.out.println("Time taken for signed in is : "+durationForSignin+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("Mobile Sign in completed successfully");
        test.info("Mobile Sign in time: " + durationForSignin + " milliseconds");
        mobileDriver.quit();
		stopAppiumServiceUsing4723();
	}
	
	public void FireTv_ApplicationLaunch() throws URISyntaxException, InterruptedException, IOException {
		startAppiumServiceUsing4723();
		options.setUdid("192.168.2.129:5555");
		FireTVDriver=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		launchCamera();
//		FireTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		wait=new WebDriverWait(FireTVDriver,Duration.ofSeconds(30));
		FireTVWait=new WebDriverWait(FireTVDriver,Duration.ofSeconds(5));
		long startTimeOnceAppClicked=System.currentTimeMillis();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text=\"Not Now\"]")));
		long endTimeOnceNotnowPageLoaded=System.currentTimeMillis();
		StopCamera();
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/top_container\"]")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/top_container\"]")));
//		FireTVVideoStopping(FireTVDriver);
		long durationForAppLaunch=endTimeOnceNotnowPageLoaded-startTimeOnceAppClicked;
		System.out.println("FireTV App Launch "+durationForAppLaunch+" milliseconds");
		writeToExcel("Fire TV - App Launch", durationForAppLaunch, "Passed",filePath);
		extent = new ExtentReports();
		test = extent.createTest("Test Name");
	    test.pass("FireTV App Launch completed successfully");
        test.info("FireTV App Launch: " + durationForAppLaunch + " milliseconds");
	}
	
	public void FireTV_Category_Change_In_LiveTv() throws IOException, InterruptedException, URISyntaxException {
		
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/rocker_channel_logo\"]")));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/main_host_container\"]")));
		snackBarPopUp();
//		FireTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		launchCamera();
		long startedTimeBeforeClickingAnotherCatogory=System.currentTimeMillis();
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@bounds='[830,537][1261,753]]")));
		long endTimeAfterCategoryChange=System.currentTimeMillis();
		StopCamera();
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("//android.view.View[@resource-id=\"tv.pluto.android:id/view_expanded_row_channel_logo_space_holder\"]")));
//		FireTVVideoStopping(FireTVDriver);
		long durationForCategoryChange=endTimeAfterCategoryChange-startedTimeBeforeClickingAnotherCatogory;
		System.out.println("FireTV Live TV Category change time taken is "+durationForCategoryChange+" milliseconds");
		writeToExcel("Fire TV - Category change in Live TV", durationForCategoryChange, "Passed",filePath);
		System.out.println("FireTV Live TV Category change : "+durationForCategoryChange+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("FireTV Live TV Category change completed successfully");
        test.info("FireTV Live TV Category change: " + durationForCategoryChange + " milliseconds");
	}
	
	public void FireTV_Movie_Play_In_LiveTv() throws IOException, InterruptedException, URISyntaxException {
//		FireTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		launchCamera();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/collapsed_row_channel_logo\"])")));
		long startTimeAfterClickingNewMovie=System.currentTimeMillis();
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/rocker_channel_logo\"]")));
		StopCamera();
		long TimeTakenForMoviePlay=System.currentTimeMillis();
		long durationForMoviePlay=TimeTakenForMoviePlay-startTimeAfterClickingNewMovie;
//		FireTVVideoStopping(FireTVDriver);
		System.out.println("FireTV Live TV movie change time taken is "+durationForMoviePlay+" milliseconds");
		writeToExcel("Fire TV - Channel change in Live TV", durationForMoviePlay, "Passed",filePath);
		System.out.println("FireTV Live TV movie change : "+durationForMoviePlay+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("FireTV Live TV movie change completed successfully");
        test.info("FireTV Live TV movie change: " + durationForMoviePlay + " milliseconds");
	}
	
	public void FireTV_On_Demand_Category_Change() throws IOException, InterruptedException, URISyntaxException {
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"tv.pluto.android:id/section_list\"]")));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
//		FireTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/button_get_content_info\"]")));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/main_category_carousel_card_button\" and @text=\"Comedy\"]")));
		launchCamera();
		long startedTimeBeforeClickingAnotherCatogoryInOnDemand=System.currentTimeMillis();
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/normal_card_image\"])")));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"tv.pluto.android:id/metadata_container\"])")));
//		FireTVVideoStopping(FireTVDriver);
		long endTimeAfterCategoryChangeInOndemand=System.currentTimeMillis();
		StopCamera();
		long durationForCategoryChangeInOnDemand=endTimeAfterCategoryChangeInOndemand-startedTimeBeforeClickingAnotherCatogoryInOnDemand;
		System.out.println("FireTV - Catagory change in OnDemand tv is "+durationForCategoryChangeInOnDemand+" milliseconds");
		writeToExcel("Fire TV - On Demand Category Switch", durationForCategoryChangeInOnDemand, "Passed",filePath);
		System.out.println("FireTV OnDemand Category change : "+durationForCategoryChangeInOnDemand+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("FireTV OnDemand Category change completed successfully");
        test.info("FireTV OnDemand Category change: " + durationForCategoryChangeInOnDemand + " milliseconds");
	}
	
	public void FireTV_On_Demand_Movie_Play() throws IOException, InterruptedException, URISyntaxException {
//		FireTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		long startTimeAfterClickingNewMovieOnDemand=System.currentTimeMillis();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"PlayNowButton\"]")));
		launchCamera();
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		while(true) {
		try {
		if(FireTVDriver.findElement(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/play_pause_button\"]")).isDisplayed()) {
		System.out.println("Element is visible, stopping navigation.");
		break;
		}
		}catch(Exception NoSuchElementException) {
			System.out.println("Element not found, pressing UP...");
			FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		}
		}
		StopCamera();
		long TimeTakenForMoviePlayOnDemand=System.currentTimeMillis();
		long durationForMoviePlayForOnDemandMovie=TimeTakenForMoviePlayOnDemand-startTimeAfterClickingNewMovieOnDemand;
		System.out.println("Time taken for movie playing is : "+durationForMoviePlayForOnDemandMovie+"milliseconds");
//		FireTVVideoStopping(FireTVDriver);
		writeToExcel("Fire TV - On Demand Movie Playback", durationForMoviePlayForOnDemandMovie, "Passed",filePath);
		System.out.println("FireTV OnDemand movie change : "+durationForMoviePlayForOnDemandMovie+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("FireTV OnDemand movie change completed successfully");
        test.info("FireTV OnDemand movie change: " + durationForMoviePlayForOnDemandMovie + " milliseconds");
	}
	
	public void FireTV_Search() throws IOException, InterruptedException, URISyntaxException {
		while(true) {
			try {
			if(FireTVDriver.findElement(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/play_pause_button\"]")).isDisplayed()) {
			System.out.println("Element is visible, stopping navigation.");
			break;
			}
			}catch(Exception NoSuchElementException) {
				System.out.println("Element not found, pressing UP...");
				FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			}
			}
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"tv.pluto.android:id/section_list\"]")));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
//		FireTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/search_field\"]")));
		launchCamera();
		long searchingTime=System.currentTimeMillis();
		FireTVDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/search_field\"]")).sendKeys("forest");
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/image\"])")));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.Button[@resource-id=\"tv.pluto.android:id/badge\"])")));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.TextView[@resource-id=\"tv.pluto.android:id/rating\"])")));
		long searchingCompletedTime=System.currentTimeMillis();
		StopCamera();
		long durationForSearch=searchingCompletedTime-searchingTime;
		System.out.println("FireTV - Search is : "+durationForSearch+"milliseconds");
		writeToExcel("Fire TV - Search", durationForSearch, "Passed",filePath);
		System.out.println("FireTV Search : "+durationForSearch+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("FireTV Search completed successfully");
        test.info("FireTV Search: " + durationForSearch + " milliseconds");
//		FireTVVideoStopping(FireTVDriver);
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		
	}
	
	public void FireTV_SignIn() throws IOException, InterruptedException, URISyntaxException {
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"tv.pluto.android:id/section_list\"]")));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		savedContentInfoUS();
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/feature_leanback_profile_v2_sign_in_on_web_sign_in_sign_up_button\"]")));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/lib_field_edit_text\"]")));
		FireTVDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/lib_field_edit_text\"]")).sendKeys("kkk@k.tv");
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
//		FireTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"PasswordField\"]")));
		FireTVDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"PasswordField\"]")).sendKeys("aaaaaa");
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
		launchCamera();
		long startTimeAfterClickingSignin=System.currentTimeMillis();
		FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"tv.pluto.android:id/textViewHelpfulTip\"]")));
		StopCamera();
		long TimeTakenForElementsPresent=System.currentTimeMillis();
		long durationForSignin=TimeTakenForElementsPresent-startTimeAfterClickingSignin;
		System.out.println("FireTV signed in is : "+durationForSignin+"milliseconds");
		writeToExcel("Fire TV - Sign In", durationForSignin, "Passed",filePath);
		System.out.println("FireTV signed in : "+durationForSignin+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("FireTV signed in completed successfully");
        test.info("FireTV signed in: " + durationForSignin + " milliseconds");
//		FireTVVideoStopping(FireTVDriver);
		FireTVDriver.quit();
		stopAppiumServiceUsing4723();
	}
	
	public static void Roku_App_Launch_US() throws URISyntaxException, IOException, InterruptedException {
		startAppiumServiceUsing4723();
		rokuDeviceDiscovering();
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "Roku");
		caps.setCapability("appium:automationName", "roku");
		caps.setCapability("appium:deviceName", targetDeviceName);
		caps.setCapability("appium:rokuIp", rokuIp);
		caps.setCapability("appium:rokuHost", rokuIp);
		caps.setCapability("appium:rokuEcpPort", "8060");
		caps.setCapability("appium:rokuUser", "rokudev");
		caps.setCapability("appium:rokuPass", "admin");
		caps.setCapability("appium:keyCooldown", 1500);
		rokuDriver = new AppiumDriver(new URI("http://127.0.0.1:4723/").toURL(), caps);
		rokuDriver.executeScript("roku:pressKey", Map.of("key", "Home"));
		launchCamera();
		rokuDriver.executeScript("roku:activateApp", Map.of("appId", appId));
		long startTimeOnceAppClicked = System.currentTimeMillis();
		waitForPositionAndDurationDisappearedMethod();
		long stopTimeOnceAppFullyLoaded = System.currentTimeMillis();
		StopCamera();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		long durationForAppLaunch=stopTimeOnceAppFullyLoaded-startTimeOnceAppClicked;
		System.out.println("RokuTV - App launch "+durationForAppLaunch+" milliseconds");
		writeToExcelForMobile("Roku TV - Application Launch", durationForAppLaunch, "Passed");
		System.out.println("Roku TV - App launch : "+durationForAppLaunch+"milliseconds");
		extent = new ExtentReports();
		test = extent.createTest("Test Name");
	    test.pass("Roku TV - App launch completed successfully");
        test.info("Roku TV - App launch : " + durationForAppLaunch + " milliseconds");
	}
	
	public static void category_Change_Roku_US() throws IOException, InterruptedException, URISyntaxException {
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		waitForUIStabilization();
//		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
//		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		launchCamera();
		long startTimeForCateforyChangeOnLiveTV = System.currentTimeMillis();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		long stopTimeOnceCategoryChanged = System.currentTimeMillis();
		StopCamera();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		long durationForCategoryChangeInLiveTV=stopTimeOnceCategoryChanged-startTimeForCateforyChangeOnLiveTV;
		System.out.println("RokuTV - Category change in OnDemand "+durationForCategoryChangeInLiveTV+" milliseconds");
		writeToExcelForMobile("Roku TV - Category change in Live TV", durationForCategoryChangeInLiveTV, "Passed");
		System.out.println("RokuTV - Category change in OnDemand : "+durationForCategoryChangeInLiveTV+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("RokuTV - Category change in OnDemand completed successfully");
        test.info("RokuTV - Category change in OnDemand : " + durationForCategoryChangeInLiveTV + " milliseconds");
	}
	
	public static void channel_Change_Roku_US() throws IOException, InterruptedException, URISyntaxException {
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		launchCamera();
		long startTimeForNewMovieChangeOnLiveTV = System.currentTimeMillis();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		waitingForPositionAndDuration();
		long stopTimeForNewMovieChangeCompletedOnLiveTV = System.currentTimeMillis();
		StopCamera();	
		long durationForNewMovieChangeInLiveTV=stopTimeForNewMovieChangeCompletedOnLiveTV-startTimeForNewMovieChangeOnLiveTV;
		System.out.println("RokuTV - Movie change in LiveTV "+durationForNewMovieChangeInLiveTV+" milliseconds");
		writeToExcelForMobile("Roku TV - Channel change in Live TV", durationForNewMovieChangeInLiveTV, "Passed");
		System.out.println("RokuTV - Movie change in LiveTV : "+durationForNewMovieChangeInLiveTV+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("RokuTV - Movie change in LiveTV completed successfully");
        test.info("RokuTV - Movie change in LiveTV : " + durationForNewMovieChangeInLiveTV + " milliseconds");
	}
	
	public static void category_Change_On_Demand_US() throws IOException, InterruptedException, URISyntaxException {
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
//		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		launchCamera();
		long startTimeForCategoryChangeOnDemand = System.currentTimeMillis();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		long stopTimeForCategoryChangeOnDemand = System.currentTimeMillis();
		StopCamera();
		long durationForCategoryChangeInOnDemand=stopTimeForCategoryChangeOnDemand-startTimeForCategoryChangeOnDemand;
		System.out.println("RokuTV - Category change ondemand "+durationForCategoryChangeInOnDemand+" milliseconds");
		writeToExcelForMobile("Roku TV - On Demand Category Switch", durationForCategoryChangeInOnDemand, "Passed");
		System.out.println("RokuTV - Category change ondemand  : "+durationForCategoryChangeInOnDemand+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("RokuTV - Category change ondemand completed successfully");
        test.info("RokuTV - Category change ondemand : " + durationForCategoryChangeInOnDemand + " milliseconds");
		
	}
	
	public static void channel_Change_On_Demand_US() throws IOException, InterruptedException, URISyntaxException {
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		launchCamera();
		long startTimeForChannelChangeOnDemand = System.currentTimeMillis();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		waitingForPositionAndDuration();
		long stopTimeForChannelChangeOnDemand = System.currentTimeMillis();
		StopCamera();
		long durationForChannelChangeInOnDemand=stopTimeForChannelChangeOnDemand-startTimeForChannelChangeOnDemand;
		System.out.println("RokuTV - Channel change ondemand "+durationForChannelChangeInOnDemand+" milliseconds");
		writeToExcelForMobile("Roku TV - On Demand Movie Playback", durationForChannelChangeInOnDemand, "Passed");
		System.out.println("RokuTV - Channel change ondemand  : "+durationForChannelChangeInOnDemand+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("RokuTV - Channel change ondemand completed successfully");
        test.info("RokuTV - channel change ondemand : " + durationForChannelChangeInOnDemand + " milliseconds");
	}
	
	public static void search_Roku_US() throws IOException, InterruptedException, URISyntaxException {
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Up"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Up"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		launchCamera();
		long searchSearchTime = System.currentTimeMillis();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		long searchEndTime = System.currentTimeMillis();
		StopCamera();
		long searchTimeDuration=searchEndTime-searchSearchTime;
		System.out.println("RokuTV - Search "+searchTimeDuration+" milliseconds");
		writeToExcelForMobile("Roku TV - Search", searchTimeDuration, "Passed");
		System.out.println("RokuTV - Search  : "+searchTimeDuration+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("RokuTV - Search completed successfully");
        test.info("RokuTV - Search : " + searchTimeDuration + " milliseconds");
	}
	
	public void signIn_US_Roku() throws IOException, InterruptedException, URISyntaxException {
		
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Back"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		waitForUIStabilization();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		waitForUIStabilization();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Up"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Up"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Up"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Up"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Up"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Down"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Right"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Left"));
		launchCamera();
		long startTimeForSignInButtonClick = System.currentTimeMillis();
		rokuDriver.executeScript("roku:pressKey", Map.of("key","Select"));
		signedInConfirmationForRoku();
		StopCamera();
		long stopTimeForSignInButtonClick = System.currentTimeMillis();
		long signInTimeTaken=stopTimeForSignInButtonClick-startTimeForSignInButtonClick;
		System.out.println("RokuTV - SignIn "+signInTimeTaken+" milliseconds");
		writeToExcelForMobile("Roku TV - Sign In", signInTimeTaken, "Passed");
		System.out.println("RokuTV - SignIn  : "+signInTimeTaken+"milliseconds");
		test = extent.createTest("Test Name");
	    test.pass("RokuTV - SignIn completed successfully");
        test.info("RokuTV - SignIn : " + signInTimeTaken + " milliseconds");
		stopAppiumServiceUsing4723();
		
	}
	
	public void AndroidTVAppLaunch() throws URISyntaxException, IOException, InterruptedException {
		startAppiumServiceUsing4723();
		options.setUdid("192.168.2.11:5555");
        AndroidTVDriver=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        launchCamera();
        long startTimeOnceAppClicked=System.currentTimeMillis();
        wait=new WebDriverWait(AndroidTVDriver,Duration.ofSeconds(30));
//      AndroidTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
        try {
        if(AndroidTVDriver.findElement(AppiumBy.xpath("//android.widget.Button[resource-id=\"android:id/button1\"]")).isDisplayed()) {
        	AndroidTVDriver.findElement(AppiumBy.xpath("//android.widget.Button[resource-id=\"android:id/button1\"]")).click();
        }}catch (NoSuchElementException e) {
        	System.out.println("Network is working good");
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text=\"Not Now\"]")));
        StopCamera();
        long endTimeOnceNotnowPageLoaded=System.currentTimeMillis();
        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/top_container\"]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/top_container\"]")));
//      androidVideoStoping(AndroidTVDriver);
        long durationForAppLaunch=endTimeOnceNotnowPageLoaded-startTimeOnceAppClicked;
        System.out.println("TimeTaken for app launch "+durationForAppLaunch+" milliseconds");
        writeToExcel("Android TV - Application Launch", durationForAppLaunch, "Passed",filePath);

    }
	
	 public void AndroidTV_Category_Change_In_LiveTv() throws InterruptedException, IOException, URISyntaxException {
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id='tv.pluto.android:id/rocker_channel_logo']")));
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.view.View[@resource-id='tv.pluto.android:id/view_expanded_row_channel_logo_space_holder']")));
//	        AndroidTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
	        try {
	            WebElement dismissButton = AndroidTVDriver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id='tv.pluto.android:id/snackbar_dismiss_button']"));
	            if (dismissButton.isDisplayed()) {
	            	AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
	            	AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
	                dismissButton.click();
	                System.out.println("Pop-up dismissed.");
	            }
	        } catch (NoSuchElementException e) {
	            System.out.println("No pop-up found.");
	        }
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
	        launchCamera();
	        long startedTimeBeforeClickingAnotherCategory = System.currentTimeMillis();
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
	        try {
	            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id='tv.pluto.android:id/collapsed_row_channel_logo']")));
	        } catch (TimeoutException e) {
	            System.out.println("Category change element not found within timeout.");
	        }
	        long endTimeAfterCategoryChange = System.currentTimeMillis();
	        StopCamera();
//	      androidVideoStoping(AndroidTVDriver);
	        long durationForCategoryChange = endTimeAfterCategoryChange - startedTimeBeforeClickingAnotherCategory;
	        System.out.println("Time taken for category change: " + durationForCategoryChange + " milliseconds");
	        writeToExcel("Android TV - Category change in Live TV", durationForCategoryChange, "Passed", filePath);
	    }
	 	
	  public void AndroidTV_Movie_Play_In_LiveTv() throws IOException, InterruptedException, URISyntaxException {

//		  AndroidTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
		  AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
		  AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
	        try {
	            wait.until(ExpectedConditions.presenceOfElementLocated(
	                    AppiumBy.xpath("//android.widget.ImageView[@resource-id='tv.pluto.android:id/collapsed_row_channel_logo']")));
	        } catch (TimeoutException e) {
	            System.out.println("Movie category logo not found within timeout.");
	        }
	      launchCamera();
		  AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
	      long startTimeAfterClickingNewMovie = System.currentTimeMillis();
	        try {
	            wait.until(ExpectedConditions.invisibilityOfElementLocated(
	                    AppiumBy.xpath("//android.widget.ProgressBar"))); // Example: Wait for loading indicator to disappear
	        } catch (TimeoutException e) {
	            System.out.println("Loading screen did not disappear in time.");
	        }
	        long timeTakenForMoviePlay = System.currentTimeMillis();
	        StopCamera();
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
	        long durationForMoviePlay = timeTakenForMoviePlay - startTimeAfterClickingNewMovie;
	        System.out.println("Time taken for movie playing is: " + durationForMoviePlay + " milliseconds");
//		      androidVideoStoping(AndroidTVDriver);
	        writeToExcel("Android TV - Channel change in Live TV", durationForMoviePlay, "Passed", filePath);
	    }
	  
	  public void AndroidTV_Category_Change_On_Demand() throws IOException, InterruptedException, URISyntaxException {
		  while (true) {
			    try {
			        if (AndroidTVDriver.findElement(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"tv.pluto.android:id/section_list\"]")).isDisplayed()) {
			            System.out.println("L1 is visible");
			            break; // Exit the loop when condition is met
			        } else {
			            AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
			        }
			    } catch (Exception e) {
			        e.printStackTrace(); // Print the full exception details
			    }
			}
          AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
          AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
          AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
          AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
//        AndroidTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(8)));
          wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/button_get_content_info\"]")));
          AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
          launchCamera();
          long startedTimeBeforeClickingAnotherCatogoryInOnDemand=System.currentTimeMillis();
          wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/main_category_carousel_card_button\" and @text=\"Comedy\"]")));
          AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
          AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
          wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/normal_card_image\"])")));
          wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"tv.pluto.android:id/metadata_container\"])")));
          long endTimeAfterCategoryChangeInOndemand=System.currentTimeMillis();
          StopCamera();
//	      androidVideoStoping(AndroidTVDriver);
          long durationForCategoryChangeInOnDemand=endTimeAfterCategoryChangeInOndemand-startedTimeBeforeClickingAnotherCatogoryInOnDemand;
          System.out.println("TimeTaken for catagory change in OnDemand tv is "+durationForCategoryChangeInOnDemand+" milliseconds");
          writeToExcel("Android TV - On Demand Category Switch", durationForCategoryChangeInOnDemand, "Passed",filePath);
      }
	  
	  public void AndroidTV_OnDemand_Movie_Change() throws InterruptedException, IOException, URISyntaxException {
		  	AndroidTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(9)));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			launchCamera();
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		    wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"PlayNowButton\"]")));
		    AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		    long startTimeAfterClickingNewMovieOnDemand = System.currentTimeMillis();
		    while(true) {
		    try{
		    	if(AndroidTVDriver.findElement(AppiumBy.accessibilityId("Pause, button. Press right for video controls, press left to enter main menu")).isDisplayed()) {
		    		System.out.println("Element is visible, stop navigation");
		    		break;
		    	}
		    }catch (Exception e) {
				System.out.println("Element is not visible, pressing up...");
				AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			}
		    }
		    wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/progress_animation\"]")));
		    long timeTakenForMoviePlayOnDemand = System.currentTimeMillis();
		    StopCamera();
	//	    androidVideoStoping(AndroidTVDriver);
	      	long durationForMoviePlayForOnDemandMovie = timeTakenForMoviePlayOnDemand - startTimeAfterClickingNewMovieOnDemand;
	      	System.out.println("Time taken for movie playing is: " + durationForMoviePlayForOnDemandMovie + " milliseconds");
	      	writeToExcel("Android TV - On Demand Movie Playback", durationForMoviePlayForOnDemandMovie, "Passed",filePath);
	        
	    }
	  
	  public void AndroidTV_Search() throws InterruptedException, URISyntaxException, IOException {
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("Pause, button. Press right for video controls, press left to enter main menu")));
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		    wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"PlayNowButton\"]")));
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
	          wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"tv.pluto.android:id/section_list\"]")));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
//	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"tv.pluto.android:id/section_list\"]")));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
//	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
	        AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
//	        AndroidTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/search_field\"]")));
	        launchCamera();
	        AndroidTVDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/search_field\"]")).sendKeys("forest");
	        long searchingTime=System.currentTimeMillis();
//	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.Button[@resource-id=\"tv.pluto.android:id/badge\"])")));
//	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.TextView[@resource-id=\"tv.pluto.android:id/rating\"])")));
	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"tv.pluto.android:id/image\"])")));
	        long searchingCompletedTime=System.currentTimeMillis();
	        StopCamera();
	        long durationForSearch=searchingCompletedTime-searchingTime;
	        System.out.println("Time taken for search is : "+durationForSearch+"milliseconds");
//	        androidVideoStoping(AndroidTVDriver);
	        writeToExcel("Android TV - Search", durationForSearch, "Passed",filePath);
        }
	  
	  public void AndroidTV_Sign_In() throws InterruptedException, IOException, URISyntaxException {
		  	AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"tv.pluto.android:id/section_list\"]")));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			savedContentInfoAndroidTV();
			wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/feature_leanback_profile_v2_sign_in_on_web_sign_in_sign_up_button\"]")));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/lib_field_edit_text\"]")));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
			AndroidTVDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"tv.pluto.android:id/lib_field_edit_text\"]")).sendKeys("kkk@k.tv");
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			//KEYBOARD CLOSURE CODE START
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			//KEYBOARD CLOSURE CODE STOP
//			AndroidTVDriver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(5)));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
			AndroidTVDriver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"PasswordField\"]")).sendKeys("aaaaaa");
			//KEYBOARD CLOSURE CODE START
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.BACK));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
			launchCamera();
			long startTimeAfterClickingSignin=System.currentTimeMillis();
			AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			//KEYBOARD CLOSURE CODE STOP
			wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"tv.pluto.android:id/textViewHelpfulTip\"]")));
			long TimeTakenForElementsPresent=System.currentTimeMillis();
			StopCamera();
			long durationForSignin=TimeTakenForElementsPresent-startTimeAfterClickingSignin;
			System.out.println("Time taken for signed in is : "+durationForSignin+"milliseconds");
			writeToExcel("Android TV - Sign In", durationForSignin, "Passed",filePath);
	  }

	 
		
	 @AfterClass
	    public void tearDown() throws Exception {
		stopRecording();
		fileWrite();
	    System.out.println("Browser closed successfully.");
	 }
}
