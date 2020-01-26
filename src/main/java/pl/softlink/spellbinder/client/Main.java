package pl.softlink.spellbinder.client;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.softlink.spellbinder.client.connection.LocalAction;
import pl.softlink.spellbinder.client.connection.RemoteActionRunnable;
import pl.softlink.spellbinder.client.controller.FrontController;
import pl.softlink.spellbinder.global.connection.Connection;
import pl.softlink.spellbinder.server.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FrontController frontController = new FrontController(primaryStage);
        Context.getMainContext().setFrontController(frontController);
        Context.getMainContext().getFrontController().loadLogin();

//        Parent root = FXMLLoader.load(getClass().getResource( "/view/main.fxml"));
//        primaryStage.setTitle("SpellBinder");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
    }


    public static void main(String[] args) {

        Context context = new Context();
        Context.setMainContext(context);

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

        launch(args);
        connection.close();
        connection.shutdown();
        System.exit(0);
    }
}
