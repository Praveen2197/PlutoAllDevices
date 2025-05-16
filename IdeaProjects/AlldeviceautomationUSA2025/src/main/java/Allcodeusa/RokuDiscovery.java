package Allcodeusa;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class RokuDiscovery {

    private static final String SSDP_DISCOVER_MESSAGE =
        "M-SEARCH * HTTP/1.1\r\n" +
        "HOST: 239.255.255.250:1900\r\n" +
        "MAN: \"ssdp:discover\"\r\n" +
        "ST: roku:ecp\r\n" +
        "\r\n";

    public static Map<String, String> discoverRokuDevices() throws IOException {
        Map<String, String> devices = new HashMap<>();

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);

        InetAddress ssdpAddress = InetAddress.getByName("239.255.255.250");
        DatagramPacket packet = new DatagramPacket(SSDP_DISCOVER_MESSAGE.getBytes(), SSDP_DISCOVER_MESSAGE.length(), ssdpAddress, 1900);
        socket.send(packet);

        byte[] buffer = new byte[1024];
        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 5000) {
            try {
                socket.receive(responsePacket);
                String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                if (response.contains("roku:ecp")) {
                    String location = extractHeader(response, "LOCATION");
                    if (location != null) {
                        String friendlyName = fetchFriendlyName(location);
                        devices.put(friendlyName, location);
                    }
                }
            } catch (SocketTimeoutException e) {
                break;
            }
        }

        socket.close();
        return devices;
    }

    private static String extractHeader(String response, String header) {
        for (String line : response.split("\r\n")) {
            if (line.toUpperCase().startsWith(header + ":")) {
                return line.split(":", 2)[1].trim();
            }
        }
        return null;
    }

    private static String fetchFriendlyName(String location) throws IOException {
        URL url = new URL(location + "/query/device-info");
        Scanner scanner = new Scanner(url.openStream());
        String xml = scanner.useDelimiter("\\A").next();
        scanner.close();

        int start = xml.indexOf("<friendly-device-name>") + 22;
        int end = xml.indexOf("</friendly-device-name>");
        return xml.substring(start, end).trim();
    }
}
