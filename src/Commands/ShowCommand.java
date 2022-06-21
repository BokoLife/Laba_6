package Commands;

import java.io.Serializable;
import java.util.PriorityQueue;

public class ShowCommand implements Command, Serializable{
    private VehiclesCollectionKeeper collectionKeeper;

    @Override
    public void execute(){
        if (collectionKeeper.getCollection().isEmpty()){
            System.out.println("Коллекция пуста");
        } else {
            collectionKeeper.getCollection().forEach(System.out::println);
        }
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collectionKeeper = new VehiclesCollectionKeeper(collection);
    }

    @Override
    public String toString() {
        return "ShowCommand";
    }
}
