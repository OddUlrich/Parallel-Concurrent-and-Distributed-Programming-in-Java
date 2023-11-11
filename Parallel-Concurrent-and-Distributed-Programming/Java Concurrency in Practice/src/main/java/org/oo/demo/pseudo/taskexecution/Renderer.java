package org.oo.demo.pseudo.taskexecution;

import com.sun.scenario.effect.ImageData;
import org.oo.demo.model.ImageInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Renderer {

    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
        this.executor = executor;
    }


    void renderPage(CharSequence source) {
        List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);

        for (final ImageInfo imageInfo : info) {
            completionService.submit(imageInfo::downloadImage);
        }

        renderText(source);

        try {
            for (int i = 0, n = info.size(); i < n; i++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
