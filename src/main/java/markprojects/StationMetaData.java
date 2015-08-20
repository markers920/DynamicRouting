package markprojects;

import org.apache.commons.lang3.tuple.Pair;

public class StationMetaData {

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

    public Pair<Double, Double> getPosition() {
        return this.position;
    }

    public StationMetaData clone() {
        StationMetaData ret = new StationMetaData();
        ret.name = name;
        ret.position = Pair.of(position.getLeft(), position.getRight());
        return ret;
    }
}
