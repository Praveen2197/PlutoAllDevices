package Allcodeusa;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_QUICKTIME;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_QUICKTIME_ANIMATION;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

public class webRecording extends androidVideoStop{
	
	public ScreenRecorder screenRecorder;
	
	//Initialize and start screen recording
	public void startRecording() throws IOException, AWTException {
	
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
	                .getDefaultConfiguration();
	        Rectangle captureArea = gc.getBounds();
	        File movieFolder = new File(System.getProperty("user.home"), "AutomationVideos\\WebVideos");
	        screenRecorder = new ScreenRecorder(gc, captureArea,
	                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
	                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_ANIMATION, CompressorNameKey,
	                        ENCODING_QUICKTIME_ANIMATION, DepthKey, 24, FrameRateKey, Rational.valueOf(30), QualityKey,
	                        0.5f, KeyFrameIntervalKey, 30 * 60),
	                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
	                null, movieFolder);
	}
	
	public void stopRecording() throws IOException {
		 if (screenRecorder != null) {
	            screenRecorder.stop();
	            System.out.println("Recording stopped. File saved to: " + screenRecorder.getCreatedMovieFiles());
	        }

	}

}
