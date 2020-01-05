package pl.softlink.spellbinder.server.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.server.Context;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;

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

                JSONObject body = requestJson.getJSONObject("body");

                JSONObject responseJson = new JSONObject();
                responseJson.put("action", "response");
                responseJson.put("code", 200);
                responseJson.put("request_id", requestJson.getInt("request_id"));
                responseJson.put("body", body);
                String payloadString = responseJson.toString();

                connectionContainer.getConnection().push(payloadString);


                break;
            case "login":
                break;
        }
    }

}
