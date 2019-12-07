package pl.softlink.spellbinder.server.document;

import pl.softlink.spellbinder.service.TextDiff;

public class Document {

    private int id;
    private String content = "";

    public String getContent() {
        return content;
    }

    public void patch(String diff) {
        content = TextDiff.apply(content, diff);
    }





}
