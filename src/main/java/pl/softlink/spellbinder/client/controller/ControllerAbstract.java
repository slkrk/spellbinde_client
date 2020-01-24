package pl.softlink.spellbinder.client.controller;

import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.global.ContextAware;

public abstract class ControllerAbstract implements ContextAware<Context> {

    public static final String STYLE_ERROR = "-fx-text-fill: #f80000";
    public static final String STYLE_NORMAL = "-fx-text-fill: #000000";

    public Context getContext() {
        return Context.getMainContext();
    }

    public FrontController getFrontController() {
        return getContext().getFrontController();
    }

}