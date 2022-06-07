import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class ServerNIO_Selector {
    private Selector selector;
    private InetSocketAddress address;
    private Set<SocketChannel> session;

    public ServerNIO_Selector(String host, int port){
        this.address = new InetSocketAddress(host, port);
        this.session = new HashSet<SocketChannel>();
    }

    public void start() throws IOException, ClassNotFoundException {
        this.selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(this.address);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        System.out.println("Сервер запущен");

        while(true){
            //ждем подключения
            this.selector.select();
            Iterator keys = this.selector.selectedKeys().iterator();
            while(keys.hasNext()){
                SelectionKey key = (SelectionKey) keys.next();
                keys.remove();
                if (!key.isValid()){
                    continue;
                }
                if (key.isAcceptable()){
                    accept(key);
                }
                else if (key.isReadable()){
                    read(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException{
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_READ);
        this.session.add(channel);
        //String s = "Новый пользователь: " + channel.socket().getRemoteSocketAddress();
        //broadcast(s.getBytes(StandardCharsets.UTF_8));
    }

    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buf = ByteBuffer.allocate(2048);
        try {
            int read = channel.read(buf);
            if (read == -1){
                this.session.remove(channel);
                //broadcast("Клиент " + channel.socket().getRemoteSocketAddress() + " вышел");
                channel.close();
                key.cancel();
                return;
            }

            byte[] data = new byte[read];
            System.arraycopy(buf.array(), 0, data, 0, read);
            Command desCommand = deserialize(data);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
            PrintStream old = System.out;

// Tell Java to use your special stream
            System.setOut(ps);
// Print some output: goes to your special stream
// Put things back
            desCommand.execute();
            //System.out.flush();

            //buf.put(baos.toByteArray());
            broadcast(baos.toByteArray(), channel);
            buf.clear();
        } catch (SocketException e){
            System.out.println("Пользователь вышел");
        }
    }

    private void broadcast(byte [] data, SocketChannel channel){
        ByteBuffer buf = ByteBuffer.allocate(2048);
        buf.put(data);
        buf.flip();
        this.session.forEach(socketChannel ->{
            try {
                if (socketChannel.equals(channel)) {
                    socketChannel.write(buf);
                    buf.clear();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private Command deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Command command = (Command) is.readObject();
        in.close();
        is.close();
        return command;
    }
}
