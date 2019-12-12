package pl.softlink.spellbinder.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.softlink.spellbinder.client.connection.Connection;
import pl.softlink.spellbinder.client.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.client.event.EditorKeyPressedEvent;
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

        Context context = new Context();
        Context.setMainContext(context);

        Document document = new Document();

        context.setCurrentDocument(document);

        Connection connection = new Connection(Config.CONNECTION_HOST, Config.CONNECTION_PORT);
        context.setConnection(connection);

//        new EditorController();
//
//        context.postEvent(new EditorKeyPressedEvent("sdf"));
//        context.postEvent(new DocumentChangedRemotelyEvent(document, "sdf"));

//
//
//
//        launch(args);
//        connection.close();
    }
}
