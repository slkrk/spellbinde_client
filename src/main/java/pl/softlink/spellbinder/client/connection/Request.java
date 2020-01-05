package pl.softlink.spellbinder.client.connection;

import pl.softlink.spellbinder.client.Context;
import pl.softlink.spellbinder.client.event.ResponseEvent;
import pl.softlink.spellbinder.global.ContextAware;
import pl.softlink.spellbinder.global.event.EventListener;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class Request implements Callable<ResponseEvent>, EventListener<ResponseEvent>, ContextAware<Context> {

    private int requestId = new Random().nextInt(99999);
    private String requestAction;
    private Map<String, String> body = null;
    private ResponseEvent responseEvent = null;

    public Request(String requestAction) {
        this.requestAction = requestAction;
    }

    public Request(String requestAction, Map<String, String> body) {
        this.requestAction = requestAction;
        this.body = body;
    }

    @Override
    public Context getContext() {
        return Context.getMainContext();
    }

    public void onEvent(ResponseEvent responseEvent) {
        if (responseEvent.getRequestId() == requestId) {
            unregisterListenerSelf();
            this.responseEvent = responseEvent;
        }
    }

    private void registerListenerSelf() {
        getContext().getEventDispatcher().registerListener(ResponseEvent.class, this);
    }

    private void unregisterListenerSelf() {
        getContext().getEventDispatcher().unregisterListener(ResponseEvent.class, this);
    }

    public int getRequestId() {
        return requestId;
    }

    public String getRequestAction() {
        return requestAction;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public ResponseEvent call() {
        registerListenerSelf();
        getContext().getLocalAction().sendRequest(this);
        while (responseEvent == null) {
//            wait
        }

        return responseEvent;
    }

    public static ResponseEvent sendRequest(Request request) {
        FutureTask<ResponseEvent> futureTask = new FutureTask<ResponseEvent>(request);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(futureTask);

        try {
            return futureTask.get();
        } catch (ExecutionException e) {
            return new ResponseEvent(null, 503, null, e.getMessage());
        } catch (InterruptedException e) {
            return new ResponseEvent(null, 503, null, e.getMessage());
        }

    }

}
