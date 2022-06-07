import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateNewVehicle {
    public static Vehicle create(){
        Vehicle newVehicle = new Vehicle();
        Scanner scan = new Scanner(System.in);

        System.out.println("Введите имя транспорта:");
        String name;
        while (true) {
            if (!scan.hasNextInt()){
                name = scan.next();
                break;
            }
            else if (scan.hasNextInt()){
                scan.next();
                System.out.println("Введена не строка");
            }
        }
        newVehicle.setName(name);

        System.out.println("Введите координату x (макс значение: 956)");
        Integer x;
        while (true){
            if (scan.hasNextInt()){
                x = scan.nextInt();
                break;
            }
            else if (!scan.hasNextInt()){
                scan.next();
                System.out.println("Введено не число");
            }
        }
        System.out.println("Введите координату y (макс значение: 368)");
        int y;
        while (true){
            if (scan.hasNextInt()){
                y = scan.nextInt();
                break;
            }
            else if (!scan.hasNextInt()){
                scan.next();
                System.out.println("Введено не число");
            }
        }
        newVehicle.setCoordinates(new Coordinates(x, y));

        System.out.println("Введите значение мощности (больше 0)");
        Integer enginePower;
        while (true){
            if (scan.hasNextInt()){
                enginePower = scan.nextInt();
                break;
            }
            else if (!scan.hasNextInt()){
                scan.next();
                System.out.println("Введено не число");
            }
        }
        newVehicle.setEnginePower(enginePower);

        System.out.println("Введите тип транспорта из списка:");
        VehicleType [] vehicleTypes = VehicleType.values();
        List<String> vehicleTypesList = new ArrayList<>();
        for (VehicleType v : vehicleTypes){
            vehicleTypesList.add(v.toString());
            System.out.println(v);
        }

        String vehicleType;
        while (true) {
            if (!scan.hasNextInt()){
                String in = scan.nextLine();
                if (vehicleTypesList.contains(in)){
                    vehicleType = in;
                    break;
                }
                else {
                    //scan.next();
                    System.out.println("Введено значение не из списка констант");
                }
            }
            //else if (scan.hasNextInt())
            else {
                scan.nextInt();
                System.out.println("Введена не строка");
            }
        }
        newVehicle.setType(VehicleType.valueOf(vehicleType));

        System.out.println("Введите тип топлива из списка:");
        FuelType [] vehicleFuelTypes = FuelType.values();
        List<String> vehicleFuelTypesList = new ArrayList<>();
        for (FuelType v : vehicleFuelTypes){
            vehicleFuelTypesList.add(v.toString());
            System.out.println(v);
        }

        String vehicleFuelType;
        while (true) {
            if (!scan.hasNextInt()){
                String in = scan.nextLine();
                if (vehicleFuelTypesList.contains(in)){
                    vehicleFuelType = in;
                    break;
                }
                else {
                    //scan.nextLine();
                    System.out.println("Введено значение не из списка констант");
                }
            }
            else if (scan.hasNextInt()){
                scan.nextInt();
                System.out.println("Введена не строка");
            }
        }
        newVehicle.setFuelType(FuelType.valueOf(vehicleFuelType));

        return newVehicle;
    }
}
