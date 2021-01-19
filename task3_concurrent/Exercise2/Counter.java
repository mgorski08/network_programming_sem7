package Exercise2;

import java.util.HashSet;
import java.util.Set;

public class Counter {
    private final Thread thread;
    private final Set<Runnable> doOnCount = new HashSet<>();
    private int count = 0;

    public Counter() {
        thread = new Thread(() -> {
            while (true) {
                Thread.yield();
                synchronized (Counter.class) {
                    ++count;
                    for (Runnable action : doOnCount) {
                        action.run();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {

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
        return getCount() + "";
    }

}
