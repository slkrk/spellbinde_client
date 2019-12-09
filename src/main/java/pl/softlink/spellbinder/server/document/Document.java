package pl.softlink.spellbinder.server.document;

import pl.softlink.spellbinder.service.TextDiff;

public class Document {


    private int id;
    private String content = "";

    public static Document instance = null;

    public String getContent() {
        return content;
    }

    public void patch(String diff) {
        content = TextDiff.apply(content, diff);
    }

    private Document() {

    }

    public static Document getInstance() {
        if (instance == null) {
            instance = new Document();
        }
        return instance;
    }





}
