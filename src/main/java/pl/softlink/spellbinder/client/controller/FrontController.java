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
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadEditor() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/editor.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("SpellBinder - editor: " + Context.getMainContext().getCurrentDocument().getDocumentName() + " (id: " + Context.getMainContext().getCurrentDocument().getDocumentId() + ")");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadLogin() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadList() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/list.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.show();
    }

    private void load(String resourcePath) {

    }

}