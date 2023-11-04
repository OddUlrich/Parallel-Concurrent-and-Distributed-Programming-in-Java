package org.oo.demo.pseudo.constructor;

import org.oo.demo.exception.DataLoadException;
import org.oo.demo.model.ProductInfo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {

    private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws DataLoadException {
            return loadProductInfo();
        }
    });

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws DataLoadException, InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw launderThrowable(cause);
            }
        }
    }

    private ProductInfo loadProductInfo() {
        return new ProductInfo();
    }

    private InterruptedException launderThrowable(Throwable cause) {
        return new InterruptedException();
    }
}
