package Commands;

import java.io.Serializable;
import java.util.*;

public class RemoveGreaterCommand implements Command, UseId, Serializable {
    VehiclesCollectionKeeper collectionKeeper;
    Vehicle newVehicle;
    private int userId;

    public RemoveGreaterCommand() {
        this.newVehicle = CreateNewVehicle.create();
    }

    @Override
    public void execute() {
        ArrayList<Vehicle> newCollection = new ArrayList<>();
        while (!this.collectionKeeper.getCollection().isEmpty()) {
            Vehicle polled = this.collectionKeeper.getCollection().poll();
            if (polled != null) {
                if (polled.compareTo(this.newVehicle) < 0) {
                    newCollection.add(polled);
                } else {
                    newCollection.add(newVehicle);
                    break;
                }
            }
        }
        collectionKeeper.getCollection().clear();
        newCollection.forEach(v -> collectionKeeper.getCollection().add(v));
        System.out.println("Элементы превышающие заданный были удалены из коллекции");
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collectionKeeper = new VehiclesCollectionKeeper(collection);
    }

    @Override
    public PriorityQueue<Vehicle> getCollection() {
        return this.collectionKeeper.getCollection();
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
