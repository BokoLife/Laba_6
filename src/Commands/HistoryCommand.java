package Commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class HistoryCommand implements Command, Serializable {
    VehiclesCollectionKeeper collectionKeeper;
    private String[] history;

    public HistoryCommand(ArrayList<String> history){
        this.history = history.toArray(new String[0]);
    }

    @Override
    public void execute(){
        try {
            if (this.history.length >= 7) {
                for (int i = this.history.length - 7; i < this.history.length; i++) {
                    System.out.println(this.history[i]);
                }
            } else {
                Arrays.stream(this.history).forEach(System.out::println);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("В истории меньше 7 команд");
        }
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collectionKeeper = new VehiclesCollectionKeeper(collection);
    }
}
