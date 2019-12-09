package pl.softlink.spellbinder.server.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.server.document.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class SendRunnable implements Runnable {
    Socket socket;

    private static Document document = Document.getInstance();

    public SendRunnable(Socket socket)
    {
        super();
        this.socket = socket;
    }

    public void run()
    {
        try {

//            Document document = new Document();

            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String payload = bufferedReader.readLine();

                JSONObject payloadJson = new JSONObject(payload);
                String diff = payloadJson.getString("diff");

                document.patch(diff);
                System.out.println(document.getContent());
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }


}