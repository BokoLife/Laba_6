package Commands;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

public class AddCommand implements Command, UseId, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    private Vehicle newVehicle;
    private Long id;
    private int userId;

    public AddCommand() {
        this.newVehicle = CreateNewVehicle.create();
    }

    @Override
    public void execute(){
        try {
            if(DataBase.addVehicle(this.newVehicle, this.userId)) {
                this.collectionKeeper = new VehiclesCollectionKeeper(DataBase.getCollection());
                System.out.println("Новый элемент добавлен в коллекцию");
                //this.collectionKeeper.getCollection().add(newVehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
        //collection = this.collectionKeeper.getCollection();
        this.collectionKeeper = new VehiclesCollectionKeeper(collection);
    }

    @Override
    public PriorityQueue<Vehicle> getCollection(){
        return this.collectionKeeper.getCollection();
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Vehicle getNewVehicle(){
        return newVehicle;
    }
    public void setId(Long id){
        this.id = id;
    }
}
