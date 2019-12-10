package pl.softlink.spellbinder.client.connection;

import pl.softlink.spellbinder.server.Config;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PushRunnable implements Runnable {

    private Socket socket;
    private boolean closed = false;
    private LinkedList<String> payloadList = new LinkedList<String>();
    private Lock lock = new ReentrantLock();

    public PushRunnable(Socket socket) {
        this.socket = socket;
    }

    public void push(String payload) {
        lock.lock();
        try {
            payloadList.addLast(payload);
        } finally {
            lock.unlock();
        }
    }

    public void close() {
        closed = true;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (! closed) {

                String payload = null;

                lock.lock();
                try {
                    if (! payloadList.isEmpty()) {
                        payload = payloadList.pop();
                    }
                } finally {
                    lock.unlock();
                }

                if (payload == null) {
                    continue;
                }

                out.println(payload);
            }

        } catch (IOException e) {
            throw new RuntimeException("PushRunnable: nie można odczytać strumienia.", e);
        }
    }

}
