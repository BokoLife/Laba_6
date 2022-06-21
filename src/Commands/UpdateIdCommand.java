package Commands;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

public class UpdateIdCommand implements Command, UseId, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    private Long id;
    private String argument;
    private Vehicle newVehicle;
    private boolean idChecker = true;
    private boolean updateChecker;
    private int userId;

    public UpdateIdCommand(String argument) {
        try {
            this.id = Long.parseLong(argument);
            this.newVehicle = CreateNewVehicle.create();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверно введен аргумент команды: " + this.argument + "\n" +
                    "Введите число");
        }
    }

    @Override
    public void execute() {
        try {
            //this.id = Long.parseLong(this.argument);
            List<Vehicle> vehList = new ArrayList<>(Arrays.asList(collectionKeeper.getCollection().toArray(new Vehicle[0])));
            if (!vehList.removeIf(vehicle -> vehicle.getId().equals(this.id))) {
                System.out.println("Элемента с таким id нет в коллекции");
            } else {
                if (this.userId == DataBase.getOwnerId(Integer.parseInt(this.id.toString()))) {
                    this.newVehicle.setId(this.id);
                    collectionKeeper.getCollection().clear();
                    vehList.forEach(this.collectionKeeper::add);
                    collectionKeeper.getCollection().add(newVehicle);
                    DataBase.removeById(Integer.parseInt(this.id.toString()));
                    DataBase.addVehicleWithId(this.newVehicle, this.userId);
                    System.out.println("Значения элемента с id: " + this.id + " были изменены");
                } else {
                    System.out.println("У Вас нет права изменять данный объект");
                }
            }
//            List<Vehicle> veh = this.collectionKeeper.getCollection().stream()
//                    .filter(v -> v.getId().equals(this.id))
//                    .collect(Collectors.toList());
//            if (veh.isEmpty()) {
//                throw new IOException("Элемента с таким id нет в коллекции");
//            }
        } catch (NumberFormatException e) {
            System.out.println("Неверно введен аргумент команды: " + this.argument + "\n" +
                    "Введите число");
            this.idChecker = false;
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
