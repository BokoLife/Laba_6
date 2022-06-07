import java.io.Serializable;

public class InfoCommand implements Command, Serializable {
    VehiclesCollectionKeeper collection;

    public InfoCommand(VehiclesCollectionKeeper collection){
        this.collection = collection;
    }
    @Override
    public void execute() {
        String queueType = collection.getCollection().getClass().toString();
        String replace = "class java.util.";
        queueType = queueType.replace(replace, "");
        System.out.println("Тип коллекции: " + queueType + " Дата создания: " +  this.collection.getDATE() + " Количество элементов: " + this.collection.getCollection().size());
    }
}
