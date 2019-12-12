package pl.softlink.spellbinder.client.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.client.event.DocumentChangedLocallyEvent;
import pl.softlink.spellbinder.client.event.EventListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Connection implements EventListener<DocumentChangedLocallyEvent> {

    private Socket socket;

    private PushRunnable pushRunnable;
    private PullRunnable pullRunnable;
    private RemoteActionRunnable remoteActionRunnable;

    private Thread pushThread;
    private Thread pullThread;
    private Thread remoteActionThread;

    public Connection(String host, int port) {

        try {
            socket = new Socket(InetAddress.getByName(host), port);
            pushRunnable = new PushRunnable(socket);
            pullRunnable = new PullRunnable(socket);
            remoteActionRunnable = new RemoteActionRunnable(pullRunnable);
            pushThread = new Thread(pushRunnable);
            pullThread = new Thread(pullRunnable);
            remoteActionThread = new Thread(remoteActionRunnable);
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

//    public String pull() {
//        return pullRunnable.pull();
//    }

    @Override
    public void onEvent(DocumentChangedLocallyEvent event) {
        JSONObject payloadJson = new JSONObject();
        payloadJson.put("diff", event.getDiff());
        String payloadString = payloadJson.toString();
        push(payloadString);
    }
}
