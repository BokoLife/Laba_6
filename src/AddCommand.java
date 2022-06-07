import java.io.Serializable;
import java.util.*;

public class AddCommand implements Command, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    private Vehicle newVehicle;
    private Long id;

    public AddCommand(VehiclesCollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
        this.newVehicle = CreateNewVehicle.create();
        this.collectionKeeper.getCollection().add(newVehicle);
    }

    @Override
    public void execute(){
        //this.collectionKeeper.add(newVehicle);
        System.out.println("Новый элемент добавлен в коллецкию");
    }

    public Vehicle getNewVehicle(){
        return newVehicle;
    }
    public void setId(Long id){
        this.id = id;
    }
}
