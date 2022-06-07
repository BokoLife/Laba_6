import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerNIO {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        int port = 6789;

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        SocketChannel socket = serverSocketChannel.accept();
        socket.configureBlocking(false);

        while (true) {
            try {
                ByteBuffer buf = ByteBuffer.allocate(2048);
                int read = socket.read(buf);
                //System.out.println(read);
                if (read > 0) {
                    byte[] data = new byte[read];
                    System.arraycopy(buf.array(), 0, data, 0, read);
                    buf.clear();
                    System.out.println("Получено байт: " + data.length);
                    Command desCommand = deserialize(data);

                    ByteBuffer writeBuf = ByteBuffer.allocate(2048);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
                    PrintStream old = System.out;

// Tell Java to use your special stream
                    System.setOut(ps);
// Print some output: goes to your special stream
// Put things back
                    desCommand.execute();
                    System.out.flush();

                    writeBuf.put(baos.toByteArray());
                    writeBuf.flip();
                    int written = socket.write(writeBuf);
                    writeBuf.clear();

                    System.setOut(old);
                    System.out.println("Отправлено байт клиенту " + written);
                }
            } catch(SocketException e){
                System.out.println("Client left");
                Thread.sleep(5000);
            }
        }
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

    public static Command deserialize(byte[] data) throws IOException, ClassNotFoundException, EOFException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Command command = (Command) is.readObject();
        in.close();
        is.close();
        return command;
    }
}
