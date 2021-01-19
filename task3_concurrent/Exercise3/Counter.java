package Exercise3;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class Counter {
    private final Set<Runnable> doOnCount = new HashSet<>();
    private int count = 0;
    final AtomicReference<CompletableFuture<Integer>> completableFutureRef = new AtomicReference<>();
    final AtomicReference<UnaryOperator<Integer>> applyCountRef = new AtomicReference<>();

    public Counter() {
        final UnaryOperator<Integer> increment = (Integer i) -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            return i + 1;
        };

        applyCountRef.set((Integer i) -> {
            CompletableFuture<Integer> completableFuture = completableFutureRef.get();
            completableFuture = completableFuture.thenApply(increment).thenApply(applyCountRef.get());
            completableFutureRef.set(completableFuture);
            count = i;
            for (Runnable action : doOnCount) {
                action.run();
            }
            return count;
        });
    }

    public void addAction(Runnable action) {
        doOnCount.add(action);
    }

    public void start() {
        completableFutureRef.set(CompletableFuture.supplyAsync(() -> count).thenApply(applyCountRef.get()));
    }

    @Deprecated(since = "1.2")
    public void suspend() {
        completableFutureRef.get().cancel(true);
    }

    @Deprecated(since = "1.2")
    public void resume() {
        this.start();
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
