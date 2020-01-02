package pl.softlink.spellbinder.client;

import pl.softlink.spellbinder.client.event.DocumentChangedLocallyEvent;
import pl.softlink.spellbinder.client.event.EditorKeyPressedEvent;
import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.global.event.EventListener;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;
import pl.softlink.spellbinder.global.event.Event;
import pl.softlink.spellbinder.service.TextDiff;

public class Document extends pl.softlink.spellbinder.global.Document implements EventListener<Event> {

    public Document(int documentId) {
        super(documentId);
    }

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
            patch(diff);
            Context.getMainContext().postEvent(new DocumentChangedLocallyEvent(this, diff));
        }
    }

    public void onPatchReceivedEvent(PatchReceivedEvent event) {
        System.out.println("Document event dispatched MessagePulledEvent");

        if (event.getConnectionId() == Context.getMainContext().getConnection().getConnectionId()) {
            return;
        }

        String diff = event.getDiff();
        patch(diff);
        Context.getMainContext().postEvent(new DocumentChangedRemotelyEvent(event.getConnectionId(),this, diff));
    }

}
