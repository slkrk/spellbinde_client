package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.client.Document;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;
import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.client.event.EditorKeyPressedEvent;
import pl.softlink.spellbinder.global.event.EventListener;

import java.util.HashMap;

public class EditorController extends ControllerAbstract implements EventListener<DocumentChangedRemotelyEvent> {


    @FXML
    private Pane root;

    @FXML
    private TextField documentName;

    public EditorController() {
        super();
        getContext().setEditorController(this);
    }

    public void initialize() {
        ((TextArea) root.getChildren().get(0)).setText(getContext().getCurrentDocument().getContent());
    }

    @FXML
    protected void onKeyReleased(KeyEvent keyEvent) {

        String newContent = ((TextArea) root.getChildren().get(0)).getText();
        getContext().postEvent(new EditorKeyPressedEvent(newContent));
    }


    public void onEvent(DocumentChangedRemotelyEvent event) {
        ((TextArea) root.getChildren().get(0)).setText(event.getDocument().getContent());
    }

    public void exitClicked(MouseEvent mouseEvent) {
        getFrontController().loadMain();
    }

    public void renameClicked(MouseEvent mouseEvent) {
        Document document = getContext().getCurrentDocument();
        document.setDocumentName(documentName.getText());
        requestChangeName(document);
    }

    private boolean requestChangeName(Document document) {
        HashMap<String, String> body = new HashMap<String, String>();
        body.put("documentId", document.getDocumentId().toString());
        body.put("documentName", document.getDocumentName());
        Request request = new Request("changeName", body);
        ResponseEvent response = Request.sendRequest(request);
        int responseCode = response.getCode();

        switch (responseCode) {
            case 200:
                return true;
            default:
                throw new RuntimeException("Editor change document name failed. Code: " + response.getCode() + "; error: " + response.getError());
        }
    }
}