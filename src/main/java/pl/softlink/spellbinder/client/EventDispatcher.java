package pl.softlink.spellbinder.client;

import pl.softlink.spellbinder.client.event.Event;
import pl.softlink.spellbinder.client.event.EventListener;

import java.util.HashMap;
import java.util.LinkedList;

public class EventDispatcher {

    private HashMap<Class<? extends Event>, LinkedList<EventListener>> dispatcherMap = new HashMap<>();

    public void registerListener(Class<? extends Event> eventClass, EventListener eventListener) {
        if (dispatcherMap.containsKey(eventClass)) {
            dispatcherMap.get(eventClass).addLast(eventListener);
        } else {
            LinkedList<EventListener> eventListenerLinkedList = new LinkedList<>();
            eventListenerLinkedList.addLast(eventListener);
            dispatcherMap.put(eventClass, eventListenerLinkedList);
        }
    }

    public void unregisterListener(Class<? extends Event> eventClass, EventListener eventListener) {
        if (dispatcherMap.containsKey(eventClass)) {
            LinkedList<EventListener> eventListenerLinkedList = dispatcherMap.get(eventClass);
            if (eventListenerLinkedList.contains(eventListener)) {
                eventListenerLinkedList.remove(eventListener);
            }
        }
    }

    public <T extends Event> void dispatch(T event) {
        if (dispatcherMap.containsKey(event.getClass())) {
            LinkedList<EventListener> eventListenerLinkedList = dispatcherMap.get(event.getClass());
            for (EventListener eventListener : eventListenerLinkedList) {
                eventListener.onEvent(event);
            }
        }
    }

}
