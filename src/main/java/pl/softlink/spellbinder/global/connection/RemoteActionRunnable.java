package pl.softlink.spellbinder.global.connection;

public abstract class RemoteActionRunnable implements Runnable{

    private boolean closed = false;

    private PullRunnable pullRunnable;

    public void setPullRunnable(PullRunnable pullRunnable) {
        this.pullRunnable = pullRunnable;
    }

    public void close() {
        closed = true;
    }

    public void run() {
        try {

            while (! closed) {
                String payload = pullRunnable.pull();
                if (payload != null) {
                    exec(payload);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("RemoteActionRunnable exception: ", e);
        }
    }

    public abstract void exec(String payload);

}
