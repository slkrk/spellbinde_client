package pl.softlink.spellbinder.client.controller;

import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.global.ContextAware;

public abstract class ControllerAbstract implements ContextAware<Context> {

    public Context getContext() {
        return Context.getMainContext();
    }

    public FrontController getFrontController() {
        return getContext().getFrontController();
    }

}