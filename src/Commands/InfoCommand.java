package Commands;

import java.io.Serializable;
import java.util.PriorityQueue;

public class InfoCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;

    @Override
    public void execute() {
        String queueType = collectionKeeper.getCollection().getClass().toString();
        String replace = "class java.util.";
        queueType = queueType.replace(replace, "");
        System.out.println("Тип коллекции: " + queueType
                + " Дата создания: " +  this.collectionKeeper.getDATE()
                + " Количество элементов: " + this.collectionKeeper.getCollection().size());
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collectionKeeper = new VehiclesCollectionKeeper(collection);
    }
}
