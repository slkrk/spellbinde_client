package pl.softlink.spellbinder.client.connection;

import org.json.JSONObject;
import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.client.event.PatchReceivedEvent;

public class RemoteActionRunnable implements Runnable {

    private boolean closed = false;

    private PullRunnable pullRunnable;

    public RemoteActionRunnable(PullRunnable pullRunnable) {
        this.pullRunnable = pullRunnable;
    }

    public void close() {
        closed = true;
    }

    public void run() {
        try {

            while (! closed) {
                String payload = pullRunnable.pull();
                if (payload != null) {
                    exec(payload);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("RemoteActionRunnable exception: ", e);
        }
    }

    public void exec(String payload) {
        JSONObject payloadJson = new JSONObject(payload);

        switch (payloadJson.getString("action")) {
            case "patch":
                Context.getMainContext().postEvent(new PatchReceivedEvent(payloadJson.getInt("document_id"), payloadJson.getString("diff")));
                break;
        }
    }

}
