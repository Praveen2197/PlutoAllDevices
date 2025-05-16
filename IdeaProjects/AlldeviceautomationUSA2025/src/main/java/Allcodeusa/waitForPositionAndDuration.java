package Allcodeusa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import org.testng.annotations.Test;



public class waitForPositionAndDuration extends waitForPositionAndDurationDisappeared{
	public static String targetDeviceName,deviceLocation;
	public static URI deviceUri;
	public static void waitingForPositionAndDuration() {

	while (true) {
        String response = getMediaPlayerResponse();

        // Check if both <position> and <duration> tags are present in the response
        if (response.contains("<position>") && response.contains("<duration>")) {
            System.out.println("Position and Duration found!");
            break;
        }

        System.out.println("Waiting for position and duration...");
        try {
            Thread.sleep(1000); // Wait 1 second before polling again
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    System.out.println("Proceeding with next step...");

    }
	
	
	public static void rokuDeviceDiscovering() throws IOException, URISyntaxException {
	 	targetDeviceName = "Streaming Stick 4K";  // Friendly name you want to connect to.
	 	while(true) {
	 		Map<String, String> devices=RokuDiscovery.discoverRokuDevices();
	 	 if (devices.containsKey(targetDeviceName)) {
	            deviceLocation = devices.get(targetDeviceName);
	            deviceUri = new URI(deviceLocation);
	            System.out.println(deviceUri);

	            AllDeviceAutomationUS.rokuIp = deviceUri.getHost();
	            System.out.println(AllDeviceAutomationUS.rokuIp);

	            AllDeviceAutomationUS.appId = "74519";

	            break; // Device found, exit the loop
	        } else {
	            System.out.println("Waiting for device to appear...");
	            try {
	                Thread.sleep(3000); // Wait for 3 seconds before retrying
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	}
}
	
	public static String getMediaPlayerResponse() {
    String url = "http://" + AllDeviceAutomationUS.rokuIp + ":8060/query/media-player";

    try {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        System.out.println("Media Player Response: " + response.toString());
        return response.toString(); // Returns full response for comparison

    } catch (Exception e) {
        System.err.println("Error fetching media player response: " + e.getMessage());
    }

    return "UNKNOWN";
}
	public static void verifyMediaPlayerChangeAfterChannelSwitch() {
	    System.out.println("Fetching initial media player response...");
	    String previousMediaResponse = getMediaPlayerResponse(); // Get current media player response

	    int retryCount = 0;
	    while (retryCount < 10) { // Retry up to 10 times
	        try {
	            System.out.println("Attempting to change channel...");

	            String currentMediaResponse = getMediaPlayerResponse(); // Get new media player response

	            if (!currentMediaResponse.equals(previousMediaResponse)) {
	                System.out.println("Media player response changed! Channel switch confirmed.");
	                return;
	            } else {
	                System.out.println("No change in media player response, retrying...");
	            }
	        } catch (Exception e) {
	            System.err.println("Error detecting media player change: " + e.getMessage());
	        }

	        retryCount++;
	    }

	    throw new RuntimeException("Media player response did not change after channel switch!");
	}
}
