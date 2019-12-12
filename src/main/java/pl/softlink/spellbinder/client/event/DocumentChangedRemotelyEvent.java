package pl.softlink.spellbinder.client.event;

import pl.softlink.spellbinder.client.Document;

public class DocumentChangedRemotelyEvent extends Event {

    private Document document;

    public DocumentChangedRemotelyEvent(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

}
