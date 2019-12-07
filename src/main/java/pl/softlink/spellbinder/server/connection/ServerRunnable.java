package pl.softlink.spellbinder.server.connection;
import pl.softlink.spellbinder.server.document.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class ServerRunnable implements Runnable {
    Socket socket;

    public ServerRunnable(Socket socket)
    {
        super();
        this.socket = socket;
    }

    public void run()
    {
        try {

            Document document = new Document();

            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String diff = bufferedReader.readLine();
                document.patch(diff);
                System.out.println(document.getContent());
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}