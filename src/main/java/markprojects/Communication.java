package markprojects;

import java.awt.Color;
//import java.util.ArrayList;
//import java.util.List;
import java.awt.Graphics2D;

import org.apache.commons.lang3.tuple.Pair;

public class Communication {

    //TODO: prob make these dynamic
    private Color color;	// = Color.YELLOW;
    
    
    private StationMetaData source;
    private Pair<Double, Double> origin;
    private StationMetaData destination;
    private long timeSent;
    private long lifetime;
    

    public Communication(double x, double y, Station src, Station dst, long t, long l, Color c) {
        this.origin = Pair.of(Double.valueOf(x), Double.valueOf(y));
        
        this.source = src.getMetaData();
        this.destination = dst.getMetaData();

        this.timeSent = t;
        this.lifetime = l;
        
        this.color = c;
    }
    
    public Communication(double x, double y, StationMetaData src, StationMetaData dst, long t, long l, Color c) {
        this.origin = Pair.of(Double.valueOf(x), Double.valueOf(y));
        
        this.source = src;
        this.destination = dst;

        this.timeSent = t;
        this.lifetime = l;
        
        this.color = c;
    }

    
    public Color getColor() {
        return this.color;
    }

    public long terminationTime() {
    	return timeSent + lifetime;
    }
    
    public boolean isAlive(long time) {
        return time < terminationTime();
    }
    
    public long getAge(long time) {
        return time - timeSent;
    }
    
    public double getRadius(long time) {
    	return getAge(time) * Constants.SIGNAL_SPEED;
    }
    
    public StationMetaData getSource() {
    	return source;
    }
    
    public StationMetaData getDestination() {
    	return destination;
    }
    
    public double getOriginx() {
        return origin.getLeft();
    }
    
    public double getOriginy() {
        return origin.getRight();
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        
        b.append(source.toString() + " -> " + destination.toString());

        //b.append("\n\t*** time sent:   " + timeSent);
        //b.append("\n\t*** lifetime:    " + lifetime);
        //b.append("\n\t*** termination: " + (timeSent+lifetime));
        
        return b.toString();
    }
    
    public void paint(long time, Graphics2D g) {
        Color origionalColor = g.getColor();
        
        //draw comms
        if(isAlive(time)) {
            long age = getAge(time);
            double width = age * Constants.SIGNAL_SPEED;
            
            g.setColor(color);
            g.drawOval(
                    (int)(origin.getLeft() - (width/2)), 
                    (int)(origin.getRight() - (width/2)), 
                    (int)width, 
                    (int)width);
        }
        
        
        g.setColor(origionalColor);
    }
    
}

