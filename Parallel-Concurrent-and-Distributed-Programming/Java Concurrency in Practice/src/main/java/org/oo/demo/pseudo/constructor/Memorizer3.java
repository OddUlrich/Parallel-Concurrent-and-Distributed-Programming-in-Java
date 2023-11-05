package org.oo.demo.pseudo.constructor;

import org.oo.demo.annotation.GuardedBy;

import java.util.Map;
import java.util.concurrent.*;

public class Memorizer3<A, V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memorizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    private RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
