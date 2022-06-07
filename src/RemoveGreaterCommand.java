import java.io.Serializable;
import java.util.*;

public class RemoveGreaterCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;
    Vehicle newVehicle;

    public RemoveGreaterCommand(VehiclesCollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
        this.newVehicle = CreateNewVehicle.create();

        ArrayList<Vehicle> newCollection = new ArrayList<>();
        while (!this.collectionKeeper.getCollection().isEmpty()){
            Vehicle polled = this.collectionKeeper.getCollection().poll();
            if (polled != null) {
                if (polled.compareTo(this.newVehicle) < 0) {
                    newCollection.add(polled);
                }
                else {
                    newCollection.add(newVehicle);
                    break;
                }
            }
        }
        collectionKeeper.getCollection().clear();
        newCollection.forEach(v -> collectionKeeper.getCollection().add(v));
    }

    @Override
    public void execute(){
        /*
        ArrayList<Vehicle> newCollection = new ArrayList<>();
        while (!this.collectionKeeper.getCollection().isEmpty()){
            Vehicle polled = this.collectionKeeper.getCollection().poll();
            if (polled != null) {
                if (polled.compareTo(this.newVehicle) < 0) {
                    newCollection.add(polled);
                }
                else {
                    newCollection.add(newVehicle);
                    break;
                }
            }
        }

         */
        System.out.println("Элементы превышающие заданный были удалены из коллекции");
        //collectionKeeper.getCollection().clear();
        //newCollection.forEach(v -> collectionKeeper.getCollection().add(v));
    }
}
