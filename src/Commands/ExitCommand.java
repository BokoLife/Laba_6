package Commands;

import java.io.Serializable;
import java.util.PriorityQueue;

public class ExitCommand implements Command, Serializable {
    @Override
    public void execute() {
        System.out.println("Программа завершена");
        //System.exit(1);
    }

    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {
    }
}
