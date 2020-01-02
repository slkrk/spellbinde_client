package pl.softlink.spellbinder.server;

import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.global.event.Event;
import pl.softlink.spellbinder.global.event.EventDispatcher;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;
import pl.softlink.spellbinder.server.connection.ConnectionContainer;
import pl.softlink.spellbinder.server.document.DocumentList;

import java.util.LinkedList;

public class Context implements pl.softlink.spellbinder.global.Context {

    private static Context mainContext = null;

    private EventDispatcher eventDispatcher = new EventDispatcher();

    private DocumentList documentList;

    private LinkedList<ConnectionContainer> connectionContainerList = new LinkedList<ConnectionContainer>();

    public Context() {
        documentList = new DocumentList();
        eventDispatcher.registerListener(PatchReceivedEvent.class, documentList);
    }

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

    public void addConnectionContainer(ConnectionContainer connectionContainer) {
        connectionContainerList.addLast(connectionContainer);
        eventDispatcher.registerListener(DocumentChangedRemotelyEvent.class, connectionContainer);
    }

    public void removeConnectionContainer(ConnectionContainer connectionContainer) {
        if (connectionContainerList.contains(connectionContainer)) {
            connectionContainerList.remove(connectionContainer);
            eventDispatcher.unregisterListener(DocumentChangedRemotelyEvent.class, connectionContainer);
        }
    }



}
