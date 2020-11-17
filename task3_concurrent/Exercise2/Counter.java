package Exercise2;

import java.util.HashSet;
import java.util.Set;

public class Counter {
    private final Thread thread;
    private final Set<Runnable> doOnCount = new HashSet<>();
    private int count = 0;
    private String state = "";

    public Counter() {
        thread = new Thread(() -> {
            while (true) {
                synchronized (Counter.class) {
                    ++count;
                    for (Runnable action : doOnCount) {
                        action.run();
                    }
                    state = "(waiting)";
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    } finally {
                        state = "";
                    }
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
        return getCount() + " " + state;
    }

}
