import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintEnginePowerCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;
    public PrintEnginePowerCommand(VehiclesCollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

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
}
