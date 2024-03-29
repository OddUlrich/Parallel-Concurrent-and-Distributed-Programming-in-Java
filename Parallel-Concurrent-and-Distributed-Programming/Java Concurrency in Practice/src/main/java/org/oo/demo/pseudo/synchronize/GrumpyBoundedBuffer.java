package org.oo.demo.pseudo.synchronize;

import org.oo.demo.annotation.ThreadSafe;
import org.oo.demo.exception.BufferEmptyException;
import org.oo.demo.exception.BufferFullException;

@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }

}
