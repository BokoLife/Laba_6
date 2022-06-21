package Commands;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Server_NIO {
    public static ArrayList<String> commandsHistory = new ArrayList<>(7);

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 7777;

        PriorityQueue<Vehicle> db_collection = DataBase.getCollection();
        //CommandsMap commandsMap = new CommandsMap(new VehiclesCollectionKeeper(GetCollectionFromDB.getCollection()));

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            SocketChannel socket = serverSocketChannel.accept();
            socket.configureBlocking(false);

            Authorization a = new Authorization(socket);
            a.start();

            while (true) {
                if (a.isLoginChecker()) {
                    ByteBuffer buf = ByteBuffer.allocate(2048);
//                    while (true) {
                    try {
                        int read = socket.read(buf);
                        if (read > 0) {
                            byte[] data = new byte[read];
                            System.arraycopy(buf.array(), 0, data, 0, read);
                            Command desCommand = deserialize(data);
                            buf.clear();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
                            PrintStream old = System.out;

// Tell Java to use your special stream
                            System.setOut(ps);
// Print some output: goes to your special stream
// Put things back
                            desCommand.setCollection(db_collection);
                            desCommand.execute();
                            System.out.flush();

                            buf.put(baos.toByteArray());
                            buf.flip();
                            socket.write(buf);
                            buf.clear();
                        }
//            while (true) {
//                String choice = readClientString(socket);
//                System.out.println("Ответ пользователя " + choice);
//                if (choice.equals("Login") || choice.equals("login")) {
//                    login(socket);
//                    break;
//                } else if (choice.equals("Register") || choice.equals("register")) {
//                    register(socket);
//                    break;
//                } else {
//                    sendString("Было ведено неверное значение, введите Login or Register", socket);
//                }
                    } catch (SocketException e) {
                        System.out.println("Client left");
                        Thread.sleep(5000);
                    } catch (ClassNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
//                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Client left");
            //Thread.sleep(5000);
        }
    }

    public static void start(SocketChannel socket) {
        PriorityQueue<Vehicle> db_collection = DataBase.getCollection();
        //CommandsMap commandsMap = new CommandsMap(new VehiclesCollectionKeeper(GetCollectionFromDB.getCollection()));

        try {
            Authorization a = new Authorization(socket);
            a.start();

            while (true) {
                if (a.isLoginChecker()) {
                    ByteBuffer buf = ByteBuffer.allocate(2048);
//                    while (true) {
                    try {
                        int read = socket.read(buf);
                        if (read > 0) {
                            byte[] data = new byte[read];
                            System.arraycopy(buf.array(), 0, data, 0, read);
                            Command desCommand = deserialize(data);
                            buf.clear();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
                            PrintStream old = System.out;

// Tell Java to use your special stream
                            System.setOut(ps);
// Print some output: goes to your special stream
// Put things back
                            desCommand.setCollection(db_collection);
                            desCommand.execute();
                            System.out.flush();

                            buf.put(baos.toByteArray());
                            buf.flip();
                            socket.write(buf);
                            buf.clear();
                        }
//            while (true) {
//                String choice = readClientString(socket);
//                System.out.println("Ответ пользователя " + choice);
//                if (choice.equals("Login") || choice.equals("login")) {
//                    login(socket);
//                    break;
//                } else if (choice.equals("Register") || choice.equals("register")) {
//                    register(socket);
//                    break;
//                } else {
//                    sendString("Было ведено неверное значение, введите Login or Register", socket);
//                }
                    } catch (SocketException e) {
                        System.out.println("Client left");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (EOFException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client left");
            //Thread.sleep(5000);
        }
    }

        private static String readClientString (SocketChannel socket){
            ByteBuffer buf = ByteBuffer.allocate(2048);
            while (true) {
                try {
                    int read = socket.read(buf);
                    if (read > 0) {
                        byte[] readData = new byte[read];
                        System.arraycopy(buf.array(), 0, readData, 0, read);
                        return new String(readData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void sendString (String string, SocketChannel socket){
            ByteBuffer buf = ByteBuffer.allocate(2048);
            buf.put(string.getBytes(StandardCharsets.UTF_8));
            buf.flip();
            try {
                socket.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buf.clear();
        }

        public static void login (SocketChannel socket){
            ByteBuffer buf = ByteBuffer.allocate(2048);
            try {
                sendString("Введите логин:", socket);
                while (true) {
                    int read = socket.read(buf);
                    if (read > 0) {
                        byte[] readData = new byte[read];
                        System.arraycopy(buf.array(), 0, readData, 0, read);
                        String login = new String(readData);
                        System.out.println("Логин: " + login);
                        buf.clear();

                        if (DataBase.loginCheck(login)) {
                            String passwordRequest = ("Введите пароль:");
                            sendString(passwordRequest, socket);

                            while (true) {
                                int readPass = socket.read(buf);
                                if (readPass > 0) {
                                    byte[] readPassData = new byte[readPass];
                                    System.arraycopy(buf.array(), 0, readPassData, 0, readPass);
                                    String password = new String(readPassData);
                                    buf.clear();
                                    System.out.println("Пароль: " + password);
                                    if (DataBase.login(login, password)) {
                                        System.out.println("Вход успешен");
                                    } else {
                                        System.out.println("Пароль неверный");
                                    }
                                }
                            }
                        } else {
                            sendString("Пользователь с таким логином не зарегистрирован \n" +
                                    "Введите register для регистрации", socket);
                            String choice = readClientString(socket);
                            if (choice.equals("Register") || choice.equals("register")) {
                                register(socket);
                            }
                        }
                    }
                }
            } catch (IOException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public static void register (SocketChannel socket){
            try {
                sendString("Придумайте логин для входа:", socket);
                String login = readClientString(socket);
                while (true) {
                    if (DataBase.loginCheck(login)) {
                        sendString("Такой логин уже используется \n" +
                                "Введите другой:", socket);
                        login = readClientString(socket);
                    } else {
                        sendString("Придумайте пароль:", socket);
                        String password = readClientString(socket);
                        DataBase.addUser(login, password);
                        break;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public static Command deserialize ( byte[] data) throws IOException, ClassNotFoundException, EOFException {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            Command command = (Command) is.readObject();
            in.close();
            is.close();
            return command;
        }

        public static void sendCommandResult (Command command, SocketChannel socket) throws IOException {
            ByteBuffer writeBuf = ByteBuffer.allocate(2048);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            PrintStream old = System.out;

            System.setOut(ps);
            command.execute();
            System.out.flush();

            writeBuf.put(baos.toByteArray());
            writeBuf.flip();
            int written = socket.write(writeBuf);
            writeBuf.clear();

            System.setOut(old);
            System.out.println("Отправлено байт клиенту " + written);
        }

        public static ByteBuffer toBuffer (Serializable obj) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            oos.close();
            baos.close();
            return buffer;
        }
    }
