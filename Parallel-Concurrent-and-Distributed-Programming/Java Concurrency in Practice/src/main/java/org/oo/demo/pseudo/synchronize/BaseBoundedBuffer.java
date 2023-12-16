package org.oo.demo.pseudo.synchronize;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;

@ThreadSafe
public class BaseBoundedBuffer<V> {

    @GuardedBy("this")
    private final V[] buf;
    @GuardedBy("this")
    private int tail = 0;
    @GuardedBy("this")
    private int head = 0;
    @GuardedBy("this")
    private int count = 0;

    protected BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    protected synchronized final V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }

}
