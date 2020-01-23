package pl.softlink.spellbinder.server.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.server.Context;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;
import pl.softlink.spellbinder.server.model.Document;
import pl.softlink.spellbinder.server.model.User;

import java.util.HashMap;
import java.util.LinkedList;

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
                int documentId = payloadJson.getInt("document_id");
                Context.getMainContext().postEvent(new PatchReceivedEvent(payloadJson.getInt("connection_id"), documentId, payloadJson.getString("diff")));
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
                loginAction(requestJson);
                break;
            case "newDocument":
                newDocumentAction(requestJson);
                break;
            case "documentList":
                documentListAction(requestJson);
                break;
            case "openDocument":
                openDocumentAction(requestJson);
                break;

        }
    }

    private void openDocumentAction(JSONObject requestJson) {
        JSONObject responseJson = new JSONObject();

        responseJson.put("request_id", requestJson.getInt("request_id"));
        responseJson.put("action", "response");
        JSONObject body = requestJson.getJSONObject("body");
        Integer documentId =  body.getInt("documentId");
        connectionContainer.setCurrentDocumentId(documentId);
        responseJson.put("code", 200);
        String payloadString = responseJson.toString();
        connectionContainer.getConnection().push(payloadString);
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
            HashMap<String, String> responseBody = new HashMap<String, String>();
            User user = new User(email, password);
            user.save();
            responseBody.put("email", user.getEmail());
            responseBody.put("userId", user.getId().toString());
            responseJson.put("body", responseBody);
            responseJson.put("code", 201);
            connectionContainer.setUser(user);

        } else {
            responseJson.put("code", 406);
            responseJson.put("error", "Podany adres email jest zajęty.");
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

        if (existingUser == null || !existingUser.getPassword().equals(password)) {
            responseJson.put("code", 401);
        } else {
            HashMap<String, String> responseBody = new HashMap<String, String>();
            responseBody.put("email", existingUser.getEmail());
            responseBody.put("userId", existingUser.getId().toString());
            responseJson.put("body", responseBody);
            responseJson.put("error", "Błędne dane logowania.");
            responseJson.put("code", 200);
            connectionContainer.setUser(existingUser);
        }

        String payloadString = responseJson.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void newDocumentAction(JSONObject requestJson) {
        JSONObject responseJson = new JSONObject();

        responseJson.put("request_id", requestJson.getInt("request_id"));
        responseJson.put("action", "response");

        JSONObject body = requestJson.getJSONObject("body");
        Document document = new Document(body.getString("name"));
        document.save();
        document.addUser(connectionContainer.getUser());

        if (document == null) {
            responseJson.put("error", "Nie udało się utworzyć dokumentu.");
            responseJson.put("code", 400);
        } else {
            int documentId = document.getId();
            HashMap<String, String> responseBody = new HashMap<String, String>();
            responseBody.put("documentId", Integer.toString(documentId));
            responseBody.put("documentName", document.getName());
            responseJson.put("body", responseBody);
            responseJson.put("code", 201);
            connectionContainer.setCurrentDocumentId(documentId);
        }

        String payloadString = responseJson.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void documentListAction(JSONObject requestJson) {
        JSONObject responseJson = new JSONObject();

        responseJson.put("request_id", requestJson.getInt("request_id"));
        responseJson.put("action", "response");

        LinkedList<Document> documentList = Document.getListForUser(connectionContainer.getUser());

        HashMap<String, String> documentMap = new HashMap<>();

        for (Document document : documentList) {

            documentMap.put(document.getId().toString(), document.getName());
        }

        responseJson.put("body", documentMap);
        responseJson.put("code", 200);

        String payloadString = responseJson.toString();
        connectionContainer.getConnection().push(payloadString);
    }

}
