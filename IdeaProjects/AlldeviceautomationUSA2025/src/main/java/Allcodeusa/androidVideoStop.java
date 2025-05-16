package Allcodeusa;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class androidVideoStop extends waitForPositionAndDuration {
	public AndroidDriver mobileDriver;
	public static AndroidDriver FireTVDriver;
	public UiAutomator2Options options;
	public String filePath,methodName,fileName1,directoryPath;
	
	public void androidVideoStoping(AndroidDriver mobileDriver) throws MalformedURLException, URISyntaxException {
//		options=new UiAutomator2Options();
//		mobileDriver=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		String base64String1=mobileDriver.stopRecordingScreen();
		DateTimeFormatter dateTimeFormat1 = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
		LocalDateTime now1 = LocalDateTime.now();
		methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		fileName1=methodName+" Recorded_video "+dateTimeFormat1.format(now1)+".mp4";
		directoryPath = "C:\\Users\\Test\\AutomationVideos\\AndroidMobileVideos\\";
		filePath = directoryPath + fileName1; 
		byte[] data1=java.util.Base64.getDecoder().decode(base64String1);
		 try (java.io.FileOutputStream stream = new java.io.FileOutputStream(filePath)) {
	            stream.write(data1);
	        } catch (java.io.IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public void FireTVVideoStopping(AndroidDriver FireTVDriver) {
		String base64String1=FireTVDriver.stopRecordingScreen();
		DateTimeFormatter dateTimeFormat1 = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
		LocalDateTime now1 = LocalDateTime.now();
		methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		fileName1=methodName+" Recorded_video "+dateTimeFormat1.format(now1)+".mp4";
		directoryPath = "C:\\Users\\Test\\AutomationVideos\\FireTVVideos\\";
		filePath = directoryPath + fileName1; 
		byte[] data1=java.util.Base64.getDecoder().decode(base64String1);
		 try (java.io.FileOutputStream stream = new java.io.FileOutputStream(filePath)) {
	            stream.write(data1);
	        } catch (java.io.IOException e) {
	            e.printStackTrace();
	        }
	}
	
}
