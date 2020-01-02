package pl.softlink.spellbinder.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.softlink.spellbinder.client.connection.RemoteActionRunnable;
import pl.softlink.spellbinder.global.connection.Connection;
import pl.softlink.spellbinder.server.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource( "/view/editor.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Context context = new Context();
        Context.setMainContext(context);

        Document document = new Document(123);

        context.setCurrentDocument(document);

        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getByName(Config.CONNECTION_HOST), Config.CONNECTION_PORT);
        } catch (IOException e) {
            throw new RuntimeException("Nieznany host", e);
        }

        RemoteActionRunnable remoteActionRunnable = new RemoteActionRunnable();

        Connection connection = new Connection(socket, remoteActionRunnable);
        LocalAction localAction = new LocalAction(connection);

        context.setConnection(connection);
        context.setLocalAction(localAction);

        connection.start();


//        new EditorController();
//
//        context.postEvent(new EditorKeyPressedEvent("sdf"));
//        context.postEvent(new DocumentChangedRemotelyEvent(document, "sdf"));

//
//
//
        launch(args);
//        connection.close();
    }
}
