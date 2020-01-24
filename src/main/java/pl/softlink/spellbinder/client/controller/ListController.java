package pl.softlink.spellbinder.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.softlink.spellbinder.client.Document;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;
import pl.softlink.spellbinder.global.security.Security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListController extends ControllerAbstract {

    @FXML
    private ListView listListView;

    @FXML
    protected Label infoLabel;

    public void initialize() {
        HashMap<String, String> body = new HashMap<String, String>();
        Request request = new Request("documentList", body);
        ResponseEvent response = Request.sendRequest(request);
        int responseCode = response.getCode();

        switch (responseCode) {
            case 200:

                JSONObject documentMapJson = response.getPayload();
                Map<String, ?> documentMap = documentMapJson.toMap();

                Iterator hmIterator = documentMap.entrySet().iterator();

                ObservableList<Button> items = FXCollections.observableArrayList();

                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry) hmIterator.next();

                    final String documentId = (String) mapElement.getKey();
                    final String documentName = (String) mapElement.getValue();

                    Button button = new Button();
                    button.setPrefSize(500, button.getPrefHeight());
                    button.setText(getButtonText(documentId, documentName));

                    button.setOnMouseClicked(mouseEvent -> buttonCallback(documentId, documentName));

                    items.add(button);
                }

                listListView.setItems(items);

                break;
            default:
                infoLabel.setText("Nie udało się otworzyć dokumentu: " + response.getError());
                infoLabel.setStyle(STYLE_ERROR);
        }
    }

    protected String getButtonText(String documentId, String documentName) {
        return "Otwórz dokument: Id: " + documentId + "; nazwa: " + documentName;
    }

    protected void buttonCallback(String documentId, String documentName) {
        System.out.println("Clicked button. documentId: " + documentId + "; documentName: " + documentName);
        Document document = new Document(Integer.parseInt(documentId), documentName);
        if (requestDocumentOpen(document)) {
            getContext().setCurrentDocument(document);
            getContext().getFrontController().loadEditor();
        }
    }

    private boolean requestDocumentOpen(Document document) {
        HashMap<String, String> body = new HashMap<String, String>();
        body.put("documentId", document.getDocumentId().toString());
        Request request = new Request("openDocument", body);
        ResponseEvent response = Request.sendRequest(request);
        int responseCode = response.getCode();

        switch (responseCode) {
            case 200:
                document.setContent(response.getPayload().getString("content"));
                return true;
            default:
                throw new RuntimeException("Document server open failure. Code: " + response.getCode() + "; error: " + response.getError());
        }
    }

    public void exitClicked(MouseEvent mouseEvent) {
        getFrontController().loadMain();
    }

}
