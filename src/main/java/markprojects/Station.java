package markprojects;

import org.apache.commons.lang3.tuple.Pair;

public class Station {

    private String name;
    private Pair<Double, Double> position;
    private Set<Communication> currentBroadcasts = new HashSet<Communication>();

    public Station(String n) {
        this.name = n;
    }

    public Station(String n, double x, double y) {
        this.name = n;
        setPosition(x, y);
    }


    public void setPosition(double x, double y) {
        position = Pair.of(Double.valueOf(x), Double.valueOf(y));
    }


    //TODO: remove this and use refs down the road
    public Station clone() {
        return new Station(position.getLeft(), position.getRight());
    }

    public void update(long time) {
        for(Communication c : currentBroadcasts) {
            if(!c.isAlive()) {
                System.out.println("dead: " + c.toString());
            }
        }
    }
}



