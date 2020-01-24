package pl.softlink.spellbinder.global.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.*;

public class Connection extends ThreadPoolExecutor {

    private Socket socket;

    private PushRunnable pushRunnable;
    private PullRunnable pullRunnable;
    private RemoteActionRunnable remoteActionRunnable;
    private int connectionId;

    public Connection(Socket socket, RemoteActionRunnable remoteActionRunnable) {
        super(3, 3, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));

        try {
            connectionId = new Random().nextInt(99999);
            this.remoteActionRunnable = remoteActionRunnable;
            this.socket = socket;
            pushRunnable = new PushRunnable(this.socket);
            pullRunnable = new PullRunnable(this.socket);
            pullRunnable.setConnection(this);
            this.remoteActionRunnable.setPullConnection(this);
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Connection setConnectionId(int connectionId) {
        this.connectionId = connectionId;
        return this;
    }

    public void start() {

        try {
            execute(pushRunnable);
            execute(pullRunnable);
            execute(remoteActionRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        pushRunnable.close();
        pullRunnable.close();
        remoteActionRunnable.close();
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

    public int getConnectionId() {
        return connectionId;
    }

//    @Override
//    public void onEvent(DocumentChangedLocallyEvent event) {
//        JSONObject payloadJson = new JSONObject();
//        payloadJson.put("diff", event.getDiff());
//        String payloadString = payloadJson.toString();
//        push(payloadString);
//    }
}
