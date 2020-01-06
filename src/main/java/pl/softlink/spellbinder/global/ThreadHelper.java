package pl.softlink.spellbinder.global;

public class ThreadHelper {

    public static final int DEFAULT_SLEEP_MILIS = 100;

    public static void sleep() {
        sleep(DEFAULT_SLEEP_MILIS);
    }

    public static void sleep(int milis) {

        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
