import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.*;

public class SaveCommand implements Command, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    private String argument;

    public SaveCommand(VehiclesCollectionKeeper collectionKeeper, String argument) {
        this.collectionKeeper = collectionKeeper;
        this.argument = argument;
    }

    @Override
    public void execute() {
        // Создается построитель документа
        DocumentBuilder documentBuilder = null;
        Document document = null;

        try {
            String fileName = this.argument;
            File file = new File(fileName);
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                document = documentBuilder.parse(file);

                for (Vehicle veh : this.collectionKeeper.getCollection()){
                    DomParser.addNewVehicle(document, veh, file);
                }
                System.out.println("Коллекция была сохранена в файл " + fileName);
            } catch (ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Имя файла задано неверно");
            }
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
}
