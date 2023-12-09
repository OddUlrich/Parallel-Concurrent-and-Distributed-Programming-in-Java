package org.oo.demo.pseudo.gui;

import java.util.concurrent.*;

abstract public class BackGroundTask<V> implements Runnable, Future<V> {

    private final FutureTask<V> computation = new Computation();

    private class Computation extends FutureTask<V> {
        public Computation() {
            super(new Callable<V>() {
                public V call() throws Exception {
                    return BackGroundTask.this.compute();
                }
            });
        }

        protected final void done() {
            GuiExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    V value = null;
                    Throwable thrown = null;
                    boolean cancelled = false;
                    try {
                        value = get();
                    } catch (ExecutionException e) {
                        thrown = e.getCause();
                    } catch (CancellationException e) {
                        cancelled = true;
                    } catch (InterruptedException consumed) {

                    } finally {
                        onCompletion(value, thrown, cancelled);
                    }
                }
            });
        }
    }

    protected void setProgress(final int current, final int max) {
        GuiExecutor.getInstance().execute((new Runnable() {
            @Override
            public void run() {
                onProgress(current, max);
            }
        }));
    }

    protected abstract V compute() throws Exception;

    protected void onCompletion(V result, Throwable exception, boolean cancelled) {

    }

    protected void onProgress(int current, int max) {

    }
}
