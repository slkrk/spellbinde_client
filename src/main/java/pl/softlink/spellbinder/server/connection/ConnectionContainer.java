package pl.softlink.spellbinder.server.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.global.connection.Connection;
import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.global.event.EventListener;
import pl.softlink.spellbinder.server.model.User;

public class ConnectionContainer implements EventListener<DocumentChangedRemotelyEvent> {

    private int currentDocumentId;
    private Connection connection;
    private User user;

    public ConnectionContainer(Connection connection) {
        this.connection = connection;
    }

    public void setCurrentDocumentId(int currentDocumentId) {
        this.currentDocumentId = currentDocumentId;
    }

    public void onEvent (DocumentChangedRemotelyEvent event) {
        if (event.getDocument().getDocumentId() == currentDocumentId) {

            JSONObject payloadJson = new JSONObject();
            payloadJson.put("action", "patch");
            payloadJson.put("connection_id", event.getConnectionId());
            payloadJson.put("document_id", event.getDocument().getDocumentId());
            payloadJson.put("diff", event.getDiff());
            String payloadString = payloadJson.toString();

            connection.push(payloadString);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
