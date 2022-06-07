import java.io.*;
import java.util.*;

public class ByteWriter {
    public static void main(String[]args){
        Queue<Vehicle> vehicles = new PriorityQueue<Vehicle>();

        Vehicle car = new Vehicle("Машина", new Coordinates(25, 329), 325, VehicleType.CAR, FuelType.ALCOHOL);
        Vehicle helicopter = new Vehicle("Вертик 1", new Coordinates(500, 180), 1253, VehicleType.HELICOPTER, FuelType.ANTIMATTER);
        Vehicle ship = new Vehicle("Аврора", new Coordinates(500, 200), 2343, VehicleType.SHIP, FuelType.NUCLEAR);
        Vehicle bike = new Vehicle("Велик", new Coordinates(202, 59), 10, VehicleType.CAR, FuelType.MANPOWER);

        vehicles.add(car);
        vehicles.add(helicopter);
        vehicles.add(ship);
        vehicles.add(bike);

        System.out.println(vehicles);

        try {
            FileOutputStream fos = new FileOutputStream("vehicles1.xml");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(car);
            oos.writeObject(helicopter);
            oos.writeObject(ship);
            oos.writeObject(bike);
            fos.close();
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
