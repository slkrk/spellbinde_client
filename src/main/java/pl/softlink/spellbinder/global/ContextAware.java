package pl.softlink.spellbinder.global;

public interface ContextAware<T extends Context> {

    public T getContext();

}
