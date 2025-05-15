package Allcodeusa;

import java.io.File;
import java.net.ConnectException;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class appiumService extends excelSheetCreation{
	
	public static AppiumDriverLocalService appiumServiceBuilder;
	
	public static void startAppiumServiceUsing4723() {
		try {
		appiumServiceBuilder = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\Test\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).withIPAddress("127.0.0.1").usingPort(4723).build();
		appiumServiceBuilder.start();
	}catch (Exception e) {
		// TODO: handle exception
		appiumServiceBuilder.stop();
		startAppiumServiceUsing4723();
	}
}
	
	public static void stopAppiumServiceUsing4723() {
		appiumServiceBuilder.stop();
	}
	
	public void startAppiumServiceUsing4725() {
		appiumServiceBuilder = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\Test\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).withIPAddress("127.0.0.1").usingPort(4725).build();
		appiumServiceBuilder.start();
	}
	
	public void stopAppiumServiceUsing4725() {
		appiumServiceBuilder.stop();
	}

}
