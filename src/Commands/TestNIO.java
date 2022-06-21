package Commands;

import java.io.IOException;

public class TestNIO {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        InetAddress host = InetAddress.getLocalHost();
//        Server_NIO_Selector server = new Server_NIO_Selector(host.getHostName(), 7777);
//        server.start();
//        ForkJoinPool pool = new ForkJoinPool();
//        ExecutorService es = Executors.newFixedThreadPool(4);
//        System.out.println(pool.getActiveThreadCount());
        AddCommand a = new AddCommand();
        System.out.println(Command.class.isAssignableFrom(a.getClass()));
    }
}
