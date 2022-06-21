package Commands;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.PriorityQueue;

public class ClearCommand implements Command, UseId, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    private int userId;

    @Override
    public void execute() {
        try {
            if (DataBase.clearByUserId(this.userId)) {
                this.collectionKeeper = new VehiclesCollectionKeeper(DataBase.getCollection());
                System.out.println("Из коллекции были удалены все элементы, принадлежащие Вам");
            } else{
                System.out.println("В коллекции нет ваших элементов");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
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
}
