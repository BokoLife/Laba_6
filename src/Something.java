import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Something {
    public static void main(String[] args) throws IOException{

        Scanner scan = new Scanner(System.in);
        String in = scan.nextLine();
        try {
            Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("uuummm");
        }

        File file = new File("58");

//        PriorityQueue<Vehicle> collection = new PriorityQueue<>();
//
//        Vehicle car = new Vehicle("Машина", new Coordinates(25, 329), 325, VehicleType.CAR, FuelType.ALCOHOL);
//        Vehicle helicopter = new Vehicle("Вертик 1", new Coordinates(500, 180), 1253, VehicleType.HELICOPTER, FuelType.ANTIMATTER);
//        Vehicle ship = new Vehicle("Аврора", new Coordinates(500, 200), 2343, VehicleType.SHIP, FuelType.NUCLEAR);
//
//        collection.add(car);
//        collection.add(helicopter);
//        collection.add(ship);
//
//        collection.forEach(System.out::println);
//        collection.stream()
//                .filter(v -> v.getId().equals(car.getId()))
//                .forEach(v -> collection.poll());
//        collection.forEach(System.out::println);


//        File commandsFile = new File("commands.txt");
//
//        String fileName = "commands.txt";
//        Path path = Paths.get(fileName);
//
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(commandsFile);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//
//            List lines = Files.readAllLines(path);
//            for (Object line1 : lines) {
//                String line = (String) line1;
//                if (Laba_5.getCommandsMap().getKeys().contains(line)) {
//                    Laba_5.getCommandsMap().get(line).execute();
//                } else {
//                    System.out.println("Было введено значение не из списка команд");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //fis.close();

//        try {
//            PriorityQueue<Vehicle> vehicles = getQueueFromFile();
//            VehiclesCollectionKeeper collectionKeeper = new VehiclesCollectionKeeper(vehicles);
//            new ShowCommand(collectionKeeper).execute();
//        } catch (ParserConfigurationException ex) {
//            ex.printStackTrace(System.out);
//        } catch (SAXException ex) {
//            ex.printStackTrace(System.out);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.out);
//        }
//    }

//    private static PriorityQueue<Vehicle> getQueueFromFile() throws ParserConfigurationException, IOException, SAXException {
//        // Строитель документа
//        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        // Дерево DOM документа из файла
//        Document document = documentBuilder.parse("VehicleCatalog.xml");
//
//        Node vehicleNode = document.getFirstChild(); //VehicleCatalogue
//        //System.out.println(vehicleNode.getNodeName());
//
//        PriorityQueue<Vehicle> vehiclesQueue = new PriorityQueue<>();
//
//        NodeList vehicleChilds = vehicleNode.getChildNodes(); //Vehicle
//        for (int i = 0; i < vehicleChilds.getLength(); i++){
//            Vehicle vehicle = new Vehicle();
//
//            if (vehicleChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
//                continue;
//            }
//            //System.out.println(vehicleChilds.item(i).getNodeName()); // Vehicle
//
//            if (!vehicleChilds.item(i).getNodeName().equals("Vehicle")){
//                continue;
//            }
//
//            NodeList vehicleFields = vehicleChilds.item(i).getChildNodes();
//            //Поля Id, Name...
//            for (int j = 0; j < vehicleFields.getLength(); j++){
//                if (vehicleFields.item(j).getNodeType() != Node.ELEMENT_NODE){
//                    continue;
//                }
//
//                switch (vehicleFields.item(j).getNodeName()){
//                    case "Id":{
//                        vehicle.setId(Long.parseLong(vehicleFields.item(j).getTextContent()));
//                        break;
//                    }
//                    case "Name":{
//                        vehicle.setName(vehicleFields.item(j).getTextContent());
//                        break;
//                    }
//                    case "Coordinates":{
//                        String[] coordinates = vehicleFields.item(j).getTextContent().split(",");
//                        Integer x = Integer.parseInt(coordinates[0]);
//                        int y = Integer.parseInt(coordinates[1]);
//                        vehicle.setCoordinates(new Coordinates(x, y));
//                        break;
//                    }
//                    case "CreationDate":{
//                        //System.out.println(vehicleFields.item(j).getTextContent());
//                        Date date = new Date(Long.parseLong(vehicleFields.item(j).getTextContent()));
//                        vehicle.setCreationDate(date);
//                        break;
//                    }
//                    case "EnginePower":{
//                        vehicle.setEnginePower(Integer.parseInt(vehicleFields.item(j).getTextContent()));
//                        break;
//                    }
//                    case "Type":{
//                        vehicle.setType(VehicleType.valueOf(vehicleFields.item(j).getTextContent()));
//                        break;
//                    }
//                    case "FuelType":{
//                        vehicle.setFuelType(FuelType.valueOf(vehicleFields.item(j).getTextContent()));
//                        break;
//                    }
//                }
//            }
//            vehiclesQueue.add(vehicle);
//        }
//        return vehiclesQueue;
//    }
    }
}
