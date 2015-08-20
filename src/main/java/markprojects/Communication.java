package markprojects;

import java.util.List;
import java.util.ArrayList;

public class Communication {

    private StationMetaData source;
    private List<StationMetaData> destination;
    private long timeSent;
    private long lifetime;

    public Communication(Station src, Station dst, long t, long l) {
        this.source = src.clone();

        this.destination = new ArrayList<Station>();
        this.destination.add(dst.clone());

        this.timeSent = t;
        this.lifetime = l;
    }

    public Communication(Station src, List<Station> dst, long t, long l) {
        this.source = src.clone();

        this.destination = new ArrayList<Station>();
        for(Station d : dst) {
            this.destination.add(d.clone());
        }

        this.timeSent = t;
        this.lifetime = l;
    }

    public long isAlive(long time) {
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

