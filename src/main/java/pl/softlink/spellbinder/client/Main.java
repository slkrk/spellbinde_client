package pl.softlink.spellbinder.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.softlink.spellbinder.client.connection.ClientRunnable;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{



//        Parent root = FXMLLoader.load(getClass().getResource("C:\\Users\\fishkiller\\IdeaProjects\\spellbinder_client\\src\\main\\java\\pl\\softlink\\spellbinder\\client\\view\\front.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource( "/view/front.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("../../../../../resources/view/front.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        System.out.println(System.getProperty("user.dir"));
        ClientRunnable.initialize();
        launch(args);
        ClientRunnable.close();
    }
}
