package org.oo.demo.pseudo.taskexecution;

import org.oo.demo.model.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import static com.sun.activation.registries.LogSupport.log;


public class LifecycleWebServer {

    private static final int NTHREAD = 100;
    private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREAD);

    public void start(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket connection = socket.accept();
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(connection);
                    }
                });
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown()) {
                    log("task submission rejected", e);
                }
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    private void handleRequest(Socket conn) {
        Request req = readRequest(conn);
        if (isShutdown(req)) {
            stop();
        } else {
            dispatchRequest(req);
        }
    }

    private Request readRequest(Socket conn) {
        return new Request();
    }

    private boolean isShutdown(Request req) {
        return true;
    }

    private void dispatchRequest(Request req) {
        return;
    }
}
