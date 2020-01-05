package pl.softlink.spellbinder.global.connection;

public abstract class RemoteActionRunnable implements Runnable{

    private boolean closed = false;

    private Connection connection;

    public void setPullConnection(Connection connection) {
        this.connection = connection;
    }

    public void close() {
        closed = true;
    }

    public void run() {
        try {

            while (! closed) {
                String payload = connection.pull();
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
