package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.client.Document;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;
import pl.softlink.spellbinder.global.security.Security;

import java.awt.*;
import java.util.HashMap;

public class MainController extends ControllerAbstract {

//    @FXML
//    private Label currentUserEmailLabel;

    @FXML
    private Label currentUserEmailLabel;

    @FXML
    private Label mainMenuErrorLabel;

    public void initialize(){
        currentUserEmailLabel.setText(getContext().getCurrentUserEmail());
    }

    @FXML
    public void onLogoutClick(MouseEvent mouseEvent) {
        System.out.println("onLogoutClick");
    }

    @FXML
    public void onNewClick(MouseEvent mouseEvent) {
        System.out.println("onNewClick");

        HashMap<String, String> body = new HashMap<String, String>();

        Request request = new Request("newDocument", body);

        ResponseEvent response = Request.sendRequest(request);

        int responseCode = response.getCode();

        switch (responseCode) {
            case 201:
                Document document = new Document(response.getPayload().getInt("documentId"));
                getContext().setCurrentDocument(document);
                getContext().getFrontController().loadEditor();
                break;
            default:
                mainMenuErrorLabel.setText(response.getError());
        }

    }

    @FXML
    public void onOpenClick(MouseEvent mouseEvent) {
        System.out.println("onOpenClick");
        Document document = new Document(1);
        getContext().setCurrentDocument(document);
        getFrontController().loadEditor();
    }
}