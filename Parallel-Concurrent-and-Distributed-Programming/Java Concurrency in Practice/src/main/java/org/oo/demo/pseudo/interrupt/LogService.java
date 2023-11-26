package org.oo.demo.pseudo.interrupt;

import org.oo.demo.annotation.GuardedBy;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;

    @GuardedBy("this")
    private boolean isShutdown;
    @GuardedBy("this")
    private int reservations;


    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>(100);
        this.writer = writer;
        this.loggerThread = new LoggerThread(writer);
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException("Illegal state!");
            }
            ++reservations;
        }
        queue.put(msg);
    }


    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String msg = queue.take();
                        synchronized (LogService.this) {
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {

                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
