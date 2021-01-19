package Exercise1;

import java.util.HashSet;
import java.util.Set;

public class Counter {
    private final Thread thread;
    private final Set<Runnable> doOnCount = new HashSet<>();
    private int count = 0;

    public Counter() {
        thread = new Thread(() -> {
            while (true) {
                ++count;
                for (Runnable action : doOnCount) {
                    action.run();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    public void addAction(Runnable action) {
        doOnCount.add(action);
    }

    public void start() {
        thread.start();
    }

    @Deprecated(since = "1.2")
    public void suspend() {
        thread.suspend();
    }

    @Deprecated(since = "1.2")
    public void resume() {
        thread.resume();
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        count = 0;
        for (Runnable action : doOnCount) {
            action.run();
        }
    }

    @Override
    public String toString() {
        return getCount() + "";
    }

}
