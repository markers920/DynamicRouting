package markprojects;

//import org.apache.commons.lang3.tuple.Pair;
//import StationMetaData;
import java.util.List;
import java.util.ArrayList;

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


    public StationMetaData clone() {
        return this.stationMetaData.clone();
        //return new Station(this.name, position.getLeft(), position.getRight());
    }

}



