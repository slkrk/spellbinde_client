package pl.softlink.spellbinder.client.event;

import org.json.JSONObject;
import pl.softlink.spellbinder.client.Document;
import pl.softlink.spellbinder.global.event.Event;

import java.util.HashMap;
import java.util.Map;

public class ResponseEvent extends Event {

    private Integer requestId;
    private Integer code;
    private JSONObject body;
    private String error = null;

    public ResponseEvent(Integer requestId, Integer code, JSONObject body, String error) {
        this.requestId = requestId;
        this.code = code;
        this.body = body;
        this.error = error;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public JSONObject getPayload() {
        return body;
    }
}
