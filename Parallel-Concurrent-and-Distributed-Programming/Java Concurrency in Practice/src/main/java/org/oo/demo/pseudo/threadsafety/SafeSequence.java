package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;

@ThreadSafe
public class SafeSequence {

    @GuardedBy("this")
    private int value;

    public int getNext() {
        return value++;
    }
}


