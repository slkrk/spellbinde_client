package pl.softlink.spellbinder.server.model;

public abstract class Model {

    public abstract Integer getId();

    public boolean isNew() {
        return (getId() == null) ? true : false;
    }

}
