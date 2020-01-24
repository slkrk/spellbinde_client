package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.client.event.EditorKeyPressedEvent;
import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.global.event.EventListener;

import java.io.IOException;

public class FrontController {

    private Stage stage;

    public FrontController(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        this.stage.setTitle("SpellBinder");
    }

    public void loadMain() {
        load("/view/main.fxml");
    }

    public void loadEditor() {
        load("/view/editor.fxml");
    }

    public void loadLogin() {
        load("/view/login.fxml");
    }

    public void loadList() {
        load("/view/list.fxml");
    }

    public void loadInvite() {
        load("/view/invite.fxml");
    }

    private void load(String resourcePath) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(resourcePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.show();
    }

}