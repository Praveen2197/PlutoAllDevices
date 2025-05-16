package Allcodeusa;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class externalDeviceVideo extends uiStabilization{
	
	 public static AndroidDriver mobileDriver1;
	 public static WebDriverWait wait;
	 public WebDriver driver;
	 public static UiAutomator2Options options1;

//	
//	public static void launchCamera() throws IOException, InterruptedException, URISyntaxException {//		process = Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"appium -p 4723\"");
//	options1=new UiAutomator2Options();
//		mobileDriver1=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options1);
//	options1=new UiAutomator2Options();
//	options1.setUdid("39081JEHN16873");     
//	String startCamera = "adb -s 39081JEHN16873 shell am start -a android.media.action.VIDEO_CAPTURE";
//	
//     Runtime.getRuntime().exec(startCamera);
//        System.out.println("Camera launched successfully...");
//        Thread.sleep(2000);
//        try {
//       if(mobileDriver1.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Start video\"]")).isDisplayed()) {
//        	mobileDriver1.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Start video\"]")).click();
//        	Thread.sleep(1000);
//     }}catch (Exception e) {
//    	 
//    	 StopCamera();
//    	 launchCamera();
//     }
//       
//       }
  //  }
	
	 @Test
	public static void launchCamera() throws IOException, InterruptedException, URISyntaxException {
		String startCamera = "adb -s 192.168.2.81:5555 shell am start -a android.media.action.VIDEO_CAPTURE";
        Runtime.getRuntime().exec(startCamera);
        Thread.sleep(3000);
        String startRecording="adb -s 192.168.2.81:5555 shell input tap 2670 703";
        Runtime.getRuntime().exec(startRecording);
        System.out.println("Camera launched successfully...");
    }
	
//	public static void StopCamera() throws IOException, URISyntaxException, InterruptedException {
//	        try {
////	        	Thread.sleep(1000);
//	        	mobileDriver1.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Stop video\"]")).click();
//	        	Thread.sleep(1000);
//	        	mobileDriver1.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Done\"]")).click();
//	        } catch (NoSuchElementException  e) {
//	            System.out.println("Stop recording button not found or not clickable: " + e.getMessage());
//	      
//	        }
	 //   }
	
//
	public static void StopCamera() throws IOException, URISyntaxException, InterruptedException {
        try {
        	  String stopRecording="adb -s 192.168.2.81:5555 shell input tap 2686 638";
              Runtime.getRuntime().exec(stopRecording);
              Thread.sleep(4000);
              String startRecording="adb -s 192.168.2.81:5555 shell input tap 1875 1330";
              Runtime.getRuntime().exec(startRecording);
        } catch (NoSuchElementException  e) {
            System.out.println("Stop recording button not found or not clickable: " + e.getMessage());
      
        }
    }

}
