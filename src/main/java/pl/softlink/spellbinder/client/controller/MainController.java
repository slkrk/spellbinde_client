package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.softlink.spellbinder.client.Context;

public class MainController extends ControllerAbstract {

    @FXML
    public void onLogoutClick(MouseEvent mouseEvent) {
        System.out.println("onLogoutClick");
    }

    @FXML
    public void onNewClick(MouseEvent mouseEvent) {
        System.out.println("onNewClick");
    }

    @FXML
    public void onOpenClick(MouseEvent mouseEvent) {
        System.out.println("onOpenClick");
        getFrontController().loadEditor();
    }
}