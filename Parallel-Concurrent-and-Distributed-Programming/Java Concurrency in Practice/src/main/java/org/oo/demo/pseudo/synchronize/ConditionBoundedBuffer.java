package org.oo.demo.pseudo.synchronize;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public class ConditionBoundedBuffer<T> {

    private static final int BUFFER_SIZE = 1;

    protected final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    @GuardedBy("this")
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    @GuardedBy("this")
    private int tail = 0, head = 0, count = 0;

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = t;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T t = items[head];
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }
}
