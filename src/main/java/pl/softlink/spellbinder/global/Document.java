package pl.softlink.spellbinder.global;

import pl.softlink.spellbinder.service.TextDiff;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Document {

    private int documentId;
    protected String content = "";
    private Lock lock = new ReentrantLock();

    public Document(int documentId) {
        this.documentId = documentId;
    }

    public Document(int documentId, String content) {
        this.documentId = documentId;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Document setContent(String content) {
        this.content = content;
        return this;
    }

    public void patch(String diff) {
        lock.lock();
        try {
            content = TextDiff.apply(content, diff);
        } finally {
            lock.unlock();
        }
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public abstract void setDocumentName(String text);
}
