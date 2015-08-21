package markprojects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

/**
 * Hello world!
 *
 */
public class App {
	
	private static int WINDOW_SIZE_X = 1280;
	private static int WINDOW_SIZE_Y = 1024;
	private static int FRAMES_PER_SECOND = 30;
	private static int NUMBER_OF_FRAMES = FRAMES_PER_SECOND*30;
	
	private static final String MOVIE_OUTPUT_FILE = "./movie.mp4";
	
	
    public static void main( String[] args ) {
    	
    	final IMediaWriter writer = ToolFactory.makeWriter(MOVIE_OUTPUT_FILE);
		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, WINDOW_SIZE_X, WINDOW_SIZE_Y);
        
		BufferedImage img = new BufferedImage(WINDOW_SIZE_X, WINDOW_SIZE_Y, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g = (Graphics2D)img.getGraphics();
		for(int frameIndex = 0; frameIndex < NUMBER_OF_FRAMES; frameIndex++) {
			System.out.println("frameIndex: " + frameIndex + " / " + NUMBER_OF_FRAMES);
			
			
			writer.encodeVideo(0, img, (frameIndex * (1000/FRAMES_PER_SECOND)), TimeUnit.MILLISECONDS);
		}
		
		writer.close();
		
		
		System.out.println("end.");
    }
}
