package Allcodeusa;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class uiStabilization {
	
	public static String getActiveApp() {
	    String url = "http://" + AllDeviceAutomationUS.rokuIp + ":8060/query/active-app";

	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setRequestMethod("GET");

	        Scanner scanner = new Scanner(connection.getInputStream());
	        StringBuilder response = new StringBuilder();
	        while (scanner.hasNext()) {
	            response.append(scanner.nextLine());
	        }
	        scanner.close();

	        System.out.println("Active App Response: " + response.toString());
	        return response.toString(); // Returns XML with active app info

	    } catch (Exception e) {
	        System.err.println("Error fetching active app: " + e.getMessage());
	    }

	    return "UNKNOWN";
	}

	public static void waitForUIStabilization() {
	    String previousState = getActiveApp();
	    int unchangedCount = 0;

	    for (int i = 0; i < 10; i++) { // Max retries
	        try {
	            Thread.sleep(2000);
	            String currentState = getActiveApp();

	            if (currentState.equals(previousState)) {
	                unchangedCount++;
	            } else {
	                unchangedCount = 0; // Reset if change detected
	            }

	            previousState = currentState;

	            if (unchangedCount >= 3) {
	                System.out.println("UI is stable now!");
	                return;
	            }

	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }

	    System.out.println("Timeout waiting for UI stabilization.");
	}

}
