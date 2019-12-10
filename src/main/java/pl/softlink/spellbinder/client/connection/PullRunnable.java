package pl.softlink.spellbinder.client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PullRunnable implements Runnable {

    private Socket socket;
    private boolean closed = false;
    private LinkedList<String> payloadList = new LinkedList<String>();
    private Lock lock = new ReentrantLock();

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
                String payload = bufferedReader.readLine();
                lock.lock();
                try {
                    payloadList.addLast(payload);
                } finally {
                    lock.unlock();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("PushRunnable: nie można odczytać bufora.", e);
        }
    }

}
