package Commands;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        int port = 777;

        ServerSocket serv = new ServerSocket(port);
        Socket socket = serv.accept();

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
            Command command = (Command) ois.readObject();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
            //PrintStream old = System.out;

// Tell Java to use your special stream
            System.setOut(ps);
// Print some output: goes to your special stream
// Put things back
            command.execute();
            System.out.flush();

            oos.writeObject(baos.toString());
            //String wr = "Команда выполнена";
        }
    }
}
