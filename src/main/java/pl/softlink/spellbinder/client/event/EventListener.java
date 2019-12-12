package pl.softlink.spellbinder.client.event;

public interface EventListener<Event> {

    public <T extends Event> void onEvent(T event);

}
