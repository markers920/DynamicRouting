package markprojects;

//import org.apache.commons.lang3.tuple.Pair;
//import StationMetaData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Station {

    private StationMetaData stationMetaData = new StationMetaData();
    private List<Communication> currentBroadcasts = new ArrayList<Communication>();

    public Station(String n) {
        this.stationMetaData.setName(n);
    }

    public Station(String n, double x, double y) {
        this.stationMetaData.setName(n);
        setPosition(x, y);
    }


    public void setPosition(double x, double y) {
        this.stationMetaData.setPosition(x, y);
    }


    public StationMetaData getMetaData() {
        return this.stationMetaData.clone();
        //return new Station(this.name, position.getLeft(), position.getRight());
    }
    
    
    public void addCommunication(Station dst, long startTime, long lifetime) {
    	Color c = randomColor();   //the color of this communication
    	
    	//send out several copies of this message at a regular interval
        for(long pingIndex = startTime; stationMetaData.pingRepeatPeriod*pingIndex < lifetime; pingIndex++) {
            long pingStart = stationMetaData.pingRepeatPeriod*pingIndex;
            currentBroadcasts.add(
                    new Communication(stationMetaData.getx(), stationMetaData.gety(), this, dst, pingStart, lifetime-pingStart, c));
        }
    }
    
    
    public void receiveCommunication(long time, Communication communication) {
    	//if communication still alive
    	//and
    	//this isn't the source station
    	if(communication.isAlive(time) && !communication.getSource().getName().equals(this.stationMetaData.getName())) {
    		//distance from comm origin to this station
	    	double distance = Math.pow(communication.getOriginx() - this.stationMetaData.getx(), 2.0);
			distance += Math.pow(communication.getOriginy() - this.stationMetaData.gety(), 2.0);
			distance = Math.pow(distance, 0.5);
			
			double signalRadius = communication.getRadius(time);
			
			//if station is in the communication's current location
			if(Math.abs(distance-signalRadius) < 1) {
				//reached target station
				if(communication.getDestination().equals(this.getMetaData())) {
		    		System.out.println("Message Received: " + stationMetaData.getName() + " /// " + communication.toString());
		    	}
				
				//not target station
				else {
					currentBroadcasts.add(
							new Communication(
									this.stationMetaData.getx(), 
									this.stationMetaData.gety(), 
									communication.getSource(), 
									communication.getDestination(), 
									time, 
									1000,		//TODO:fix this, set lifetime to termination time in comms object
									communication.getColor()));
				}
			}
    	}
    }
    
    public List<Communication> getBroadcasts() {
    	return currentBroadcasts;
    }

    public void paint(long time, Graphics2D g) {
        Color origionalColor = g.getColor();
        
        //draw comms
        for(Communication communication : currentBroadcasts) {
            if(communication.isAlive(time)) {
                //long age = communication.getAge(time);
                //double width = age * Constants.SIGNAL_SPEED;
            	double signalRadius = communication.getRadius(time);
                
                g.setColor(communication.getColor());
                g.drawOval(
                        (int)(stationMetaData.getx() - signalRadius), 
                        (int)(stationMetaData.gety() - signalRadius), 
                        (int)(2*signalRadius), 
                        (int)(2*signalRadius));
            }
        }
        
        //draw station
        g.setColor(stationMetaData.stationColor);
        g.fillOval(
                (int)(stationMetaData.getIntx() - (stationMetaData.stationSize/2)), 
                (int)(stationMetaData.getInty() - (stationMetaData.stationSize/2)), 
                stationMetaData.stationSize, 
                stationMetaData.stationSize);
        
        g.setColor(origionalColor);
    }
    
    
    private Color randomColor() {
    	Random rand = new Random();
    	float r = rand.nextFloat();
    	float g = rand.nextFloat();
    	float b = rand.nextFloat();
    	return new Color(r, g, b);
    }
}



