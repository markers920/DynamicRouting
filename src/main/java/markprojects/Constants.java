package markprojects;

import java.awt.Color;


public class Constants {

	public static final int WINDOW_SIZE_X = 1920;
    public static final int WINDOW_SIZE_Y = 1080;
    public static final int FRAMES_PER_SECOND = 30;
    public static final int NUMBER_OF_FRAMES = FRAMES_PER_SECOND*70;
    
    public static final String MOVIE_OUTPUT_FILE = "./movie.mp4";
    
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final int BORDER_SIZE = 10;
    public static final int NUMBER_OF_STATIONS = 100;
    public static final int NUMBER_OF_COMMUNICATIONS = 1;
    public static final int MAX_START_TIME = 1;
    
    public static final double SIGNAL_SPEED = 1;	//pixels per unit time
    public static final long RECEIVED_TIME_WINDOW = 10;
    public static final double LIFETIME_DECAY_RATIO = 0.95;
}



