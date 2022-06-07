import java.io.File;
import java.util.Map;
import java.util.PriorityQueue;

public class ReadFile {

    public static PriorityQueue<Vehicle> getCollectionFromFile(){
        //Передаем имя файла из переменной окружения
        File file = new File(getEnvireFileName());  //file1 = vehicles1.xml
        PriorityQueue<Vehicle> vehicles1 = DomParser.ParseFile(file);
        return vehicles1;
    }

    public static String getEnvireFileName(){
        Map<String, String> envireVariables = System.getenv();
        String fileName = envireVariables.get("file1");    //file1 = vehicles1.xml
        return fileName;
    }
}
