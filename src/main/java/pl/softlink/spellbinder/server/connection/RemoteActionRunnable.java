package pl.softlink.spellbinder.server.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.server.Context;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;

public class RemoteActionRunnable extends pl.softlink.spellbinder.global.connection.RemoteActionRunnable {

    public void exec(String payload) {
        JSONObject payloadJson = new JSONObject(payload);

        switch (payloadJson.getString("action")) {
            case "patch":
                Context.getMainContext().postEvent(new PatchReceivedEvent(payloadJson.getInt("connection_id"), payloadJson.getInt("document_id"), payloadJson.getString("diff")));
                break;
        }
    }

}
