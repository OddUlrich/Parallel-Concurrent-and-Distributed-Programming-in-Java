package org.oo.demo.pseudo.combination;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;

@ThreadSafe
public class Counter {
    @GuardedBy("this")
    private long value = 0;

    public synchronized  long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("Counter overflow!");
        }
        return ++value;
    }
}
