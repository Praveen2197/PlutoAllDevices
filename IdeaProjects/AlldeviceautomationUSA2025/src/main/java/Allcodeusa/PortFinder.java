package Allcodeusa;

import java.io.IOException;
import java.net.ServerSocket;

public class PortFinder {
	  public static int findFreePort() {
	        try (ServerSocket socket = new ServerSocket(0)) {
	            return socket.getLocalPort();
	        } catch (IOException e) {
	            throw new RuntimeException("No available ports found!", e);
	        }
	    }
}
