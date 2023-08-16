package org.oo.demo;

@ThreadSafe
public class SafeSequence {
    @GuardedBy("this")
    private int value;

    public int getNext() {
        return value++;
    }
}


