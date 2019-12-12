package pl.softlink.spellbinder.client;

import pl.softlink.spellbinder.client.event.*;
import pl.softlink.spellbinder.service.TextDiff;

//public class Document implements EventListener<? extends Event> {
public class Document implements EventListener<Event> {

    private String content = "";
    private EditorController editorController = null;

    @Override
    public <T extends Event> void onEvent(T event) {
        if (event instanceof EditorKeyPressedEvent) {
            onEditorKeyPressedEvent((EditorKeyPressedEvent) event);
        } else if (event instanceof PatchReceivedEvent) {
            onPatchReceivedEvent((PatchReceivedEvent) event);
        }
    }

    public void onEditorKeyPressedEvent(EditorKeyPressedEvent event) {
        System.out.println("Document event dispatched EditorContentChangedEvent");
        String diff = TextDiff.generate(content, event.getEditorContent());
        if (diff != null) {
            Context.getMainContext().postEvent(new DocumentChangedLocallyEvent(diff));
        }
    }

    public void onPatchReceivedEvent(PatchReceivedEvent event) {
        System.out.println("Document event dispatched MessagePulledEvent");
        content = TextDiff.apply(content, event.getDiff());
        Context.getMainContext().postEvent(new DocumentChangedRemotelyEvent(this));
    }



    public String getContent() {
        return content;
    }

    public Document setContent(String content) {
        this.content = content;
        return this;
    }

    public Document setEditorController(EditorController editorController) {
        this.editorController = editorController;
        return this;
    }

    public void patch(String diff) {
        content = TextDiff.apply(content, diff);
    }

    public void refreshEditor() {
//        editorController.setContent
    }
}
