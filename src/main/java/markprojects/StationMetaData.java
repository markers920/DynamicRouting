package markprojects;

import java.awt.Color;

import org.apache.commons.lang3.tuple.Pair;

public class StationMetaData {
    
    //TODO: prob make these dynamic
    public final Color stationColor = Color.WHITE;
    public final int stationSize = 5;

    private String name;
    private Pair<Double, Double> position;

    public StationMetaData() {
    
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getName() {
        return this.name;
    }

    public void setPosition(double x, double y) {
        this.position = Pair.of(Double.valueOf(x), Double.valueOf(y));
    }
    
    public double getx() {
        return position.getLeft();
    }
    
    public double gety() {
        return position.getRight();
    }
    
    public int getIntx() {
        return (int)getx();
    }
    
    public int getInty() {
        return (int)gety();
    }

    public Pair<Double, Double> getPosition() {
        return this.position;
    }

    public StationMetaData clone() {
        StationMetaData ret = new StationMetaData();
        ret.name = name;
        ret.position = Pair.of(position.getLeft(), position.getRight());
        return ret;
    }

    public String toString() {
        return name + " @ (" + position.getLeft() + ", " + position.getRight() + ")";
    }
    
    //TODO: this may nee more context
    public boolean equals(StationMetaData other) {
    	return this.name.equalsIgnoreCase(other.getName());
    }
}
