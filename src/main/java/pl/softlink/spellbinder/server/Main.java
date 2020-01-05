package pl.softlink.spellbinder.server;

import pl.softlink.spellbinder.global.connection.Connection;
import pl.softlink.spellbinder.server.connection.ConnectionContainer;
import pl.softlink.spellbinder.server.connection.RemoteActionRunnable;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String args[]) {

        Context context = new Context();
        Context.setMainContext(context);

        try {

            ServerSocket serverSocket = new ServerSocket(Config.CONNECTION_PORT);

            int currentDocumentId = 123;

            while (true) {
                Socket socket = serverSocket.accept();
                RemoteActionRunnable remoteActionRunnable = new RemoteActionRunnable();
                Connection connection = new Connection(socket, remoteActionRunnable);

                ConnectionContainer connectionContainer = new ConnectionContainer(connection);
                connectionContainer.setCurrentDocumentId(currentDocumentId);
                remoteActionRunnable.setConnectionContainer(connectionContainer);

                context.addConnectionContainer(connectionContainer);

                connection.start();
                System.out.println("Serwer: utworzono nowe połączenie.");

            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
