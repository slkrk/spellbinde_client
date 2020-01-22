package pl.softlink.spellbinder.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;
import pl.softlink.spellbinder.global.security.Security;

import java.util.HashMap;

public class ListController extends ControllerAbstract {

    @FXML
    private ListView listListView;

    public void initialize() {

//        ListView<Button> listView = new ListView();




        HashMap<String, String> body = new HashMap<String, String>();

//        body.put("email", email);
//        body.put("password", Security.md5(passwordTextField.getText()));

        body.put("userId", getContext().getUser().getId().toString());
        Request request = new Request("getDocumentList", body);

        ResponseEvent response = Request.sendRequest(request);

        int responseCode = response.getCode();

        switch (responseCode) {
            case 200:





                break;
            default:
//                loginErrorLabel.setText(response.getError());
        }





        ObservableList<Button> items = FXCollections.observableArrayList();

        for (int i = 0; i < 10; i++) {

            Button button = new Button();
            button.setText("Button " + i);
            final int j = i;
            button.setOnMouseClicked(mouseEvent -> {System.out.println("Clicked button " + j);});

            items.add(button);



        }

        listListView.setItems(items);

    }


}
