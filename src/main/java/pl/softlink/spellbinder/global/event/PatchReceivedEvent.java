package pl.softlink.spellbinder.global.event;

public class PatchReceivedEvent extends Event {

    int documentId;
    int connectionId;
    String diff;

    public PatchReceivedEvent(int connectionId, int documentId, String diff) {
        this.connectionId = connectionId;
        this.documentId = documentId;
        this.diff = diff;
    }

    public String getDiff() {
        return diff;
    }

    public int getDocumentId() {
        return documentId;
    }

    public int getConnectionId() {
        return connectionId;
    }
}
