import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class Laba_6_NIO {
    public static String[] userInputMas = new String[2];
    public static ArrayList<String> commandsHistory = new ArrayList<>(7);
    public static CommandsMap commands;

    public static void main(String[] args) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        int port = 6789;
        SocketChannel socketChannel;

        VehiclesCollectionKeeper vehicles = new VehiclesCollectionKeeper(ReadFile.getCollectionFromFile());
        commands = new CommandsMap(vehicles);

        while (true) {
            try {
                socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(host, port));
                socketChannel.configureBlocking(false);
                System.out.println("Подключение с сервером установлено");

                while (true) {
                    try {
                        ByteBuffer buf = ByteBuffer.allocate(2048);
                        Scanner scan = new Scanner(System.in);
                        String userInput = scan.nextLine();
                        userInputMas = userInput.split(" ", 3);
                        String command = GetCommand.getCommand();
                        if (commands.getKeys().contains(command)) {
                            commandsHistory.add(command);
                            buf.put(convertObjectToBytes(commands.get(command)));
                            buf.flip();
                            System.out.println(socketChannel.write(buf));
                            buf.clear();

                            Thread.sleep(1000);
                            ByteBuffer readBuf = ByteBuffer.allocate(2048);
                            int readed = socketChannel.read(readBuf);
                            if (readed > 0) {
                                System.out.println("Получено байт от сервера " + readed);
                                byte[] data = new byte[readed];
                                System.arraycopy(readBuf.array(), 0, data, 0, readed);
                                String answ = new String(data);
                                readBuf.clear();
                                System.out.println(answ);
                            }
                        } else {
                            System.out.println("Было введено значение не из списка команд");
                        }
                        userInputMas = new String[2];
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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

    public static byte[] convertObjectToBytes(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }
    public static ByteBuffer toBuffer(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.flush();
        ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
        oos.close();
        baos.close();
        return buffer;
    }
    public static String deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        String s = (String) is.readObject();
        in.close();
        is.close();
        return s;
    }

    //    public static String getCommand() {
//        return userInputMas[0];
//    }
//    public static String getArgument() throws NullPointerException{
//        if (userInputMas.length > 1) {
//            return userInputMas[1];
//        } else {
//            throw new NullPointerException ("Аргумент команды не был введен");
//            //return null;
//        }
//    }
}

class GetCommand {
    public static String getCommand() {
        return Laba_6_NIO.userInputMas[0];
    }
}
class GetArgument {
    public static String getArgument() throws NullPointerException {
        if (Laba_6_NIO.userInputMas.length > 1) {
            return Laba_6_NIO.userInputMas[1];
        } else {
            throw new NullPointerException("Аргумент команды не был введен");
            //return null;
        }
    }
}
class GetCommandsHistory {
    public static ArrayList<String> getCommandsHistory() {
        return Laba_6_NIO.commandsHistory;
    }
}
class GetCommandsMap {
    public static CommandsMap getCommandsMap() {
        return Laba_6_NIO.commands;
    }
}