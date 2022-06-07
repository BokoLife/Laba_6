import java.io.Serializable;

public class ShowCommand implements Command, Serializable{
    private final VehiclesCollectionKeeper collectionKeeper;

    public ShowCommand(VehiclesCollectionKeeper collection){
        this.collectionKeeper = collection;
    }
    @Override
    public void execute(){
        if (collectionKeeper.getCollection().isEmpty()){
            System.out.println("Коллекция пуста");
        } else {
            collectionKeeper.getCollection().forEach(System.out::println);
        }
    }

    @Override
    public String toString() {
        return "ShowCommand";
    }
}
