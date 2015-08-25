package markprojects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

// saved locally: C:\Users\markers\git\DynamicRouting

public class App {
    private static Random random = new Random(1);
    private static List<Station> stations = new ArrayList<Station>();
    
    
    public static void main( String[] args ) {
        
        initilizeStations();
        initCommunications();
        
        final IMediaWriter writer = ToolFactory.makeWriter(Constants.MOVIE_OUTPUT_FILE);
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, Constants.WINDOW_SIZE_X, Constants.WINDOW_SIZE_Y);
        
        BufferedImage img = new BufferedImage(Constants.WINDOW_SIZE_X, Constants.WINDOW_SIZE_Y, BufferedImage.TYPE_3BYTE_BGR);
        
        Graphics2D g = (Graphics2D)img.getGraphics();
        List<Communication> allBroadcasts = new ArrayList<Communication>();
        for(long frameIndex = 0; frameIndex < Constants.NUMBER_OF_FRAMES; frameIndex++) {
            System.out.println("frameIndex: " + frameIndex + " / " + Constants.NUMBER_OF_FRAMES);
            
            //fill background
            g.setColor(Constants.BACKGROUND_COLOR);
            g.fillRect(0, 0, Constants.WINDOW_SIZE_X, Constants.WINDOW_SIZE_Y);
            
            
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
            
            
            writer.encodeVideo(0, img, (frameIndex * (1000/Constants.FRAMES_PER_SECOND)), TimeUnit.MILLISECONDS);
        }
        
        writer.close();
        
        
        System.out.println("end.");
    }
    
    private static void initilizeStations() {
        for(int stationIndex = 0; stationIndex < Constants.NUMBER_OF_STATIONS; stationIndex++) {
            int x = (int)((Constants.BORDER_SIZE/2.0) + random.nextInt(Constants.WINDOW_SIZE_X-Constants.BORDER_SIZE));
            int y = (int)((Constants.BORDER_SIZE/2.0) + random.nextInt(Constants.WINDOW_SIZE_Y-Constants.BORDER_SIZE));
        	//double r = ((double)stationIndex) / NUMBER_OF_STATIONS;
        	//int x =  (int)(r*WINDOW_SIZE_X);
        	//int y =  (int)(r*WINDOW_SIZE_X);
            stations.add(new Station("station-" + stationIndex, x, y));
        }
    }
    
    private static void initCommunications() {
    	int minStartTime = Integer.MAX_VALUE;
    	int maxStartTime = Integer.MIN_VALUE;
    	for(int cIdx = 0; cIdx < Constants.NUMBER_OF_COMMUNICATIONS; cIdx++) {
    		//get random source and destination index values
    		int srcIdx = random.nextInt(Constants.NUMBER_OF_STATIONS);
    		int dstIdx = srcIdx;
    		do {
    			dstIdx = random.nextInt(Constants.NUMBER_OF_STATIONS);
    		}while(dstIdx == srcIdx);
    		
    		int startTime = random.nextInt(Constants.MAX_START_TIME);	//delay
    		minStartTime = Math.min(minStartTime, startTime);
    		maxStartTime = Math.max(maxStartTime, startTime);
    		
            stations.get(srcIdx).addCommunication(cIdx, stations.get(dstIdx), startTime, 400);
    	}
    	
    	System.out.println("MIN Start Time: " + minStartTime);
    	System.out.println("MAX Start Time: " + maxStartTime);
    }
}
