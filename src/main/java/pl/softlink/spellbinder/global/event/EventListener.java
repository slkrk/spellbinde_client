package pl.softlink.spellbinder.global.event;

public interface EventListener<Event> {

    public <T extends Event> void onEvent(T event);

}
