package pl.softlink.spellbinder.global.event;

import pl.softlink.spellbinder.global.Document;

public class DocumentChangedRemotelyEvent extends Event {

    private Document document;
    private String diff;
    private int connectionId;

    public DocumentChangedRemotelyEvent(int connectionId, Document document, String diff) {
        this.connectionId = connectionId;
        this.document = document;
        this.diff = diff;
    }

    public Document getDocument() {
        return document;
    }

    public String getDiff() {
        return diff;
    }

    public int getConnectionId() {
        return connectionId;
    }

}
