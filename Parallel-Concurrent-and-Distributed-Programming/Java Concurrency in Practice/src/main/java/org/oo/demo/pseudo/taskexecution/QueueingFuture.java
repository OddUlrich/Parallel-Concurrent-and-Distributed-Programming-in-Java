package org.oo.demo.pseudo.taskexecution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class QueueingFuture<V> extends FutureTask<V> {

    private final List<QueueingFuture<V>> completionQueue = new ArrayList<>();

    QueueingFuture(Callable<V> c) {
        super(c);
    }

    QueueingFuture(Runnable t, V r) {
        super(t, r);
    }

    protected void done() {
        completionQueue.add(this);
    }
}
