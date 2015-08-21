package markprojects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

/**
 * Hello world!
 *
 */
public class App {
    
    private static final int WINDOW_SIZE_X = 1280;
    private static final int WINDOW_SIZE_Y = 1024;
    private static final int FRAMES_PER_SECOND = 30;
    private static final int NUMBER_OF_FRAMES = FRAMES_PER_SECOND*20;
    
    private static final String MOVIE_OUTPUT_FILE = "./movie.mp4";
    
    private static Random random = new Random();
    
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final int BORDER_SIZE = 10;
    private static final int NUMBER_OF_STATIONS = 15;
    private static List<Station> stations = new ArrayList<Station>();
    
    
    public static void main( String[] args ) {
        
        initilizeStations();
        
        stations.get(0).addCommunication(stations.get(1), 0, 400);
        stations.get(1).addCommunication(stations.get(2), 0, 400);
        stations.get(2).addCommunication(stations.get(3), 0, 400);
        stations.get(3).addCommunication(stations.get(4), 0, 300);
        stations.get(4).addCommunication(stations.get(5), 0, 300);
        
        
        final IMediaWriter writer = ToolFactory.makeWriter(MOVIE_OUTPUT_FILE);
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, WINDOW_SIZE_X, WINDOW_SIZE_Y);
        
        BufferedImage img = new BufferedImage(WINDOW_SIZE_X, WINDOW_SIZE_Y, BufferedImage.TYPE_3BYTE_BGR);
        
        Graphics2D g = (Graphics2D)img.getGraphics();
        List<Communication> allBroadcasts = new ArrayList<Communication>();
        for(long frameIndex = 0; frameIndex < NUMBER_OF_FRAMES; frameIndex++) {
            System.out.println("frameIndex: " + frameIndex + " / " + NUMBER_OF_FRAMES);
            
            //fill background
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, WINDOW_SIZE_X, WINDOW_SIZE_Y);
            
            
            allBroadcasts.clear();
            for(Station station : stations) {
            	allBroadcasts.addAll(station.getBroadcasts());
            }
            
            for(Communication broadcast : allBroadcasts) {
            	for(Station station : stations) {
            		station.receiveCommunication(frameIndex, broadcast);
            	}
        	}
            
            
            //paint each station
            for(Station station : stations) {
                station.paint(frameIndex, g);
            }
            
            
            writer.encodeVideo(0, img, (frameIndex * (1000/FRAMES_PER_SECOND)), TimeUnit.MILLISECONDS);
        }
        
        writer.close();
        
        
        System.out.println("end.");
    }
    
    private static void initilizeStations() {
        for(int stationIndex = 0; stationIndex < NUMBER_OF_STATIONS; stationIndex++) {
            int x = (int)((BORDER_SIZE/2.0) + random.nextInt(WINDOW_SIZE_X-BORDER_SIZE));
            int y = (int)((BORDER_SIZE/2.0) + random.nextInt(WINDOW_SIZE_Y-BORDER_SIZE));
            stations.add(new Station("station-" + stationIndex, x, y));
        }
    }
}
