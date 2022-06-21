package Commands;

import java.util.PriorityQueue;

public interface Command {
    void execute();
    void setCollection(PriorityQueue<Vehicle> collection);
}
