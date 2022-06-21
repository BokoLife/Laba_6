package Commands;

import java.io.Serializable;
import java.util.List;
import java.util.PriorityQueue;

public class PrintAscendingCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;

    @Override
    public void execute(){
        if (this.collectionKeeper.getCollection().isEmpty()){
            System.out.println("Коллекция пуста");
        } else {
            List<Vehicle> newList = this.collectionKeeper.getCollection().stream().toList();
            newList.stream().sorted().forEach(System.out::println);
        }
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collectionKeeper = new VehiclesCollectionKeeper(collection);
    }
}
