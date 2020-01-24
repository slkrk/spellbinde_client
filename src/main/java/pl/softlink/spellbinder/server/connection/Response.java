package pl.softlink.spellbinder.server.connection;

import org.json.JSONObject;

import java.util.HashMap;

public class Response {

    private JSONObject request;
    private JSONObject response = new JSONObject();
    ;
    private HashMap<String, String> body = new HashMap<String, String>();

    public Response(JSONObject request) {
        this.request = request;
        response.put("request_id", request.getInt("request_id"));
        response.put("action", "response");
    }

    public Response put(String key, String value) {
        response.put(key, value);
        return this;
    }

    public Response putBody(String key, String value) {
        body.put(key, value);
        return this;
    }

    public Response put(String key, Integer value) {
        response.put(key, value.toString());
        return this;
    }

    public Response putBody(String key, Integer value) {
        body.put(key, value.toString());
        return this;
    }

    public String toString() {
        response.put("body", body);
        return response.toString();
    }

}
