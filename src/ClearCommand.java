import java.io.Serializable;

public class ClearCommand implements Command, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;

    public ClearCommand(VehiclesCollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
        this.collectionKeeper.getCollection().clear();
    }
    @Override
    public void execute() {
        System.out.println("Из коллекции были удалены все элементы");
    }
}
