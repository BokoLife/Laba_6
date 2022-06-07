import java.io.Serializable;

public class GreaterFuelType implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;
    String stringFuelType;

    public GreaterFuelType(VehiclesCollectionKeeper collectionKeeper, String argument) {
        this.collectionKeeper = collectionKeeper;
        this.stringFuelType = argument;
    }

    @Override
    public void execute() {
        if (this.collectionKeeper.getCollection().isEmpty()){
            System.out.println("Коллекция пуста");
        } else {
            try {
                this.collectionKeeper.getCollection()
                        .forEach(v -> {
                            if (v.getFuelType().compareTo(FuelType.valueOf(stringFuelType)) > 0)
                                System.out.println(v);
                        });
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Введено значение не из списка констант");
                for (FuelType value : FuelType.values()) {
                    System.out.println(value);
                }
            }
        }
    }
}
