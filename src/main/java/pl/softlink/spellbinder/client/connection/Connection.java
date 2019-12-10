package pl.softlink.spellbinder.client.connection;

import pl.softlink.spellbinder.server.Config;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Connection {

    private Socket socket;

    private PushRunnable pushRunnable;
    private PullRunnable pullRunnable;

    private Thread pushThread;
    private Thread pullThread;

    public Connection(String host, int port) {

        try {
            socket = new Socket(InetAddress.getByName(host), port);
            pushRunnable = new PushRunnable(socket);
            pullRunnable = new PullRunnable(socket);
            pushThread = new Thread(pushRunnable);
            pullThread = new Thread(pullRunnable);
        } catch (IOException e) {
            throw new RuntimeException("Nieznany host", e);
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void start() {
        pushThread.start();
        pullThread.start();
    }

    public void close() {
        pushRunnable.close();
        pullRunnable.close();
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void push(String payload) {
        pushRunnable.push(payload);
    }

    public String pull() {
        return pullRunnable.pull();
    }

}
