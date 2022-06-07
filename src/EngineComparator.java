import java.util.Comparator;

public class EngineComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle veh1, Vehicle veh2) {
        return veh2.getEnginePower() - veh1.getEnginePower();
    }
}
