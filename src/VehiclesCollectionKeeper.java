import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Класс, хранящий в себе коллекцию и данные о ней.
 */

public class VehiclesCollectionKeeper implements Serializable {
    private final Date DATE;
    private PriorityQueue<Vehicle> collection;

    public VehiclesCollectionKeeper(PriorityQueue<Vehicle> collection){
        this.collection = collection;
        this.DATE = new Date();
    }

    public Date getDATE() {
        return DATE;
    }

    public PriorityQueue<Vehicle> getCollection() {
        return collection;
    }

    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collection = collection;
    }

    public void add(Vehicle vehicle){
        this.collection.add(vehicle);
    }
}
