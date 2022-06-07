import java.io.IOException;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UpdateIdCommand implements Command, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    private Long id;
    private String argument;
    private Vehicle newVehicle;
    private boolean idChecker = true;
    private boolean updateChecker;

    public UpdateIdCommand(VehiclesCollectionKeeper collectionKeeper, String argument) {
        this.collectionKeeper = collectionKeeper;
        this.argument = argument;

        try{
            this.id = Long.parseLong(this.argument);
            List<Vehicle> veh = this.collectionKeeper.getCollection().stream()
                    .filter(v -> v.getId().equals(this.id))
                    .collect(Collectors.toList());
            if (veh.isEmpty()){
                throw new IOException();
            }
        } catch (NumberFormatException e){
            System.out.println("Неверно введен аргумент команды: " + this.argument + "\n" +
                    "Введите число");
            this.idChecker = false;
        } catch (IOException e){
            //System.out.println("Элемента с таким id нет в коллекции");
            this.idChecker = false;
        }

        if (idChecker) {
            this.newVehicle = CreateNewVehicle.create();
            this.newVehicle.setId(this.id);

            try {
                Vehicle[] veh = collectionKeeper.getCollection().toArray(new Vehicle[0]);
                List<Vehicle> vehList = new ArrayList<>(Arrays.asList(veh));
                vehList.removeIf(vehicle -> vehicle.getId().equals(this.id));
                if (veh.length == vehList.size()) {
                    this.updateChecker = false;
                }
                else {
                    collectionKeeper.getCollection().clear();
                    vehList.forEach(this.collectionKeeper::add);
                    collectionKeeper.getCollection().add(newVehicle);
                    updateChecker = true;
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Неверно введен аргумент команды: " + this.argument + "\n" +
                        "Введите число");
            }
        }
    }

    @Override
    public void execute(){
        if (updateChecker){
            System.out.println("Значения элемента с id: " + this.id + " были изменены");
        } else {
            System.out.println("Элемента с таким id нет в коллекции");
        }
    }
}
