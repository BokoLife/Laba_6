package Commands;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Something {
    public static void main(String[] args) throws IOException{

//        Queue<Integer> q = new LinkedList<>();
//        q.add(4); q.add(3); q.add(2); q.add(1); q.remove(); q.add(2);
//        q.stream().forEach(System.out::println);

        // libra   scorpio  capricorn   aquarius   pisces
        // lba   sopo  cpion   auru   pse
        // lba   sopo  cpion

        Stream.of("lba", "sopo", "cpion")
                .filter(s -> s.length() <= 9)
                .sorted()
                .forEachOrdered(System.out::print);

    }
}
