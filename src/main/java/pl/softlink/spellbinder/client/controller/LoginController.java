package pl.softlink.spellbinder.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.client.User;
import pl.softlink.spellbinder.client.connection.Request;
import pl.softlink.spellbinder.client.event.ResponseEvent;
import pl.softlink.spellbinder.global.security.Security;

import java.util.HashMap;

public class LoginController extends ControllerAbstract {

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label loginErrorLabel;

    @FXML
    public void onLoginClick(MouseEvent mouseEvent) {
        System.out.println("onLoginClick");

        HashMap<String, String> body = new HashMap<String, String>();
        String email = emailTextField.getText();
        body.put("email", email);
        body.put("password", Security.md5(passwordTextField.getText()));

        Request request = new Request("login", body);

        ResponseEvent response = Request.sendRequest(request);

        int responseCode = response.getCode();

        switch (responseCode) {
            case 200:
                getContext().setUser(
                    new User(
                        response.getPayload().getInt("userId"),
                        response.getPayload().getString("email")
                    )
                );

                getContext().getFrontController().loadMain();
                break;
            default:
                loginErrorLabel.setText(response.getError());
        }
    }

    @FXML
    public void onRegisterClick(MouseEvent mouseEvent) {
        System.out.println("onRegisterClick");

        HashMap<String, String> body = new HashMap<String, String>();
        String email = emailTextField.getText();
        body.put("email", email);
        body.put("password", Security.md5(passwordTextField.getText()));

        Request request = new Request("register", body);

        ResponseEvent response = Request.sendRequest(request);

        int responseCode = response.getCode();

        switch (responseCode) {
            case 201:
                getContext().setUser(
                    new User(
                        response.getPayload().getInt("userid"),
                        response.getPayload().getString("email")
                    )
                );
                
                getContext().getFrontController().loadMain();
                break;
            default:
                loginErrorLabel.setText(response.getError());
        }

    }
}