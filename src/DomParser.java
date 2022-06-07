import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.PriorityQueue;

public class DomParser {
    private File file;

    public DomParser(File file) {
        this.file = file;
    }

    public static PriorityQueue<Vehicle> ParseFile(File file) {
        PriorityQueue<Vehicle> vehicles = null;
        try {
            vehicles = getQueueFromFile(file);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            System.out.println("Имя файла задано неверно");
        }
        return vehicles;
    }

    public static PriorityQueue<Vehicle> getQueueFromFile(File file) throws IOException, SAXException, ParserConfigurationException, FileNotFoundException {
        // Строитель документа
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Дерево DOM документа из файла
        //file = new File("VehicleCatalog.xml");
        Document document = documentBuilder.parse(file);

        Node vehicleNode = document.getFirstChild(); //VehicleCatalogue
        //System.out.println(vehicleNode.getNodeName());

        PriorityQueue<Vehicle> vehiclesQueue = new PriorityQueue<>();

        NodeList vehicleChilds = vehicleNode.getChildNodes(); //Vehicle
        for (int i = 0; i < vehicleChilds.getLength(); i++) {
            Vehicle vehicle = new Vehicle();

            if (vehicleChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            //System.out.println(vehicleChilds.item(i).getNodeName()); // Vehicle

            if (!vehicleChilds.item(i).getNodeName().equals("Vehicle")) {
                continue;
            }

            NodeList vehicleFields = vehicleChilds.item(i).getChildNodes();
            //Поля Id, Name...
            for (int j = 0; j < vehicleFields.getLength(); j++) {
                if (vehicleFields.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                switch (vehicleFields.item(j).getNodeName()) {
                    case "Id": {
                        vehicle.setId(Long.parseLong(vehicleFields.item(j).getTextContent()));
                        break;
                    }
                    case "Name": {
                        vehicle.setName(vehicleFields.item(j).getTextContent());
                        break;
                    }
                    case "Coordinates": {
                        String[] coordinates = vehicleFields.item(j).getTextContent().split(",");
                        Integer x = Integer.parseInt(coordinates[0]);
                        int y = Integer.parseInt(coordinates[1]);
                        vehicle.setCoordinates(new Coordinates(x, y));
                        break;
                    }
                    case "CreationDate": {
                        //System.out.println(vehicleFields.item(j).getTextContent());
                        Date date = new Date(Long.parseLong(vehicleFields.item(j).getTextContent()));
                        vehicle.setCreationDate(date);
                        break;
                    }
                    case "EnginePower": {
                        vehicle.setEnginePower(Integer.parseInt(vehicleFields.item(j).getTextContent()));
                        break;
                    }
                    case "Type": {
                        vehicle.setType(VehicleType.valueOf(vehicleFields.item(j).getTextContent()));
                        break;
                    }
                    case "FuelType": {
                        vehicle.setFuelType(FuelType.valueOf(vehicleFields.item(j).getTextContent()));
                        break;
                    }
                }
            }
            vehiclesQueue.add(vehicle);
        }
        return vehiclesQueue;
    }

    public static void addNewVehicle(Document document, Vehicle vehicle1, File file) throws DOMException, TransformerFactoryConfigurationError {
        // Получаем корневой элемент
        Node root = document.getDocumentElement();

        // Сам транспорт <Vehicle>
        Element vehicle = document.createElement("Vehicle");
        // <ID>
        Element id = document.createElement("Id");
        id.setTextContent(vehicle1.getId().toString());
        // <Name>
        Element name = document.createElement("Name");
        name.setTextContent(vehicle1.getName());
        // <Coordinates>
        Element coordinates = document.createElement("Coordinates");
        coordinates.setTextContent(vehicle1.getCoordinates().toString());
        // <CreationDate>
        Element date = document.createElement("CreationDate");
        date.setTextContent(vehicle1.getCreationDate().toString());
        // <EnginePower>
        Element enginePower = document.createElement("EnginePower");
        enginePower.setTextContent(vehicle1.getEnginePower().toString());
        // <Type>
        Element type = document.createElement("Type");
        type.setTextContent(vehicle1.getType().toString());
        // <FuelType>
        Element fuelType = document.createElement("FuelType");
        fuelType.setTextContent(vehicle1.getFuelType().toString());

        // Добавляем внутренние элементы транспорта в элемент <Vehicle>
        vehicle.appendChild(id);
        vehicle.appendChild(name);
        vehicle.appendChild(coordinates);
        vehicle.appendChild(date);
        vehicle.appendChild(enginePower);
        vehicle.appendChild(type);
        vehicle.appendChild(fuelType);
        // Добавляем транспорт в корневой элемент
        root.appendChild(vehicle);

        // Записываем XML в файл
        writeDocument(document, file);
    }

    // Функция для сохранения DOM в файл
    public static void writeDocument(Document document, File file) {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);

            //String fileName = "VehicleCatalog.xml";
            FileWriter fw = new FileWriter(file);

            StreamResult result = new StreamResult(fw);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
