package pl.softlink.spellbinder.server.document;

import pl.softlink.spellbinder.global.Document;
import pl.softlink.spellbinder.global.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.global.event.EventListener;
import pl.softlink.spellbinder.global.event.PatchReceivedEvent;
import pl.softlink.spellbinder.server.Context;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DocumentList implements EventListener<PatchReceivedEvent> {

    private HashMap<Integer, Document> documentMap = new HashMap<Integer, Document>();
    private Lock lock = new ReentrantLock();

    public Document getDocument(Integer documentId) {

        lock.lock();
        try {
            if (! documentMap.containsKey(documentId)) {
                documentMap.put(documentId, new Document(documentId));
            }
        } finally {
            lock.unlock();
        }

        return documentMap.get(documentId);
    }

    public void onEvent(PatchReceivedEvent event) {
        Document document = getDocument(event.getDocumentId());
        document.patch(event.getDiff());
        Context.getMainContext().postEvent(new DocumentChangedRemotelyEvent(event.getConnectionId(), document, event.getDiff()));
    }


}
