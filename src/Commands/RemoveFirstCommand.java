package Commands;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class RemoveFirstCommand implements Command, UseId, Serializable {
    VehiclesCollectionKeeper collectionKeeper;
    private boolean removeChecker;
    private Long removedId;
    private int userId;

    @Override
    public void execute() {
//        if (removeChecker){
//            System.out.println("Элемент с id " + this.removedId + " был удален");
//        } else{
//            System.out.println("Коллекция пуста");
//        }
        try{
            Vehicle removedVehicle = this.collectionKeeper.getCollection().remove();
            this.removedId = removedVehicle.getId();
            if (this.userId == DataBase.getOwnerId(Integer.parseInt(this.removedId.toString()))) {
                //this.removedId = this.collectionKeeper.getCollection().remove().getId();
                DataBase.removeById(Integer.parseInt(this.removedId.toString()));
                System.out.println("Элемент с id " + this.removedId + " был удален");
                //removeChecker = true;
            } else{
                this.collectionKeeper.getCollection().add(removedVehicle);
                System.out.println("Первый элемент не принадлежит Вам");
            }
        }
        catch(NoSuchElementException e) {
            System.out.println("Коллекция пуста");
            //removeChecker = false;
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
