package Allcodeusa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;



public class signedInConfirmation extends popUpHandlings{
	public static String targetDeviceName;
	public static void signedInConfirmationForRoku() {

	while (true) {
        String response = getMediaPlayerResponse();

        // Check if both <position> and <duration> tags are present in the response
        if (response.contains("state=\"play\"")) {
            System.out.println("SignedIn Successfully");
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
	    Map<String, String> devices = RokuDiscovery.discoverRokuDevices();
	    System.out.println(devices);
	    if (!devices.containsKey(targetDeviceName)) {
	        throw new RuntimeException("Device with friendly name '" + targetDeviceName + "' not found!");
	    }
	    String deviceLocation = devices.get(targetDeviceName);
	    URI deviceUri = new URI(deviceLocation);
	    System.out.println(deviceUri);
	    AllDeviceAutomationUS.rokuIp = deviceUri.getHost();
	    System.out.println(AllDeviceAutomationUS.rokuIp);
	    AllDeviceAutomationUS.appId="74519";
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
}
