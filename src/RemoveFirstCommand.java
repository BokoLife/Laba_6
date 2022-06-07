import java.io.Serializable;
import java.util.NoSuchElementException;

public class RemoveFirstCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;
    private boolean removeChecker;
    private Long removedId;

    public RemoveFirstCommand(VehiclesCollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
        try{
            this.removedId = this.collectionKeeper.getCollection().remove().getId();
            removeChecker = true;
        }
        catch(NoSuchElementException e) {
            removeChecker = false;
        }
    }

    @Override
    public void execute() {
        if (removeChecker){
            System.out.println("Элемент с id " + this.removedId + " был удален");
        } else{
            System.out.println("Коллекция пуста");
        }
    }
}
