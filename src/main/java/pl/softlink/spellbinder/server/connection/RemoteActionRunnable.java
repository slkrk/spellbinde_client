package pl.softlink.spellbinder.server.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.server.Context;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;
import pl.softlink.spellbinder.server.model.User;

import java.util.HashMap;
import java.util.Map;

public class RemoteActionRunnable extends pl.softlink.spellbinder.global.connection.RemoteActionRunnable {

    private ConnectionContainer connectionContainer;

    public void setConnectionContainer(ConnectionContainer connectionContainer) {
        this.connectionContainer = connectionContainer;
    }

    public void exec(String payload) {
        JSONObject payloadJson = new JSONObject(payload);
        connectionContainer.getConnection().setConnectionId(payloadJson.getInt("connection_id"));

        switch (payloadJson.getString("action")) {
            case "patch":
                Context.getMainContext().postEvent(new PatchReceivedEvent(payloadJson.getInt("connection_id"), payloadJson.getInt("document_id"), payloadJson.getString("diff")));
                break;
            case "request":
                handleRequest(payloadJson);
                break;
        }
    }

    private void handleRequest(JSONObject requestJson) {
        switch (requestJson.getString("request_action")) {
            case "register":
                registerAction(requestJson);
                break;
            case "login":




                break;
        }
    }

    private void registerAction(JSONObject requestJson) {
        JSONObject responseJson = new JSONObject();

        responseJson.put("request_id", requestJson.getInt("request_id"));
        responseJson.put("action", "response");

        JSONObject body = requestJson.getJSONObject("body");
        String email = body.getString("email");
        String password = body.getString("password");
        User existingUser = User.findByEmail(email);

        if (existingUser == null) {
            User user = new User(email, password);
            user.save();
            responseJson.put("code", 201);
            connectionContainer.setUser(user);

        } else {
            responseJson.put("code", 406);
            responseJson.put("error", "Podany adres email jest już zarejestrowany.");
        }

        String payloadString = responseJson.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void loginAction(JSONObject requestJson) {
        JSONObject responseJson = new JSONObject();

        responseJson.put("request_id", requestJson.getInt("request_id"));
        responseJson.put("action", "response");

        JSONObject body = requestJson.getJSONObject("body");
        String email = body.getString("email");
        String password = body.getString("password");
        User existingUser = User.findByEmail(email);

        if (existingUser == null || existingUser.getPassword() != password) {
            responseJson.put("error", "Błędne dane logowania.");
            responseJson.put("code", 401);
        } else {
            responseJson.put("code", 200);
            connectionContainer.setUser(existingUser);
        }

        String payloadString = responseJson.toString();
        connectionContainer.getConnection().push(payloadString);
    }

}
