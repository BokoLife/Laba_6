import java.io.Serializable;
import java.util.List;

public class PrintAscendingCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;

    public PrintAscendingCommand(VehiclesCollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    public void execute(){
        if (this.collectionKeeper.getCollection().isEmpty()){
            System.out.println("Коллекция пуста");
        } else {
            List<Vehicle> newList = this.collectionKeeper.getCollection().stream().toList();
            newList.stream().sorted().forEach(System.out::println);
        }
    }
}
