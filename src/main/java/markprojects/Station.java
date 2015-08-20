package markprojects;

import org.apache.commons.lang3.tuple.Pair;

public class Station {

    private Pair<Double, Double> position;

    public Station() {

    }

    public Station(double x, double y) {
        setPosition(x, y);
    }


    public void setPosition(double x, double y) {
        position = Pair.of(Double.valueOf(x), Double.valueOf(y));
    }


    //TODO: remove this and use refs down the road
    public Station clone() {
        return new Station(position.getLeft(), position.getRight());
    }
}



