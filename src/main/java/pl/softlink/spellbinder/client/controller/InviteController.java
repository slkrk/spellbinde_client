package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.softlink.spellbinder.client.Document;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;

import java.util.HashMap;

public class InviteController extends ListController {

    @FXML
    private TextField inviteEmailTextField;

    @FXML
    private Label inviteEmailLabel;

    protected void buttonCallback(String documentId, String documentName) {
        System.out.println("Clicked button. documentId: " + documentId + "; documentName: " + documentName);
        Document document = new Document(Integer.parseInt(documentId), documentName);
        requestInvite(document);
    }

    protected String getButtonText(String documentId, String documentName) {
        return "Udostępnij dokument: Id: " + documentId + "; nazwa: " + documentName;
    }

    private boolean requestInvite(Document document) {
        HashMap<String, String> body = new HashMap<String, String>();
        String email = inviteEmailTextField.getText();
        if (email.length() < 1) {
            infoLabel.setText("Email nie może być pusty");
            infoLabel.setStyle(STYLE_ERROR);
            return false;
        }
        body.put("documentId", document.getDocumentId().toString());
        body.put("email", email);
        Request request = new Request("invite", body);
        ResponseEvent response = Request.sendRequest(request);
        int responseCode = response.getCode();

        switch (responseCode) {
            case 200:
                infoLabel.setText("Użytkownik " + email + " uzyskał dostęp do dokumentu Id: " + document.getDocumentId());
                infoLabel.setStyle(STYLE_NORMAL);
                return true;
            default:
                infoLabel.setText(response.getError());
                infoLabel.setStyle(STYLE_ERROR);
                return false;
        }
    }

}
