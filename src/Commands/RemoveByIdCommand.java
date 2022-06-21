package Commands;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class RemoveByIdCommand implements Command, UseId, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    private Long id;
    private int userId;
    private String argument;

    public RemoveByIdCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute() {
        try {
            this.id = Long.parseLong(this.argument);
            if (this.userId == DataBase.getOwnerId(Integer.parseInt(this.id.toString()))) {
                Vehicle[] veh = collectionKeeper.getCollection().toArray(new Vehicle[0]);
                List<Vehicle> vehList = new ArrayList<>(Arrays.asList(veh));
                vehList.removeIf(vehicle -> vehicle.getId().equals(this.id));
                if (veh.length == vehList.size()) {
                    //removeChecker = false;
                    System.out.println("Элемента с таким id нет в коллекции");
                } else {
                    collectionKeeper.getCollection().clear();
                    vehList.forEach(this.collectionKeeper::add);
                    if (DataBase.removeById(Integer.parseInt(this.id.toString()))) {
                        //removeChecker = true;
                        System.out.println("Элемент " + this.id + " был удален");
                    }
                }
            } else{
                System.out.println("У Вас нет права изменять данный объект");
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверно введен аргумент команды: " + this.argument + "\n" +
                    "Введите число");
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

    public void setId(Long id) {
        this.id = id;
    }
}
