package cf.mgorski.networkprogramming.task2.helper;

import java.util.Iterator;
import java.util.function.Function;

public class Functional {

    public static <A, B> Iterable<B> map(Iterable<A> iterable, Function<A, B> func) {
        return () -> new Iterator<>() {
            final Iterator<A> iterator = iterable.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public B next() {
                return func.apply(iterator.next());
            }
        };
    }
}
