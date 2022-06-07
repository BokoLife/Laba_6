import java.io.IOException;
import java.net.InetAddress;

public class TestNIO {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        ServerNIO_Selector server = new ServerNIO_Selector(host.getHostName(), 6789);
        server.start();
    }
}
