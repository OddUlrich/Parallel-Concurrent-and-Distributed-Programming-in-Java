package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    public int getNext() {
        return value++;
    }
}


