package pl.softlink.spellbinder.client.event;

import pl.softlink.spellbinder.global.event.Event;

public class EditorKeyPressedEvent extends Event {

    private String editorContent;

    public EditorKeyPressedEvent(String editorContent) {
        this.editorContent = editorContent;
    }

    public String getEditorContent() {
        return editorContent;
    }
}
