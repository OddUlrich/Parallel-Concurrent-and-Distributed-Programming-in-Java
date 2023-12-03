package org.oo.demo.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyAppThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(MyAppThread.class.getName());

    public static final String DEFAULT_NAME = "MyAppThread";

    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();

    public MyAppThread(Runnable r) {
        super(r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable r, String poolName) {
        super(r, poolName + "-" + created.incrementAndGet());

        setUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    public void uncaughtException(Thread t, Throwable e) {
                        LOGGER.log(Level.SEVERE, "UNCAUGHT in thread " + t.getName(), e);
                    }
                }
        );
    }

    public void run() {
        boolean debug = debugLifecycle;
        if (debug) {
            LOGGER.log(Level.FINE, "Created " + getName());
        }
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) {
                LOGGER.log(Level.FINE, "Exiting " + getName());
            }
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

    public static boolean getDebug() {
        return debugLifecycle;
    }

    public static void setDebug(boolean b) {
        debugLifecycle = b;
    }

}
