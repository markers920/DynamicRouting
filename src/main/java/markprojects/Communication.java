package markprojects;

import java.util.List;
import java.util.ArrayList;

public class Communication {

    private Station source;
    private List<Station> destination;
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

}

