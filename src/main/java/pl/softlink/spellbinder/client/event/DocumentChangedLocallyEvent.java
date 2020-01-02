package pl.softlink.spellbinder.client.event;

import pl.softlink.spellbinder.client.Document;
import pl.softlink.spellbinder.global.event.Event;

public class DocumentChangedLocallyEvent extends Event {

    private String diff;
    private Document document;

    public DocumentChangedLocallyEvent(Document document, String diff) {
        this.document = document;
        this.diff = diff;
    }

    public String getDiff() {
        return diff;
    }

    public Document getDocument() {
        return document;
    }

}
