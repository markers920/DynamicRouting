package markprojects;

//import org.apache.commons.lang3.tuple.Pair;
//import StationMetaData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Station {

    private StationMetaData stationMetaData = new StationMetaData();
    private List<Communication> currentBroadcasts = new ArrayList<Communication>();
    private List<Communication> deadBroadcasts = new ArrayList<Communication>();
    private long lastTimeReceivedTargetedCommunication = -1000;	//TODO: rename this
    private Set<Long> messagesReceived = new HashSet<Long>();

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
    
    
    public void addCommunication(long message, Station dst, long startTime, long lifetime) {
    	Color c = randomColor();   //the color of this communication
    	
    	currentBroadcasts.add(
                new Communication(
                		message, 
                		stationMetaData.getx(), 
                		stationMetaData.gety(), 
                		this, 
                		dst, 
                		startTime, 
                		lifetime, 
                		c));
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
			
			//if station is in the communication's current location - received
			if(Math.abs(distance-signalRadius) < 1) {

				//if this station is already broadcasting this message
	    		if(communicationAlreadySeen(communication)) {
	    			return;
	    		}
	    		
	    		
				//reached target station
				if(communication.getDestination().getName().equalsIgnoreCase(this.getMetaData().getName())) {
		    		//System.out.println("Message Received: " + stationMetaData.getName() + " /// " + communication.toString());
					long message = communication.getMessage();
					if(!messagesReceived.contains(message)) {	//if it hasn't seen this message yet... important for the multiple messages sent
						messagesReceived.add(message);
						lastTimeReceivedTargetedCommunication = time;
					}
		    	}
				
				//not target station
				else {
					currentBroadcasts.add(
							new Communication(
									communication.getMessage(),
									this.stationMetaData.getx(), 
									this.stationMetaData.gety(), 
									communication.getSource(), 
									communication.getDestination(), 
									time, 
									(long)(communication.getLifetime()*Constants.LIFETIME_DECAY_RATIO),
									communication.getColor()));
				}
			}
    	}
    }
    
    private boolean communicationAlreadySeen(Communication communication) {
    	for(Communication cb : currentBroadcasts) {
			if(cb.getMessage() == communication.getMessage()) {
				return true;
			}
		}
		
		for(Communication cb : deadBroadcasts) {
			if(cb.getMessage() == communication.getMessage()) {
				return true;
			}
		}
		
		return false;
    }
    
    public List<Communication> getBroadcasts() {
    	return currentBroadcasts;
    }

    public void paint(long time, Graphics2D g) {
        Color origionalColor = g.getColor();
        
        //draw comms and collect the dead comms
        Set<Communication> deadComms = new HashSet<Communication>();
        for(Communication communication : currentBroadcasts) {
            if(communication.isAlive(time)) {
            	double signalRadius = communication.getRadius(time);
                
                g.setColor(communication.getColor());
                
                g.drawOval(
                        (int)(stationMetaData.getx() - signalRadius), 
                        (int)(stationMetaData.gety() - signalRadius), 
                        (int)(2*signalRadius), 
                        (int)(2*signalRadius));
            }
            
            else {
            	deadComms.add(communication);
            }
        }
        currentBroadcasts.removeAll(deadComms);
        deadBroadcasts.addAll(deadComms);
        
        //draw station
        g.setColor(stationMetaData.stationColor);
        if(time - lastTimeReceivedTargetedCommunication < Constants.RECEIVED_TIME_WINDOW) {
        	int largerSize = 4*stationMetaData.stationSize;
        	g.fillOval(
	                (int)(stationMetaData.getIntx() - (largerSize/2)), 
	                (int)(stationMetaData.getInty() - (largerSize/2)), 
	                largerSize, 
	                largerSize);
        }
        
        else {
	        g.fillOval(
	                (int)(stationMetaData.getIntx() - (stationMetaData.stationSize/2)), 
	                (int)(stationMetaData.getInty() - (stationMetaData.stationSize/2)), 
	                stationMetaData.stationSize, 
	                stationMetaData.stationSize);
        }
        
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



