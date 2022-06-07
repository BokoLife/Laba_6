import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class Laba_6 {
    public static String[] userInputMas = new String[2];
    public static ArrayList<String> commandsHistory = new ArrayList<>(7);
    public static CommandsMap commands;

    public static void main(String[] args) throws IOException {
        Socket socket;
        InetAddress host = InetAddress.getLocalHost();
        int port = 777;

        VehiclesCollectionKeeper vehicles = new VehiclesCollectionKeeper(ReadFile.getCollectionFromFile());
        commands = new CommandsMap(vehicles);
        Scanner scan = new Scanner(System.in);

        while (true) {
            try {
                socket = new Socket(host, port);
                System.out.println("Подключение с сервером установлено");

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    String userInput = scan.nextLine();
                    userInputMas = userInput.split(" ", 3);
                    if (commands.getKeys().contains(getCommand())) {
                        oos.writeObject(commands.get(getCommand()));
                        commandsHistory.add(getCommand());

                        System.out.println(ois.readObject());
                    } else {
                        System.out.println("Было введено значение не из списка команд");
                    }
                    userInputMas = new String[2];
                }
            } catch (NullPointerException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e){
                System.out.println("Сервер временно недоступен, ожидание повторного подключения...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    /*
    catch(IOException e)
    {
        System.out.println("Сервер временно недоступен, ожидание повторного подключения...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

     */

    public static String getCommand() {
        return userInputMas[0];
    }

    public static String getArgument() throws NullPointerException {
        if (userInputMas.length > 1) {
            return userInputMas[1];
        } else {
            throw new NullPointerException("Аргумент команды не был введен");
            //return null;
        }
    }

    public static CommandsMap getCommandsMap() {
        return commands;
    }

    public static byte[] convertObjectToBytes(Object obj) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try {
            ObjectOutputStream ois2 = new ObjectOutputStream(boas);
            ois2.writeObject(obj);
            return boas.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }
}
