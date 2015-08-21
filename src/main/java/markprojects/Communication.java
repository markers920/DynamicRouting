package markprojects;

import java.util.ArrayList;
import java.util.List;

public class Communication {

    private StationMetaData source;
    private List<StationMetaData> destination;
    private long timeSent;
    private long lifetime;

    public Communication(Station src, Station dst, long t, long l) {
        this.source = src.getMetaData();

        this.destination = new ArrayList<StationMetaData>();
        this.destination.add(dst.getMetaData());

        this.timeSent = t;
        this.lifetime = l;
    }

    public Communication(Station src, List<Station> dst, long t, long l) {
        this.source = src.getMetaData();

        this.destination = new ArrayList<StationMetaData>();
        for(Station d : dst) {
            this.destination.add(d.getMetaData());
        }

        this.timeSent = t;
        this.lifetime = l;
    }

    public boolean isAlive(long time) {
        return time < (timeSent + lifetime);
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(source.toString());

        for(StationMetaData smd : destination) {
            b.append("\n\t-> " + smd.toString());
        }
        return b.toString();
    }
}

