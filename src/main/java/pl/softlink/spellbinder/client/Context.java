package pl.softlink.spellbinder.client;

import pl.softlink.spellbinder.client.connection.Connection;
import pl.softlink.spellbinder.client.event.*;

public class Context {

    private static Context mainContext = null;

    private EventDispatcher eventDispatcher = new EventDispatcher();

    private Document currentDocument = null;
    private Connection connection = null;
    private EditorController editorController = null;

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public void postEvent(Event event) {
        eventDispatcher.dispatch(event);
    }

    public static Context getMainContext() {
        return mainContext;
    }

    public static void setMainContext(Context mainContext) {
        Context.mainContext = mainContext;
    }

    public Document getCurrentDocument() {
        return currentDocument;
    }

    public Context setCurrentDocument(Document currentDocument) {
        eventDispatcher.unregisterListener(EditorKeyPressedEvent.class, this.currentDocument);
        eventDispatcher.unregisterListener(PatchReceivedEvent.class, this.currentDocument);
        this.currentDocument = currentDocument;
        eventDispatcher.registerListener(EditorKeyPressedEvent.class, this.currentDocument);
        eventDispatcher.registerListener(PatchReceivedEvent.class, this.currentDocument);
        return this;
    }

    public Context setConnection(Connection connection) {
        eventDispatcher.unregisterListener(DocumentChangedLocallyEvent.class, this.connection);
        this.connection = connection;
        eventDispatcher.registerListener(DocumentChangedLocallyEvent.class, this.connection);
        return this;
    }

    public Context setEditorController(EditorController editorController) {
        eventDispatcher.unregisterListener(DocumentChangedRemotelyEvent.class, this.editorController);
        this.editorController = editorController;
        eventDispatcher.registerListener(DocumentChangedRemotelyEvent.class, this.editorController);
        return this;
    }

}
