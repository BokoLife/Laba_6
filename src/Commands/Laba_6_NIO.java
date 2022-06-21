package Commands;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Laba_6_NIO {
    public static String[] userInputMas = new String[2];
    public static ArrayList<String> commandsHistory = new ArrayList<>(7);
    public static CommandsMap commands;

    public static void main(String[] args) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        int port = 7777;
        SocketChannel socket;
        Scanner scan = new Scanner(System.in);

        //VehiclesCollectionKeeper vehicles = new VehiclesCollectionKeeper(ReadFile.getCollectionFromFile());
        //VehiclesCollectionKeeper vehicles = new VehiclesCollectionKeeper(GetCollectionFromDB.getCollection());
        commands = new CommandsMap();

        while(true) {
            try {
                socket = SocketChannel.open();
                socket.connect(new InetSocketAddress(host, port));
                socket.configureBlocking(true);
                System.out.println("Подключение с сервером установлено");

                while (true) {
                    //чтение
                    ByteBuffer buf = ByteBuffer.allocate(2048);
                    int read = socket.read(buf);
                    if (read > 0) {
                        byte[] readData = new byte[read];
                        System.arraycopy(buf.array(), 0, readData, 0, read);
                        String readString = new String(readData);
                        System.out.println(readString);
                        if (readString.equals("Вход успешен \n" + "Введите команду:")) {
                            while (true) {
                                try {
                                    ByteBuffer buf3 = ByteBuffer.allocate(2048);
                                    String userInput = scan.nextLine();
                                    userInputMas = userInput.split(" ", 3);
                                    String command_name = GetCommand.getCommand();
                                    if (commands.getKeys().contains(command_name)) {
                                        commandsHistory.add(command_name);
                                        Command command = commands.get(command_name);

                                        buf3.put(convertObjectToBytes(command));
                                        buf3.flip();
                                        socket.write(buf3);
                                        //System.out.println(socket.write(buf3));
                                        buf3.clear();

                                        Thread.sleep(1000);

                                        ByteBuffer readBuf = ByteBuffer.allocate(2048);
                                        int readed = socket.read(readBuf);
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
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    buf.clear();

                    //отправка
                    ByteBuffer buf1 = ByteBuffer.allocate(2048);
                    String choice = scan.nextLine();
                    buf1.put(choice.getBytes(StandardCharsets.UTF_8));
                    buf1.flip();
                    socket.write(buf1);
                    buf1.clear();
                }
            } catch (IOException e) {
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