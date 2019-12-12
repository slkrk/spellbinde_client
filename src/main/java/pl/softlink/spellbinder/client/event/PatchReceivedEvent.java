package pl.softlink.spellbinder.client.event;

public class PatchReceivedEvent extends Event {

    int documentId;
    String diff;

    public PatchReceivedEvent(int documentId, String diff) {
        this.documentId = documentId;
        this.diff = diff;
    }

    public String getDiff() {
        return diff;
    }

    public int getDocumentId() {
        return documentId;
    }
}
