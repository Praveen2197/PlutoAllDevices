package Allcodeusa;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class popUpHandlings extends externalDeviceVideo{
	 public static AndroidDriver mobileDriver,FireTVDriver;
	 public static WebDriverWait wait,FireTVWait;
	 public WebDriver driver;

		public void handlingPermission() throws MalformedURLException, URISyntaxException {
			AllDeviceAutomationUS.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")));
			if(AllDeviceAutomationUS.mobileDriver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")).isDisplayed()) {
				AllDeviceAutomationUS.mobileDriver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")).click();
			}else {
				System.out.println("Permission notification not appeared");
				}
			}
		
		public void popupHandling2() {
			// TODO Auto-generated method stub
			 try {
				 AllDeviceAutomationUS.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Next']")));
				 if(AllDeviceAutomationUS.driver.findElement(By.xpath("//button[@class='rfmodal-button-no']")).isDisplayed()) {
					 AllDeviceAutomationUS.driver.findElement(By.xpath("//button[@class='rfmodal-button-no']")).click();
				 }
			 }catch (Exception e) {
				System.out.println("UnLock Free TV Pop is not visible");
			}
			
		}
		
		public void popupHandling() {
			try {
				if(AllDeviceAutomationUS.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Remind Me Later']"))).isDisplayed()) {
					AllDeviceAutomationUS.driver.findElement(By.xpath("//span[text()='Remind Me Later']")).click();
				}
			}catch (Exception e) {
					System.out.println("Popup not populated");
				}
		}
		
		public void snackBarPopUp() {
			try{
				AllDeviceAutomationUS.FireTVWait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/snackbar_dismiss_button\"]")));
				if(AllDeviceAutomationUS.FireTVDriver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id=\"tv.pluto.android:id/snackbar_dismiss_button\"]")).isDisplayed()) {
					AllDeviceAutomationUS.FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
					AllDeviceAutomationUS.FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					System.out.println("Pop up available");
				}
			}catch(NoSuchElementException e) {
				System.out.println("Pop up not available");
			}
		}
		
		public void savedContentInfoUS() {
			try {
				AllDeviceAutomationUS.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.CompoundButton[@resource-id=\"tv.pluto.android:id/feature_leanback_profile_v2_merge_data_yes\"]")));
				if(AllDeviceAutomationUS.FireTVDriver.findElement(AppiumBy.xpath("//android.widget.CompoundButton[@resource-id=\"tv.pluto.android:id/feature_leanback_profile_v2_merge_data_yes\"]")).isDisplayed()) {
					AllDeviceAutomationUS.FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
					AllDeviceAutomationUS.FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					System.out.println("Saved Contents are not moved to signin");
				}
			}catch (Exception e) {
				System.out.println("Saved content nudge is not visible");
			}
		}
		
		public void savedContentInfo() {
			try {
				AllDeviceAutomationUS.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/feature_leanback_profile_v2_merge_data_yes\"]")));
				if(AllDeviceAutomationUS.FireTVDriver.findElement(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"tv.pluto.android:id/feature_leanback_profile_v2_merge_data_yes\"]")).isDisplayed()) {
					AllDeviceAutomationUS.FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
					AllDeviceAutomationUS.FireTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					System.out.println("Saved Contents are not moved to signin");
				}
			}catch (Exception e) {
				System.out.println("Saved content nudge is not visible");
			}
		}
		
		public void savedContentInfoAndroidTV() {
			try {
				if(AllDeviceAutomationUS.wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.CompoundButton[@resource-id=\"tv.pluto.android:id/feature_leanback_profile_v2_merge_data_yes\"]"))).isDisplayed()) {
					AllDeviceAutomationUS.AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
					AllDeviceAutomationUS.AndroidTVDriver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					System.out.println("Saved Contents are not moved to signin");
				}
			}catch (Exception e) {
				System.out.println("Saved content nudge is not visible");
			}
		}
	
}
