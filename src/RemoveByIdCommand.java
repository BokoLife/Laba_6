import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveByIdCommand implements Command, Serializable {
    private final VehiclesCollectionKeeper collectionKeeper;
    private Long id;
    private String argument;
    private boolean removeChecker;

    public RemoveByIdCommand(VehiclesCollectionKeeper collectionKeeper, String argument) {
        this.collectionKeeper = collectionKeeper;
        this.argument = argument;

        try {
            this.id = Long.parseLong(this.argument);
            Vehicle[] veh = collectionKeeper.getCollection().toArray(new Vehicle[0]);
            List<Vehicle> vehList = new ArrayList<>(Arrays.asList(veh));
            vehList.removeIf(vehicle -> vehicle.getId().equals(this.id));
            if (veh.length == vehList.size()) {
                removeChecker = false;
            } else {
                collectionKeeper.getCollection().clear();
                vehList.forEach(this.collectionKeeper::add);
                removeChecker = true;
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверно введен аргумент команды: " + this.argument + "\n" +
                    "Введите число");
        }
    }

    @Override
    public void execute() {
        if (!this.removeChecker) {
            System.out.println("Элемента с таким id нет в коллекции");
        } else {
            System.out.println("Элемент " + this.id + " был удален");
        }
    }

    public boolean removeByIdChecker(Long id) {
        return removeChecker;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
