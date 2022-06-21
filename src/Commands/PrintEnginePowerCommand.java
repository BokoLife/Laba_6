package Commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class PrintEnginePowerCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;

    @Override
    public void execute(){
        if (this.collectionKeeper.getCollection().isEmpty()){
            System.out.println("Коллекция пуста");
        } else {
            Vehicle[] vehicles = collectionKeeper.getCollection().toArray(new Vehicle[0]);
            List<Vehicle> vehiclesList = new ArrayList<>(Arrays.asList(vehicles));
            vehiclesList.sort(new EngineComparator());
            for (Vehicle vehicle : vehiclesList) {
                System.out.println(vehicle.getId() + " " + vehicle.getName() + " " + vehicle.getEnginePower());
            }
        }
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collectionKeeper = new VehiclesCollectionKeeper(collection);
    }
}
