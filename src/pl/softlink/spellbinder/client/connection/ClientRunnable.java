package pl.softlink.spellbinder.client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientRunnable implements Runnable {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 6789;

    private static ClientRunnable instance;
    private static boolean closed = false;

    private LinkedList<String> payloadList = new LinkedList<String>();
    private Lock lock = new ReentrantLock();

    public static void initialize() {
        ClientRunnable instance = new ClientRunnable();
        Thread thread = new Thread(instance);
        thread.run();
    }

    public static void send(String payload) {
        instance.doSend(payload);
    }

    public static void close() {
        closed = true;
    }

    private ClientRunnable() {
        // empty private constructor
    }

    public void run() {

        try {
            Socket socket = new Socket(InetAddress.getByName(HOST), PORT);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (! closed) {

                String payload = null;

                lock.lock();
                if (! payloadList.isEmpty()) {
                    payload = payloadList.pop();
                }
                lock.unlock();

                if (payload == null) {
                    continue;
                }

                out.print(payload);
            }

            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private void doSend(String payload) {
        lock.lock();
        payloadList.addLast(payload);
        lock.unlock();
    }

}
