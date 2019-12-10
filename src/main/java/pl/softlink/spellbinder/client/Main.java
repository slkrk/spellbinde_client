package pl.softlink.spellbinder.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.softlink.spellbinder.client.connection.Connection;
import pl.softlink.spellbinder.client.connection.PushRunnable;
import pl.softlink.spellbinder.server.Config;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource( "/view/editor.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        System.out.println(System.getProperty("user.dir"));
        Connection connection = new Connection(Config.CONNECTION_HOST, Config.CONNECTION_PORT);


        launch(args);
        connection.close();
    }
}
