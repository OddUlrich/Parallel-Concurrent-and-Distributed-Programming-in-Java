package org.oo.demo.pseudo.combination;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.model.Widget;

public class PrivateLock {
    private final Object myLock = new Object();

    @GuardedBy("myLock")
    Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // do something.
        }
    }
}
