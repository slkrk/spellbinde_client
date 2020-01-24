package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import pl.softlink.spellbinder.client.Document;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;
import java.util.HashMap;

public class MainController extends ControllerAbstract {

    @FXML
    private Label currentUserEmailLabel;

    @FXML
    private Label mainMenuErrorLabel;

    public void initialize(){
        currentUserEmailLabel.setText(getContext().getUser().getEmail());
    }

    @FXML
    public void onLogoutClick(MouseEvent mouseEvent) {
        System.out.println("onLogoutClick");
        getContext().setUser(null);
        getContext().getConnection().shutdown();
        getFrontController().loadLogin();
    }

    @FXML
    public void onNewClick(MouseEvent mouseEvent) {
        System.out.println("onNewClick");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("name", "NewDocument");

        Request request = new Request("newDocument", body);

        ResponseEvent response = Request.sendRequest(request);

        int responseCode = response.getCode();

        switch (responseCode) {
            case 201:
                Document document = new Document(response.getPayload().getInt("documentId"), response.getPayload().getString("documentName"));
                getContext().setCurrentDocument(document);
                getFrontController().loadEditor();
                break;
            default:
                mainMenuErrorLabel.setText(response.getError());
        }

    }

    @FXML
    public void onOpenClick(MouseEvent mouseEvent) {
        System.out.println("onOpenClick");
        getFrontController().loadList();

    }

    public void onInviteClick(MouseEvent mouseEvent) {
        getFrontController().loadInvite();
    }
}