package pl.softlink.spellbinder.client.event;

import pl.softlink.spellbinder.client.Document;

public class DocumentChangedLocallyEvent extends Event {

    private String diff;

    public DocumentChangedLocallyEvent(String diff) {
        this.diff = diff;
    }

    public String getDiff() {
        return diff;
    }

}
