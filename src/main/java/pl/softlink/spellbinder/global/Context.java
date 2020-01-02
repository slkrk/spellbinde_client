package pl.softlink.spellbinder.global;

import pl.softlink.spellbinder.global.event.Event;

public interface Context {

    public void postEvent(Event event);

}
