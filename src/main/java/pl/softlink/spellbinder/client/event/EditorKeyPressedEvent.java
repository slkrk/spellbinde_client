package pl.softlink.spellbinder.client.event;

public class EditorKeyPressedEvent extends Event {

    private String editorContent;

    public EditorKeyPressedEvent(String editorContent) {
        this.editorContent = editorContent;
    }

    public String getEditorContent() {
        return editorContent;
    }
}
