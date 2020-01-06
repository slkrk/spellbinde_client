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

                JSONObject responseJson = new JSONObject();
                responseJson.put("request_id", requestJson.getInt("request_id"));
                responseJson.put("action", "response");

                JSONObject body = requestJson.getJSONObject("body");
                String email = body.getString("email");
                User existingUser = User.findByEmail(email);

                if (existingUser == null) {
                    responseJson.put("code", 201);

                } else {
                    responseJson.put("code", 406);
                    responseJson.put("error", "Podany adres email jest już zarejestrowany.");
                }

                String payloadString = responseJson.toString();

                connectionContainer.getConnection().push(payloadString);


                break;
            case "login":
                break;
        }
    }

}
