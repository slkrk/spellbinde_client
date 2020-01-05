package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.client.event.EditorKeyPressedEvent;
import pl.softlink.spellbinder.global.event.EventListener;

public class EditorController extends ControllerAbstract implements EventListener<DocumentChangedRemotelyEvent> {


    @FXML
    private Pane root;

    public EditorController() {
        super();
        getContext().setEditorController(this);
    }

    @FXML
    protected void onKeyReleased(KeyEvent keyEvent) {

        String newContent = ((TextArea) root.getChildren().get(0)).getText();
        getContext().postEvent(new EditorKeyPressedEvent(newContent));
    }


    public void onEvent(DocumentChangedRemotelyEvent event) {
        ((TextArea) root.getChildren().get(0)).setText(event.getDocument().getContent());
    }

}