package pl.softlink.spellbinder.client;

import pl.softlink.spellbinder.client.connection.LocalAction;
import pl.softlink.spellbinder.client.controller.EditorController;
import pl.softlink.spellbinder.client.controller.FrontController;
import pl.softlink.spellbinder.client.event.*;
import pl.softlink.spellbinder.global.connection.Connection;
import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.global.event.Event;
import pl.softlink.spellbinder.global.event.EventDispatcher;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;

public class Context implements pl.softlink.spellbinder.global.Context {

    private static Context mainContext = null;

    private EventDispatcher eventDispatcher = new EventDispatcher();

    private Document currentDocument = null;
    private EditorController editorController = null;
    private LocalAction localAction = null;
    private Connection connection;
    private FrontController frontController;

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

    public Connection getConnection() {
        return connection;
    }

    public Context setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Context setCurrentDocument(Document currentDocument) {
        eventDispatcher.unregisterListener(EditorKeyPressedEvent.class, this.currentDocument);
        eventDispatcher.unregisterListener(PatchReceivedEvent.class, this.currentDocument);
        this.currentDocument = currentDocument;
        eventDispatcher.registerListener(EditorKeyPressedEvent.class, this.currentDocument);
        eventDispatcher.registerListener(PatchReceivedEvent.class, this.currentDocument);
        return this;
    }

    public Document getCurrentDocument() {
        return currentDocument;
    }

    public Context setEditorController(EditorController editorController) {
        eventDispatcher.unregisterListener(DocumentChangedRemotelyEvent.class, this.editorController);
        this.editorController = editorController;
        eventDispatcher.registerListener(DocumentChangedRemotelyEvent.class, this.editorController);
        return this;
    }

    public Context setLocalAction(LocalAction localAction) {
        eventDispatcher.unregisterListener(DocumentChangedLocallyEvent.class, this.localAction);
        this.localAction = localAction;
        eventDispatcher.registerListener(DocumentChangedLocallyEvent.class, this.localAction);
        return this;
    }

    public LocalAction getLocalAction() {
        return localAction;
    }

    public FrontController getFrontController() {
        return frontController;
    }

    public Context setFrontController(FrontController frontController) {
        this.frontController = frontController;
        return this;
    }


}
