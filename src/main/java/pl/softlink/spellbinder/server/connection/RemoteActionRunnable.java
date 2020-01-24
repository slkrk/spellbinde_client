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
            case "changeName":
                changeNameAction(requestJson);
                break;
            case "invite":
                inviteAction(requestJson);
                break;

        }
    }

    private void inviteAction(JSONObject requestJson) {
        Response response = new Response(requestJson);

        JSONObject body = requestJson.getJSONObject("body");
        Integer documentId = body.getInt("documentId");
        String email = body.getString("email");

        Document document = Document.findById(documentId);
        User user = User.findByEmail(email);

        if (user == null) {
            response.put("code", 406);
            response.put("error", "Podany adres email nie istnieje.");
        } else if (document.addUser(user)) {
            response.put("code", 200);
        } else {
            response.put("code", 409);
            response.put("error", "Użytkownij został już wcześniej dodany.");
        }

        String payloadString = response.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void changeNameAction(JSONObject requestJson) {
        Response response = new Response(requestJson);

        JSONObject body = requestJson.getJSONObject("body");
        Integer documentId = body.getInt("documentId");
        String newName = body.getString("documentName");

        Document document = Document.findById(documentId);
        document.setName(newName);
        document.save();

        response.put("code", 200);
        String payloadString = response.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void openDocumentAction(JSONObject requestJson) {

        Response response = new Response(requestJson);

        JSONObject body = requestJson.getJSONObject("body");
        Integer documentId = body.getInt("documentId");
        Document document = Document.findById(documentId);
        connectionContainer.setCurrentDocumentId(document.getId());
        response.putBody("content", document.getContent());
        response.put("code", 200);
        String payloadString = response.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void registerAction(JSONObject requestJson) {

        JSONObject body = requestJson.getJSONObject("body");
        String email = body.getString("email");
        String password = body.getString("password");
        User existingUser = User.findByEmail(email);

        Response response = new Response(requestJson);

        if (existingUser == null) {
            User user = new User(email, password);
            user.save();
            response.put("email", user.getEmail());
            response.put("userId", user.getId().toString());
            response.put("code", 201);
            connectionContainer.setUser(user);

        } else {
            response.put("code", 406);
            response.put("error", "Podany adres email jest zajęty.");
        }

        String payloadString = response.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void loginAction(JSONObject requestJson) {
        Response response = new Response(requestJson);
        JSONObject body = requestJson.getJSONObject("body");
        String email = body.getString("email");
        String password = body.getString("password");
        User existingUser = User.findByEmail(email);

        if (existingUser == null || !existingUser.getPassword().equals(password)) {
            response.put("code", 401);
        } else {
            HashMap<String, String> responseBody = new HashMap<String, String>();
            response.putBody("email", existingUser.getEmail());
            response.putBody("userId", existingUser.getId().toString());
            response.put("error", "Błędne dane logowania.");
            response.put("code", 200);
            connectionContainer.setUser(existingUser);
        }

        String payloadString = response.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void newDocumentAction(JSONObject requestJson) {
        Response response = new Response(requestJson);
        JSONObject body = requestJson.getJSONObject("body");
        Document document = new Document(body.getString("name"));
        document.save();
        document.addUser(connectionContainer.getUser());

        if (document == null) {
            response.put("error", "Nie udało się utworzyć dokumentu.");
            response.put("code", 400);
        } else {
            int documentId = document.getId();
            HashMap<String, String> responseBody = new HashMap<String, String>();
            response.putBody("documentId", Integer.toString(documentId));
            response.putBody("documentName", document.getName());
            response.put("code", 201);
            connectionContainer.setCurrentDocumentId(documentId);
        }

        String payloadString = response.toString();
        connectionContainer.getConnection().push(payloadString);
    }

    private void documentListAction(JSONObject requestJson) {
        Response response = new Response(requestJson);

        LinkedList<Document> documentList = Document.getListForUser(connectionContainer.getUser());

        for (Document document : documentList) {
            response.putBody(document.getId().toString(), document.getName());
        }

        response.put("code", 200);

        String payloadString = response.toString();
        connectionContainer.getConnection().push(payloadString);
    }

}
