package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;

import java.util.HashMap;

public class LoginController extends ControllerAbstract {

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    public void onLoginClick(MouseEvent mouseEvent) {
        System.out.println("onLoginClick");
    }

    @FXML
    public void onRegisterClick(MouseEvent mouseEvent) {
        System.out.println("onRegisterClick");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("email", emailTextField.getText());
        body.put("password", passwordTextField.getText());

        Request request = new Request("register", body);

        ResponseEvent response = Request.sendRequest(request);

        System.out.println(response.getCode());


    }
}