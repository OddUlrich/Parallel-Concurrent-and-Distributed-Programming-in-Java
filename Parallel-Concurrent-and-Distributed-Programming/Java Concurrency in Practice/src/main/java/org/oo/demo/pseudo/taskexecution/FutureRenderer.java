package org.oo.demo.pseudo.taskexecution;

import com.sun.scenario.effect.ImageData;
import org.oo.demo.model.ImageInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class FutureRenderer {


    private static final int NTHREAD = 100;
    private static final ExecutorService executor = Executors.newFixedThreadPool(NTHREAD);

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = () -> {
            List<ImageData> result = new ArrayList<>();
            for (ImageInfo imageInfo : imageInfos) {
                result.add(imageInfo.downloadImage());
            }
            return result;
        };

        Future<List<ImageData>> future = executor.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }


    private void renderText(CharSequence source) {
        return;
    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return Collections.emptyList();
    }

    private void renderImage(ImageData data) {
        return;
    }

    private RuntimeException launderThrowable(Throwable t) {
        throw new IllegalStateException("Not unchecked", t);
    }
}
