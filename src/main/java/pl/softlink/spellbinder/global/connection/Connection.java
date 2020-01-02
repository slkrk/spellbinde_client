package pl.softlink.spellbinder.global.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Connection {

    private Socket socket;

    private PushRunnable pushRunnable;
    private PullRunnable pullRunnable;
    private RemoteActionRunnable remoteActionRunnable;

    private Thread pushThread;
    private Thread pullThread;
    private Thread remoteActionThread;
    private int connectionId;

    public Connection(Socket socket, RemoteActionRunnable remoteActionRunnable) {

        try {
            connectionId = new Random().nextInt(99999);
            this.remoteActionRunnable = remoteActionRunnable;
            this.socket = socket;
            pushRunnable = new PushRunnable(this.socket);
            pullRunnable = new PullRunnable(this.socket);
            this.remoteActionRunnable.setPullRunnable(pullRunnable);
            pushThread = new Thread(pushRunnable);
            pullThread = new Thread(pullRunnable);
            remoteActionThread = new Thread(remoteActionRunnable);
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
        remoteActionThread.start();
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
