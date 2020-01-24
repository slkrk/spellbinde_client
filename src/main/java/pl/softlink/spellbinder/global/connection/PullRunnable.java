package pl.softlink.spellbinder.global.connection;

import pl.softlink.spellbinder.global.ThreadHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PullRunnable implements Runnable {

    private Socket socket;
    private boolean closed = false;
    private LinkedList<String> payloadList = new LinkedList<String>();
    private Lock lock = new ReentrantLock();
    private Connection connection;

    public PullRunnable(Socket socket) {
        this.socket = socket;
    }

    public String pull() {
        String payload = null;
        lock.lock();
        try {
            if (! payloadList.isEmpty()) {
                payload = payloadList.pop();
            }
        } finally {
            lock.unlock();
        }
        return payload;
    }

    public void close() {
        closed = true;
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (! closed) {
                System.out.println("PullRunnable::run waiting for buffer.");
                String payload = bufferedReader.readLine();
                lock.lock();
                try {
                    payloadList.addLast(payload);
                    System.out.println("Odebrano dane: " + payload);
                } finally {
                    lock.unlock();
                }
            }

        } catch (IOException e) {
            System.out.println("Rozłączono..");
            connection.shutdown();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
